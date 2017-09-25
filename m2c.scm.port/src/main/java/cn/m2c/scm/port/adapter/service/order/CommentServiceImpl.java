package cn.m2c.scm.port.adapter.service.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.m2c.scm.application.order.order.query.SpringJdbcOrderQuery;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.Order;
import cn.m2c.scm.domain.model.order.comment.Comment;
import cn.m2c.scm.domain.model.order.service.CommentService;
import cn.m2c.scm.port.adapter.persistence.hibernate.order.HibernateCommentRepository;
import cn.m2c.scm.port.adapter.persistence.hibernate.order.HibernateOrderRepository;
import cn.m2c.users.interfaces.dubbo.UserService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private HibernateCommentRepository hibernateCommentRepository;
	@Autowired
	private HibernateOrderRepository hibernateOrderRepository;
	@Autowired
	private SpringJdbcOrderQuery springJdbcOrderQuery;
	@Autowired
	private UserService userService;
	
	@Override
	public void appCreate(
			String commentId,
			String buyerId,
			String content,
			Integer commentLevel,
			Integer starLevel,
			String orderId) throws NegativeException {
		
		Map<String,Object> orderMap = springJdbcOrderQuery.findT(orderId);
		if(orderMap == null){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		String goodsId = orderMap.get("goodsId") == null?null:(String)orderMap.get("goodsId");
		String goodsName = orderMap.get("goodsName") == null?null:(String)orderMap.get("goodsName");
		String dealerId = orderMap.get("dealerId") == null?null:(String)orderMap.get("dealerId");
		String dealerName = orderMap.get("dealerName") == null?null:(String)orderMap.get("dealerName");
		String buyerName = orderMap.get("buyerName") == null?null:(String)orderMap.get("buyerName");
		Long buyingTime = orderMap.get("payEndTime") == null?null:(Long)orderMap.get("payEndTime");
		//调用用户中心 获取用户信息
		Map<String,Object> userMap = userService.getUserInfo(buyerId);
		if(userMap == null){
			throw new NegativeException(NegativeCode.REMOTE_GET_USER_FAIL,"远程获取用户信息失败");
		}
		String buyerIcon = userMap.get("icon") == null?null:(String)userMap.get("icon");
		
		//变更订单为已经评论
		Order order = hibernateOrderRepository.findT(orderId);
		if(order == null){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		order.commentOrder();
		hibernateOrderRepository.save(order);
		
		//保存评论
		Comment comment = new Comment();
		comment.appCreate(commentId, orderId,order.getOrderNo(), goodsId, goodsName,order.getGoodsUnitPrice(),order.getGoodsIcon(), dealerId, dealerName, buyerId, buyerName, buyerIcon, commentLevel,starLevel, content, buyingTime);
		hibernateCommentRepository.save(comment);
		
		
		
	}

}
