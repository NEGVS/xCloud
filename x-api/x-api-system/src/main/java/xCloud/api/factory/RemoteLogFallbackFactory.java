//package xCloud.api.factory;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import xCloud.api.RemoteLogService;
//import xCloud.api.domain.SysLogininfor;
//import xCloud.api.domain.SysOperLog;
//
///**
// * 日志服务降级处理
// *
// * @author AndyFan
// */
//@Component
//public class RemoteLogFallbackFactory implements FallbackFactory< RemoteLogService >
//{
//    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);
//
//    @Override
//    public RemoteLogService create(Throwable throwable)
//    {
//        log.error("日志服务调用失败:{}", throwable.getMessage());
//        return new RemoteLogService()
//        {
//            @Override
//            public R<Boolean> saveLog( SysOperLog sysOperLog, String source)
//            {
//                return R.fail("保存操作日志失败:" + throwable.getMessage());
//            }
//
//            @Override
//            public R<Boolean> saveLogininfor( SysLogininfor sysLogininfor, String source)
//            {
//                return R.fail("保存登录日志失败:" + throwable.getMessage());
//            }
//        };
//
//    }
//}
