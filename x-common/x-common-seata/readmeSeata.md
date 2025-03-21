seata
https://seata.apache.org/
What is Seata?
Seata is an open source distributed transaction solution that delivers high performance and easy to use distributed transaction services under a 
microservices architecture. Before the open source of Seata, its internal version has been playing the role of middleware for data consistency of 
application architecture layer in Alibaba group, helping the economy to smoothly go through the Double 11 of the past year and providing strong technical 
support for the upper business. After years of development, its commercial products have been sold on Alibaba Cloud and Financial cloud. 2019.1 in order to create a more complete technological ecology and inclusive technological achievements, Seata officially announced open source to the outside world. In the future, Seata will organize as a community to help users quickly land distributed transaction solutions.


Feature List
Microservices Framework Support
RPC frameworks such as Dubbo, Spring Cloud, Sofa-RPC, Motan, and grpc are currently supported, and other frameworks are continuously integrated.

AT mode
Provides non-intrusive automatic compensation transaction mode, currently supports MySQL, Oracle, PostgreSQL, TiDB, MariaDB, DaMeng, PolarDB-X 2.0, SQLServer. DB2 is currently under development.

TCC mode
Support TCC mode and mix with AT for greater flexibility.

SAGA mode
Provide an effective solution for long transactions.

XA mode
Support for XA schemas for databases that have implemented XA interfaces. currently supports MySQL, Oracle and MariaDB

High availability
Supports cluster mode with separate storage and computing, where computing nodes can be horizontally scaled, and storage supports both databases and Redis. The Raft cluster mode has entered the beta testing phase.


Seata 是什么?
Seata 是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。Seata 将为用户提供了 AT、TCC、SAGA 和 XA 事务模式，为用户打造一站式的分布式解决方案。 image

AT 模式
前提
基于支持本地 ACID 事务的关系型数据库。
Java 应用，通过 JDBC 访问数据库。
整体机制
两阶段提交协议的演变：

一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。

二阶段：

提交异步化，非常快速地完成。
回滚通过一阶段的回滚日志进行反向补偿。
写隔离
一阶段本地事务提交前，需要确保先拿到 全局锁 。
拿不到 全局锁 ，不能提交本地事务。
拿 全局锁 的尝试被限制在一定范围内，超出范围将放弃，并回滚本地事务，释放本地锁。
以一个示例来说明：

两个全局事务 tx1 和 tx2，分别对 a 表的 m 字段进行更新操作，m 的初始值 1000。

tx1 先开始，开启本地事务，拿到本地锁，更新操作 m = 1000 - 100 = 900。本地事务提交前，先拿到该记录的 全局锁 ，本地提交释放本地锁。 tx2 后开始，开启本地事务，拿到本地锁，更新操作 m = 900 - 100 = 800。本地事务提交前，尝试拿该记录的 全局锁 ，tx1 全局提交前，该记录的全局锁被 tx1 持有，tx2 需要重试等待 全局锁 。

Write-Isolation: Commit

tx1 二阶段全局提交，释放 全局锁 。tx2 拿到 全局锁 提交本地事务。

Write-Isolation: Rollback

如果 tx1 的二阶段全局回滚，则 tx1 需要重新获取该数据的本地锁，进行反向补偿的更新操作，实现分支的回滚。

此时，如果 tx2 仍在等待该数据的 全局锁，同时持有本地锁，则 tx1 的分支回滚会失败。分支的回滚会一直重试，直到 tx2 的 全局锁 等锁超时，放弃 全局锁 并回滚本地事务释放本地锁，tx1 的分支回滚最终成功。

因为整个过程 全局锁 在 tx1 结束前一直是被 tx1 持有的，所以不会发生 脏写 的问题。

读隔离
在数据库本地事务隔离级别 读已提交（Read Committed） 或以上的基础上，Seata（AT 模式）的默认全局隔离级别是 读未提交（Read Uncommitted） 。

如果应用在特定场景下，必需要求全局的 读已提交 ，目前 Seata 的方式是通过 SELECT FOR UPDATE 语句的代理。

Read Isolation: SELECT FOR UPDATE

SELECT FOR UPDATE 语句的执行会申请 全局锁 ，如果 全局锁 被其他事务持有，则释放本地锁（回滚 SELECT FOR UPDATE 语句的本地执行）并重试。这个过程中，查询是被 block 住的，直到 全局锁 拿到，即读取的相关数据是 已提交 的，才返回。

出于总体性能上的考虑，Seata 目前的方案并没有对所有 SELECT 语句都进行代理，仅针对 FOR UPDATE 的 SELECT 语句。

工作机制
以一个示例来说明整个 AT 分支的工作过程。

业务表：product

