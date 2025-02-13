//package xCloud.serviceImpl;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONUtil;
//import com.alibaba.fastjson2.JSONObject;
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import xCloud.entity.XFinancials;
//import xCloud.entity.XOrderItems;
//import xCloud.entity.XOrderMessage;
//import xCloud.entity.XOrders;
//import xCloud.entity.result.XResponseResult;
//import xCloud.mapper.XFinancialsMapper;
//import xCloud.mapper.XOrderItemsMapper;
//import xCloud.mapper.XOrdersMapper;
//import xCloud.param.OrderDetailInfoDTO;
//import xCloud.param.OrderInfoDTO;
//import xCloud.param.OrderParam;
//import xCloud.param.OrderProductParam;
//import xCloud.param.PayParam;
//import xCloud.service.IXOrdersService;
//import xCloud.util.CodeX;
//import xCloud.xProduct.entity.XProducts;
//import xCloud.xProduct.service.IXProductsService;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
///**
// * 订单Service业务层处理
// *
// * @author AndyFan
// * @date 2024-11-28
// */
//@Slf4j
//@Service
//public class XOrdersServiceImpl implements IXOrdersService
//{
//   @Resource
//   private XOrdersMapper xOrdersMapper;
//   @Resource
//   private XOrderItemsServiceImpl xOrderItemsService;
//   @Resource
//   private XFinancialsMapper xFinancialsMapper;
//   @Resource
//   private IXProductsService xProductsService;
//
//   //   @Resource
//   //   private XShoppingCartMapper shoppingCartMapper;
//
//   @Resource
//   private XOrderItemsMapper xOrderItemsMapper;
//   //
//   //   @Resource
//   //   private RedisCache redisCache;
//
//   /**
//    * 查询订单
//    *
//    * @param orderId 订单主键
//    * @return 订单
//    */
//   @Override
//   public XOrders selectXOrdersByOrderId( Long orderId )
//   {
//      return xOrdersMapper.selectXOrdersByOrderId( orderId );
//   }
//
//   /**
//    * 查询订单列表
//    *
//    * @param xOrders 订单
//    * @return 订单
//    */
//   @Override
//   public List< XOrders > selectXOrdersList( XOrders xOrders )
//   {
//      return xOrdersMapper.selectXOrdersList( xOrders );
//   }
//
//   /**
//    * 新增订单
//    *
//    * @param xOrders 订单
//    * @return 结果
//    */
//   AtomicInteger countAtomic = new AtomicInteger( 0 );
//   AtomicInteger countAtomicItems = new AtomicInteger( 0 );
//   AtomicInteger countAtomicFinancials = new AtomicInteger( 0 );
//
//   @Override
//   @Transactional(rollbackFor = Exception.class)
//   public XResponseResult insertXOrders( XOrders xOrders )
//   {
//      int count = 0;
//      /**
//       * 1-create order
//       */
//      long orderId = IdWorker.getId();
//      xOrders.setOrderId( orderId );
//      xOrders.setUserId( 1L );
//      xOrders.setMerchantId( 1L );
//      xOrders.setAmount( new BigDecimal( CodeX.generalRandomInt() ) );
//      xOrders.setStatus( 1 );
//      xOrders.setPaymentId( "paymentId" + 1L );
//      xOrders.setShoppingJson( JSONObject.toJSONString( xOrders ) );
//      xOrders.setPayTime( new Date() );
//      xOrders.setRiderId( "1" );
//      xOrders.setShippingAddress( "收货地址" );
//      xOrders.setCreatedTime( new Date() );
//      if ( xOrdersMapper.insertXOrders( xOrders ) < 1 )
//      {
//         log.info( "\n创建订单失败" );
//      }
//      else
//      {
//         countAtomic.incrementAndGet();
//         count++;
//         log.info( "\n创建订单成功" );
//      }
//      /**
//       * 2.create order items
//       */
//      XOrderItems xOrderItems = new XOrderItems();
//      xOrderItems.setOrderItemId( IdWorker.getId() );
//      xOrderItems.setOrderId( orderId );
//      xOrderItems.setProductId( 1L );
//      xOrderItems.setQuantity( 2L );
//      xOrderItems.setPrice( new BigDecimal( CodeX.generalRandomInt() ) );
//      xOrderItems.setCreatedTime( new Date() );
//      if ( xOrderItemsMapper.insertXOrderItems( xOrderItems ) < 1 )
//      {
//         log.info( "\n创建订单明细失败" );
//      }
//      else
//      {
//         countAtomicItems.incrementAndGet();
//         count++;
//         log.info( "\n创建订单明细成功" );
//      }
//      /**
//       * 3. 更新库存、支付、等后续操作
//       */
//      updateInventory( xOrders );
//
//      //4. 发起支付
//      // initiatePayment(orderMessage);
//      //5.财务流水
//      XFinancials xFinancials = new XFinancials();
//      xFinancials.setFinancialId( IdWorker.getId() );
//      xFinancials.setMerchantId( xOrders.getMerchantId() );
//      xFinancials.setAmount( xOrders.getAmount() );
//      xFinancials.setTransactionType( 1L );
//      xFinancials.setCreatedTime( new Date() );
//      if ( xFinancialsMapper.insertXFinancials( xFinancials ) < 1 )
//      {
//         log.info( "\n财务流水创建失败" );
//      }
//      else
//      {
//         countAtomicFinancials.incrementAndGet();
//         count++;
//         log.info( "\n财务流水创建成功" );
//      }
//      return XResponseResult.success( "orderCount:" + countAtomic, ", OrderItemCount:" + countAtomicItems + ", financial:" + countAtomicFinancials );
//   }
//
//   // 发起支付
//   private void initiatePayment( XOrderMessage orderMessage )
//   {
//      //处理支付操作
//      //      boolean paymentSuccess = paymentService.processPayment( orderMessage.getAmount(), orderMessage.getPaymentId() );
//      //      if ( !paymentSuccess )
//      //      {
//      //         throw new RuntimeException( "Payment failed for order " + orderMessage.getPaymentId() );
//      //      }
//   }
//
//   /**
//    * 扣减库存
//    *
//    * @param xOrders x
//    */
//   private void updateInventory( XOrders xOrders )
//   {
//      // 假设库存服务是一个独立的微服务，这里调用库存服务API进行扣减库存操作
//      for ( XOrderItems item : xOrders.getItems() )
//      {
//         XProducts xProducts = xProductsService.selectXProductsByProductId( item.getProductId() );
//         if ( xProducts == null )
//         {
//            throw new RuntimeException( "Product not found: " + item.getProductId() );
//         }
//         if ( xProducts.getStock() < item.getQuantity() )
//         {
//            log.info( "\n库存：" + xProducts.getStock() + "，扣减数量：" + item.getQuantity() );
//            throw new RuntimeException( "Insufficient stock for product " + item.getProductId() );
//         }
//
//         xProducts.setStock( xProducts.getStock() - item.getQuantity() );
//         if ( xProductsService.updateInventory( xProducts ) < 1 )
//         {
//            log.info( "\n库存扣减失败" );
//         }
//         else
//         {
//            log.info( "\n库存扣减成功" );
//         }
//      }
//   }
//
//   /**
//    * 新增订单明细--生成
//    *
//    * @param xOrders x
//    * @return 结果
//    */
//   @Override
//   public int insertXGenerate( XOrders xOrders )
//   {
//      int count = 0;
//      for ( int i = 0; i < 100; i++ )
//      {
//         xOrders.setMerchantId( 1L );
//         xOrders.setAmount( new BigDecimal( CodeX.generalRandomInt() ) );
//         long orderId = IdWorker.getId();
//         xOrders.setOrderId( orderId );
//         xOrders.setStatus( 1 );
//         xOrders.setUserId( 1L );
//         xOrders.setRiderId( String.valueOf( CodeX.generalRandomInt() ) );
//         xOrders.setPaymentId( String.valueOf( CodeX.generalRandomInt() ) );
//         xOrders.setShoppingJson( CodeX.generalComplexRandomString( 20 ) );
//         xOrders.setPayTime( new Date() );
//         xOrders.setCreatedTime( new Date() );
//         count += xOrdersMapper.insertXOrders( xOrders );
//
//      }
//      return count;
//   }
//
//   /**
//    * 修改订单
//    *
//    * @param xOrders 订单
//    * @return 结果
//    */
//   @Override
//   public int updateXOrders( XOrders xOrders )
//   {
//      return xOrdersMapper.updateXOrders( xOrders );
//   }
//
//   /**
//    * 批量删除订单
//    *
//    * @param orderIds 需要删除的订单主键
//    * @return 结果
//    */
//   @Override
//   public int deleteXOrdersByOrderIds( List< Long > orderIds )
//   {
//      return xOrdersMapper.deleteXOrdersByOrderIds( orderIds );
//   }
//
//   /**
//    * 删除订单信息
//    *
//    * @param orderId 订单主键
//    * @return 结果
//    */
//   @Override
//   public int deleteXOrdersByOrderId( Long orderId )
//   {
//      return xOrdersMapper.deleteXOrdersByOrderId( orderId );
//   }
//
//   /**
//    * 创建订单
//    * 1、校验参数
//    * 2、校验购物车中的信息和入参是否一致
//    * 3、校验商品信息是否发生变动
//    * 4、创建订单信息
//    * 5、创建订单明细
//    * 6、生成支付ID
//    *
//    * @param dto 订单参数
//    * @return 结果
//    */
//   @Override
//   @Transactional(rollbackFor = Exception.class)
//   public XResponseResult addOrder( OrderParam dto )
//   {
//      log.info( "创建订单入参：{}", JSONUtil.toJsonStr( dto ) );
//
//      // 1. 参数校验
//      String errorMsg = validateOrderParam( dto );
//      if ( StrUtil.isNotBlank( errorMsg ) )
//      {
//         return XResponseResult.fail( errorMsg );
//      }
//
//      // 2. 校验购物车信息与订单参数是否匹配
//      //      List< XShoppingCart > shoppingCarts = getValidShoppingCarts();
//      //      if ( CollUtil.isEmpty( shoppingCarts ) )
//      //      {
//      //         return XResponseResult.fail( "购物车为空" );
//      //      }
//      //      // 获取参数中的商品ID列表
//      //      List< Long > productIdList = dto.getProductList().stream().map( OrderProductParam::getProductId ).collect( Collectors.toList() );
//      //      // 初始化订单明细列表
//      //      List< XOrderItems > itemsList = new ArrayList<>();
//      //      // 初始化订单详情返回数据
//      //      List< OrderDetailInfoDTO > detailInfoList = new ArrayList<>();
//      //      // 计算总金额
//      //      BigDecimal totalAmount = processShoppingCart( shoppingCarts, productIdList, dto, itemsList, detailInfoList );
//      //
//      //      // 3. 创建订单
//      //      XOrders order = createOrder( dto, totalAmount );
//      //      itemsList.forEach( item -> item.setOrderId( order.getOrderId() ) );
//      //      int row = xOrderItemsMapper.insertXOrderItemsBatch( itemsList );
//      //      if ( row <= 0 )
//      //      {
//      //         // 订单明细生成失败
//      //         log.info( "订单明细生成失败，用户ID：{}", SecurityUtils.getUserId() );
//      //         throw new ServiceException( "订单创建失败" );
//      //      }
//      //
//      //      // 4. 清空购物车
//      //      clearShoppingCart();
//
//      // 5. 返回订单信息
//      //      return XResponseResult.success( buildOrderResponse( order, totalAmount, detailInfoList, dto ) );
//      return XResponseResult.success( "null" );
//   }
//
//   /**
//    * 获取当前用户的购物车信息
//    *
//    * @return 购物车信息
//    */
//   //   private List< XShoppingCart > getValidShoppingCarts()
//   //   {
//   //      return shoppingCartMapper.selectXShoppingCartByUserId( SecurityUtils.getUserId() );
//   //   }
//
//   /**
//    * 处理购物车信息，生成订单明细，并计算总金额
//    *
//    * @param shoppingCarts  购物车信息
//    * @param productIdList  商品ID列表
//    * @param dto            入参
//    * @param itemsList      详情实体
//    * @param detailInfoList 详情返回数据
//    * @return 总金额
//    */
//   //   private BigDecimal processShoppingCart( List< XShoppingCart > shoppingCarts, List< Long > productIdList, OrderParam dto, List< XOrderItems > itemsList,
//   //         List< OrderDetailInfoDTO > detailInfoList )
//   //   {
//   //      BigDecimal totalAmount = BigDecimal.ZERO;
//   //
//   //      for ( XShoppingCart cart : shoppingCarts )
//   //      {
//   //         validateCartAgainstOrder( cart, productIdList, dto );
//   //
//   //         XProducts product = xProductsService.selectXProductsByProductId( cart.getProductId() );
//   //         if ( product == null )
//   //         {
//   //            log.info( "商品不存在，商品ID：{}", cart.getProductId() );
//   //            // 清空购物车当前商品数据
//   //            clearShoppingCart( cart.getProductId() );
//   //            continue;
//   //         }
//   //         /*
//   //         暂时不需要对比价格 | 后续可能需要对比价格
//   //         if (product.getPrice().compareTo(cart.getPrice()) != 0) {
//   //            // 购物车中清空当前商品
//   //            clearShoppingCart(product.getProductId());
//   //            log.info("商品价格有变动，商品ID：{}", product.getProductId());
//   //         }*/
//   //
//   //         BigDecimal price = cart.getPrice().multiply( new BigDecimal( cart.getQuantity() ) );
//   //         itemsList.add( buildOrderItem( cart, price ) );
//   //         // 设置商品名称
//   //         detailInfoList.add( buildOrderDetail( cart, price ).setProductName( product.getName() ) );
//   //         // 累加金额
//   //         totalAmount = totalAmount.add( price );
//   //      }
//   //
//   //      return totalAmount;
//   //   }
//
//   // 提取方法：校验购物车与订单参数是否匹配
//
//   /**
//    * 校验购物车与订单参数是否匹配
//    *
//    * @param cart          购物车单个商品信息
//    * @param productIdList 入参商品ID信息
//    * @param dto           入参
//    */
//   //   private void validateCartAgainstOrder( XShoppingCart cart, List< Long > productIdList, OrderParam dto )
//   //   {
//   //      // 校验购物车中的商品ID是否在订单参数中
//   //      if ( !productIdList.contains( cart.getProductId() ) )
//   //      {
//   //         clearShoppingCart();
//   //         throw new RuntimeException( "商品信息有变动，请重新下单" );
//   //      }
//   //      Optional< OrderProductParam > productOpt = dto.getProductList().stream().filter( item -> item.getProductId().equals( cart.getProductId() ) ).findFirst();
//   //      // 校验购物车中的商品数量是否与订单参数一致
//   //      if ( productOpt.isPresent() && !Objects.equals( cart.getQuantity(), productOpt.get().getQuantity() ) )
//   //      {
//   //         clearShoppingCart();
//   //         throw new RuntimeException( "商品信息有变动，请重新下单" );
//   //      }
//   //   }
//
//   // 提取方法：生成订单项对象
//
//   /**
//    * 生成订单明细对象
//    *
//    * @param cart  购物车单个商品信息
//    * @param price 当前商品单价 * 商品数量
//    * @return 订单明细信息
//    */
////   private XOrderItems buildOrderItem( XShoppingCart cart, BigDecimal price )
////   {
////      XOrderItems item = new XOrderItems();
////      // 商品ID
////      item.setProductId( cart.getProductId() );
////      // 商品数量
////      item.setQuantity( cart.getQuantity() );
////      // 商品单价
////      item.setPrice( cart.getPrice() );
////      // 小计金额
////      item.setTotalPrice( price );
////      item.setCreatedTime( new Date() );
////      item.setUpdatedTime( new Date() );
////      return item;
////   }
//
//   // 提取方法：生成订单详情对象
//   private OrderDetailInfoDTO buildOrderDetail( XShoppingCart cart, BigDecimal price )
//   {
//      OrderDetailInfoDTO detail = new OrderDetailInfoDTO();
//      // 商品ID
//      detail.setProductId( cart.getProductId() );
//      // 商品数量
//      detail.setQuantity( cart.getQuantity() );
//      // 商品单价
//      detail.setPrice( cart.getPrice() );
//      // 商品小计金额
//      detail.setTotalPrice( price );
//      return detail;
//   }
//
//   /**
//    * 创建订单主项
//    *
//    * @param dto         参数
//    * @param totalAmount 总价
//    * @return 订单主项信息
//    */
//   private XOrders createOrder( OrderParam dto, BigDecimal totalAmount )
//   {
//      XOrders order = new XOrders();
//      // 用户ID
//      order.setUserId( SecurityUtils.getUserId() );
//      // 总金额
//      order.setAmount( totalAmount );
//      // 收货地址
//      order.setShippingAddress( dto.getShippingAddress() );
//      // 唯一标识号
//      String paymentId = generateOrderNumber();
//      log.info( "生成的唯一的标识号：{},用户ID：{}", paymentId, SecurityUtils.getUserId() );
//      order.setPaymentId( paymentId );
//      // 购物车信息
//      order.setShoppingJson( JSONUtil.toJsonStr( dto.getProductList() ) );
//      order.setStatus( 1 );
//      order.setCreatedTime( new Date() );
//      order.setUpdatedTime( new Date() );
//      int row = xOrdersMapper.insertXOrders( order );
//      if ( row > 0 )
//      {
//         log.info( "创建订单成功：{}", order.getOrderId() );
//         return order;
//      }
//      else
//      {
//         log.warn( "创建订单失败，用户ID：{}", SecurityUtils.getUserId() );
//         throw new ServiceException( "订单创建失败" );
//      }
//   }
//
//   /**
//    * 清空购物车
//    */
//   private void clearShoppingCart()
//   {
//      int row = shoppingCartMapper.deleteXShoppingCartByUserId( SecurityUtils.getUserId() );
//      if ( row > 0 )
//      {
//         log.info( "清空购物车成功：{}", row );
//      }
//      else
//      {
//         log.warn( "清空购物车失败，可能购物车已为空。" );
//      }
//   }
//
//   /**
//    * 将商品从购物车中删除
//    */
//   private void clearShoppingCart( Long productId )
//   {
//      int row = shoppingCartMapper.deleteXShoppingCartByProductId( productId, SecurityUtils.getUserId() );
//      if ( row > 0 )
//      {
//         log.info( "清空购物车明细成功：{}", row );
//      }
//      else
//      {
//         log.warn( "清空购物车明细失败，可能购物车中当前商品已不存在已为空。" );
//      }
//   }
//
//   // 提取方法：构建返回的订单信息
//   private OrderInfoDTO buildOrderResponse( XOrders order, BigDecimal totalAmount, List< OrderDetailInfoDTO > detailInfoList, OrderParam dto )
//   {
//      OrderInfoDTO response = new OrderInfoDTO();
//      // 订单ID
//      response.setOrderId( order.getOrderId() );
//      // 总价
//      response.setAmount( totalAmount );
//      response.setCreatedTime( DateUtil.formatDateTime( order.getCreatedTime() ) );
//      // 订单明细
//      response.setOrderDetailList( detailInfoList );
//      // 骑手ID
//      response.setRiderId( order.getRiderId() );
//      // 收货地址
//      response.setShippingAddress( order.getShippingAddress() );
//      // 订单状态
//      response.setStatus( order.getStatus() );
//      // 支付方式
//      response.setPaymentType( dto.getPaymentType() );
//      // 二维码链接
//      response.setQrCode( generateOrCode( order ) );
//      // 模拟订单待支付中状态
//      redisCache.setCacheObject( order.getPaymentId(), 1 );
//      return response;
//   }
//
//   /**
//    * 生成链接地址
//    */
//   private String generateOrCode( XOrders order )
//   {
//      String paymentId = order.getPaymentId();
//      if ( StrUtil.isBlank( paymentId ) )
//      {
//         throw new ServiceException( "系统内部错误" );
//      }
//      return "http://127.0.0.1:8081/orders/testPay?paymentId=" + paymentId;
//   }
//
//   /**
//    * 支付 （验证请求是否是支付宝或者微信通知的，防止被别人劫持，恶意修改订单信息）
//    * 1、查询订单状态（订单是否为待支付）
//    * 2、查询支付状态（确认是否已被支付过，但是订单状态未修改）
//    * 3、修改订单状态为已支付
//    * 4、订单支付成功后，需要记录，保证商品的库存足够
//    *
//    * @param param 支付参数
//    * @return 结果
//    */
//   @Override
//   @Transactional(rollbackFor = Exception.class)
//   public XResponseResult pay( PayParam param )
//   {
//      // 1、查询数据库订单状态
//      XOrders xOrders = selectXOrdersByOrderId( param.getOrderId() );
//      if ( xOrders == null )
//      {
//         return XResponseResult.fail( "订单不存在" );
//      }
//      if ( xOrders.getStatus() >= 2 )
//      {
//         return XResponseResult.success( "订单已支付" );
//      }
//      // TODO 2、调用支付宝或者微信接口，查询支付状态
//
//      // 3、修改订单状态为已支付
//      // 创建一个新的订单对象，用来更新数据（防止全表更新）
//      XOrders updateOrder = new XOrders();
//      updateOrder.setStatus( 2 );
//      updateOrder.setPayTime( new Date() );
//      updateOrder.setOrderId( updateOrder.getOrderId() );
//      updateOrder.setUpdatedTime( new Date() );
//      updateXOrders( updateOrder );
//      // 4、记录支付信息
//      XFinancials xFinancials = new XFinancials();
//      xFinancials.setAmount( xOrders.getAmount() );
//      xFinancials.setCreatedTime( new Date() );
//      xFinancials.setUpdatedTime( new Date() );
//      xFinancials.setMerchantId( xOrders.getMerchantId() );
//      xFinancials.setTransactionType( 1L );
//      int row = xFinancialsMapper.insertXFinancials( xFinancials );
//      if ( row > 0 )
//      {
//         log.info( "支付信息记录成功：{}", row );
//         return XResponseResult.success();
//      }
//      else
//      {
//         log.info( "支付信息记录失败：{}", row );
//         // TODO 财务信息插入失败，需要考虑重试 ( 可以使用MQ ， 消费成功之后从队列中删除，否则一直在队列中)
//      }
//      return XResponseResult.success();
//   }
//
//   /**
//    * 支付（测试）
//    * <p>
//    * 1、创建订单成功之后，返回一个支付链接（二维码），redis中根据支付id记录一个状态 1 待支付
//    * 2、模拟用户支付，调用第一步的链接
//    * 3、修改redis中的支付id对应的状态为2 已支付 ，修改成功之后自动再去调用另一个接口（支付宝、微信的支付通知接口）现在模拟的是本地自己的
//    * 4、通过回调接口，修改数据库订单状态（需要先校验redis中的状态是否为已支付）
//    *
//    * @param paymentId 订单唯一标识符（微信、支付宝支付状态可以用这个查询，数据库的状态也可用这个查询）
//    * @return 结果
//    */
//   @Override
//   public XResponseResult testPay( String paymentId )
//   {
//      // 先查询支付状态(这里模拟的是 微信、支付宝的支付信息)
//      Object object = redisCache.getCacheObject( paymentId );
//      if ( object == null )
//      {
//         return XResponseResult.fail( "订单不存在" );
//      }
//      if ( !object.equals( 1 ) )
//      {
//         return XResponseResult.success( "订单数据异常，请检查，订单支付ID：{}", paymentId );
//      }
//      // 修改redis中的支付状态
//      redisCache.setCacheObject( paymentId, 2 );
//      Object object1 = redisCache.getCacheObject( paymentId );
//      if ( object1.equals( 2 ) )
//      {
//         log.info( "支付成功，支付id：{},调用通知回调", paymentId );
//         noticePay( paymentId );
//      }
//      else
//      {
//         log.info( "redis中支付状态为未修改，支付id：{}", paymentId );
//      }
//      return XResponseResult.success();
//   }
//
//   public void noticePay( String paymentId )
//   {
//      // 1、查询订单状态
//      // 查询数据库订单是否存在
//      XOrders xOrders = xOrdersMapper.findOrderByPaymentId( paymentId );
//      if ( xOrders == null )
//      {
//         log.info( "支付id：{}对应的订单不存在，请检查", paymentId );
//         return;
//      }
//      if ( xOrders.getStatus() == 1 )
//      {
//         // 修改订单支付状态，并且同步修改redis中的支付信息
//         xOrders.setStatus( 2 );
//         int row = xOrdersMapper.updateXOrders( xOrders );
//         log.info( "订单支付状态修改完成,影响数据条数：{}", row );
//      }
//      else
//      {
//         log.info( "数据库订单状态不为待支付，请手动检查,订单ID：{},支付ID：{}", xOrders.getOrderId(), paymentId );
//      }
//
//   }
//
//   /**
//    * 校验订单参数
//    *
//    * @param dto 订单参数
//    * @return 结果
//    */
//   private String validateOrderParam( OrderParam dto )
//   {
//      if ( dto == null )
//      {
//         return "参数为空";
//      }
//      if ( CollUtil.isEmpty( dto.getProductList() ) )
//      {
//         return "商品信息不能为空";
//      }
//      for ( OrderProductParam item : dto.getProductList() )
//      {
//         if ( item.getProductId() == null )
//         {
//            return "商品ID不能为空";
//         }
//         if ( item.getQuantity() == null || item.getQuantity() <= 0 )
//         {
//            return "商品数量不能小于0";
//         }
//      }
//      if ( StrUtil.isBlank( dto.getShippingAddress() ) )
//      {
//         return "收货地址为空";
//      }
//      if ( dto.getAmount() == null || dto.getAmount().compareTo( BigDecimal.ZERO ) <= 0 )
//      {
//         return "订单总金额不能小于0";
//      }
//      if ( StrUtil.isBlank( dto.getPaymentType() ) )
//      {
//         return "请选择支付方式";
//      }
//      return null;
//   }
//
//   // 生成唯一订单号
//   public static String generateOrderNumber()
//   {
//      // 1. 当前时间戳部分（年月日时分秒）
//      SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmss" );
//      String timestamp = sdf.format( new Date() );
//
//      // 2. 随机数部分（6位数字）
//      Random random = new Random();
//      int randomNumber = random.nextInt( 900000 ) + 100000; // 生成 100000 - 999999 的随机数
//
//      // 3. 组合订单号
//      return timestamp + randomNumber;
//   }
//
//}