Field	Type	Key
id	bigint(20)	PRI
name	varchar(100)
since	varchar(100)
AT 分支事务的业务逻辑：

update product set name = 'GTS' where name = 'TXC';

一阶段
过程：

解析 SQL：得到 SQL 的类型（UPDATE），表（product），条件（where name = 'TXC'）等相关的信息。
查询前镜像：根据解析得到的条件信息，生成查询语句，定位数据。
select id, name, since from product where name = 'TXC';

得到前镜像：

id	name	since
1	TXC	2014
执行业务 SQL：更新这条记录的 name 为 'GTS'。
查询后镜像：根据前镜像的结果，通过 主键 定位数据。
select id, name, since from product where id = 1;

得到后镜像：

id	name	since
1	GTS	2014
插入回滚日志：把前后镜像数据以及业务 SQL 相关的信息组成一条回滚日志记录，插入到 UNDO_LOG 表中。
{
"branchId": 641789253,
"undoItems": [{
"afterImage": {
"rows": [{
"fields": [{
"name": "id",
"type": 4,
"value": 1
}, {
"name": "name",
"type": 12,
"value": "GTS"
}, {
"name": "since",
"type": 12,
"value": "2014"
}]
}],
"tableName": "product"
},
"beforeImage": {
"rows": [{
"fields": [{
"name": "id",
"type": 4,
"value": 1
}, {
"name": "name",
"type": 12,
"value": "TXC"
}, {
"name": "since",
"type": 12,
"value": "2014"
}]
}],
"tableName": "product"
},
"sqlType": "UPDATE"
}],
"xid": "xid:xxx"
}

提交前，向 TC 注册分支：申请 product 表中，主键值等于 1 的记录的 全局锁 。
本地事务提交：业务数据的更新和前面步骤中生成的 UNDO LOG 一并提交。
将本地事务提交的结果上报给 TC。
二阶段-回滚
收到 TC 的分支回滚请求，开启一个本地事务，执行如下操作。
通过 XID 和 Branch ID 查找到相应的 UNDO LOG 记录。
数据校验：拿 UNDO LOG 中的后镜与当前数据进行比较，如果有不同，说明数据被当前全局事务之外的动作做了修改。这种情况，需要根据配置策略来做处理，详细的说明在另外的文档中介绍。
根据 UNDO LOG 中的前镜像和业务 SQL 的相关信息生成并执行回滚的语句：
update product set name = 'TXC' where id = 1;

提交本地事务。并把本地事务的执行结果（即分支事务回滚的结果）上报给 TC。
二阶段-提交
收到 TC 的分支提交请求，把请求放入一个异步任务的队列中，马上返回提交成功的结果给 TC。
异步任务阶段的分支提交请求将异步和批量地删除相应 UNDO LOG 记录。
附录
回滚日志表
UNDO_LOG Table：不同数据库在类型上会略有差别。

以 MySQL 为例：

Field	Type
branch_id	bigint PK
xid	varchar(100)
context	varchar(128)
rollback_info	longblob
log_status	tinyint
log_created	datetime
log_modified	datetime
-- 注意此处0.7.0+ 增加字段 context
CREATE TABLE `undo_log` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`branch_id` bigint(20) NOT NULL,
`xid` varchar(100) NOT NULL,
`context` varchar(128) NOT NULL,
`rollback_info` longblob NOT NULL,
`log_status` int(11) NOT NULL,
`log_created` datetime NOT NULL,
`log_modified` datetime NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

TCC 模式
回顾总览中的描述：一个分布式的全局事务，整体是 两阶段提交 的模型。全局事务是由若干分支事务组成的，分支事务要满足 两阶段提交 的模型要求，即需要每个分支事务都具备自己的：

一阶段 prepare 行为
二阶段 commit 或 rollback 行为
Overview of a global transaction

根据两阶段行为模式的不同，我们将分支事务划分为 Automatic (Branch) Transaction Mode 和 Manual (Branch) Transaction Mode.

AT 模式基于 支持本地 ACID 事务 的 关系型数据库：

一阶段 prepare 行为：在本地事务中，一并提交业务数据更新和相应回滚日志记录。
二阶段 commit 行为：马上成功结束，自动 异步批量清理回滚日志。
二阶段 rollback 行为：通过回滚日志，自动 生成补偿操作，完成数据回滚。
相应的，TCC 模式，不依赖于底层数据资源的事务支持：

一阶段 prepare 行为：调用 自定义 的 prepare 逻辑。
二阶段 commit 行为：调用 自定义 的 commit 逻辑。
二阶段 rollback 行为：调用 自定义 的 rollback 逻辑。
所谓 TCC 模式，是指支持把 自定义 的分支事务纳入到全局事务的管理中。

Saga 模式
Saga模式是SEATA提供的长事务解决方案，在Saga模式中，业务流程中每个参与者都提交本地事务，当出现某一个参与者失败则补偿前面已经成功的参与者，一阶段正向服务和二阶段补偿服务都由业务开发实现。

Saga模式示意图

理论基础：Hector & Kenneth 发表论⽂ Sagas （1987）

适用场景：
业务流程长、业务流程多
参与者包含其它公司或遗留系统服务，无法提供 TCC 模式要求的三个接口
优势：
一阶段提交本地事务，无锁，高性能
事件驱动架构，参与者可异步执行，高吞吐
补偿服务易于实现
缺点：
不保证隔离性（应对方案见用户文档）

Seata术语
TC (Transaction Coordinator) - 事务协调者
维护全局和分支事务的状态，驱动全局事务提交或回滚。

TM (Transaction Manager) - 事务管理器
定义全局事务的范围：开始全局事务、提交或回滚全局事务。

RM (Resource Manager) - 资源管理器
管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。




全局事务状态表
以db模式举例，global_table是seata的全局事务表。你可以通过观察global_table表中status字段知悉全局事务处于哪个状态

状态	代码	备注
全局事务开始（Begin）	1	此状态可以接受新的分支事务注册
全局事务提交中（Committing）	2	这个状态会随时改变
全局事务提交重试（CommitRetry）	3	在提交异常被解决后尝试重试提交
全局事务回滚中（Rollbacking）	4	正在重新回滚全局事务
全局事务回滚重试中（RollbackRetrying）	5	在全局回滚异常被解决后尝试事务重试回滚中
全局事务超时回滚中（TimeoutRollbacking）	6	全局事务超时回滚中
全局事务超时回滚重试中（TimeoutRollbackRetrying）	7	全局事务超时回滚重试中
异步提交中（AsyncCommitting）	8	异步提交中
二阶段已提交（Committed）	9	二阶段已提交，此状态后全局事务状态不会再改变
二阶段提交失败（CommitFailed）	10	二阶段提交失败
二阶段决议全局回滚（Rollbacked）	11	二阶段决议全局回滚
二阶段全局回滚失败（RollbackFailed）	12	二阶段全局回滚失败
二阶段超时回滚（TimeoutRollbacked）	13	二阶段超时回滚
二阶段超时回滚失败（TimeoutRollbackFailed）	14	二阶段超时回滚失败
全局事务结束（Finished）	15	全局事务结束
二阶段提交超时（CommitRetryTimeout）	16	二阶段提交因超过重试时间限制导致失败
二阶段回滚超时（RollbackRetryTimeout）	17	二阶段回滚因超过重试时间限制导致失败
未知状态（UnKnown）	0	未知状态
分支事务状态表
状态	代码	备注
分支事务注册（Registered）	1	向TC注册分支事务
分支事务一阶段完成（PhaseOne_Done）	2	分支事务一阶段业务逻辑完成
分支事务一阶段失败（PhaseOne_Failed）	3	分支事务一阶段业务逻辑失败
分支事务一阶段超时（PhaseOne_Timeout）	4	分支事务一阶段处理超时
分支事务二阶段已提交（PhaseTwo_Committed）	5	分支事务二阶段提交
分支事务二阶段提交失败重试（PhaseTwo_CommitFailed_Retryable）	6	分支事务二阶段提交失败重试
分支事务二阶段提交失败不重试（PhaseTwo_CommitFailed_Unretryable）	7	分支事务二阶段提交失败不重试
分支事务二阶段已回滚（PhaseTwo_Rollbacked）	8	分支事务二阶段已回滚
分支事务二阶段回滚失败重试（PhaseTwo_RollbackFailed_Retryable）	9	分支事务二阶段回滚失败重试
分支事务二阶段回滚失败不重试（PhaseTwo_RollbackFailed_Unretryable）	10	二阶段提交失败
未知状态（UnKnown）	0	未知状态
为帮助理解，下面对个别状态进行补充说明：

全局事务超时回滚中（TimeoutRollbacking）
怎么发生的？

当某个seata全局事务执行过程中，无法完成业务。
TC中的一个定时任务（专门用来寻找已超时的全局事务），发现该全局事务未回滚完成，就会将此全局事务改为全局事务超时回滚中（TimeoutRollbacking），开始回滚，直到回滚完毕后删除global_table数据。
建议：当你发现全局事务处于该状态，请排查为何业务无法在限定时间内完成事务。若确实无法完成，应调大全局事务超时时间。（如排查一切正常，请检查tc集群时区与数据库是否一致，若不一致请改为一致）。

编辑此页


