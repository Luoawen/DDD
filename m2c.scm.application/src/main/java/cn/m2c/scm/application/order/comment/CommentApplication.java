package cn.m2c.scm.application.order.comment;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.order.comment.command.AdminCreateCommand;
import cn.m2c.scm.application.order.comment.command.AppCreateCommand;
import cn.m2c.scm.application.order.comment.command.AppModCommentCommand;
import cn.m2c.scm.application.order.comment.command.DeleteByAdminCommand;
import cn.m2c.scm.application.order.comment.command.DeleteCommand;
import cn.m2c.scm.application.order.comment.command.DeteteByUserCommand;
import cn.m2c.scm.application.order.comment.command.HideCommand;
import cn.m2c.scm.application.order.comment.command.ReplyCommand;
import cn.m2c.scm.application.order.comment.command.TopCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.comment.Comment;
import cn.m2c.scm.domain.model.order.comment.CommentRepository;
import cn.m2c.scm.domain.model.order.service.CommentService;
import cn.m2c.scm.goods.interfaces.GoodService;



/**
 * 
 * @ClassName: CommentApplication
 * @Description: 评论
 * @author moyj
 * @date 2017年7月12日 下午2:39:28
 *
 */
@Service
@Transactional
public class CommentApplication {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private GoodService goodsService;
	@Autowired
	private CommentService commentService;
	
	
	//新增
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void appCreate(AppCreateCommand command) throws NegativeException{
		String commentId = command.getCommentId();
		String buyerId = command.getBuyerId();
		String content = command.getCommentContent();
		Integer starLevel = command.getStarLevel();
		String orderId = command.getOrderId();
		Integer commentLevel = 1;
		if(starLevel == 1 || starLevel == 2 || starLevel == 3){
			commentLevel = 3;
		}else if(starLevel == 4){
			commentLevel = 2;
		}else if(starLevel == 5){
			commentLevel = 1;
		}
		commentService.appCreate(commentId, buyerId, content, commentLevel,starLevel,orderId);
		
	}
	//新增
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void adminCreate(AdminCreateCommand command) throws NegativeException{
		String commentId = command.getCommentId();
		String buyerName = command.getBuyerName();
		String buyerIcon = command.getBuyerIcon();
		String commentContent = command.getCommentContent();
		String goodsId = command.getGoodsId();
		String goodsName = command.getGoodsName();
		Integer starLevel = command.getStarLevel();
		Long buyingTime = command.getBuyingTime();
		Long commentTime = command.getCommentTime();
		Integer commentLevel = 1;
		if(starLevel == 1 || starLevel == 2 || starLevel == 3){
			commentLevel = 3;
		}else if(starLevel == 4){
			commentLevel = 2;
		}else if(starLevel == 5){
			commentLevel = 1;
		}
		//远程调用商家领域 获取货品信息
		Map<String,Object> goodsMap = goodsService.getPropertyInfo(goodsId, null);
		if(goodsMap == null){
			throw new NegativeException(NegativeCode.REMOTE_GET_GOODS_FAIL,"远程获取货品信息失败");
		}
		String dealerId = goodsMap.get("dealerId") == null?null:(String)goodsMap.get("dealerId");
		String dealerName = goodsMap.get("dealerName") == null?null:(String)goodsMap.get("dealerName");

		Comment comment = new Comment();
		comment.adminCreate(commentId, null, goodsId, goodsName, dealerId, dealerName, null, buyerName, buyerIcon, commentLevel,starLevel,commentContent,buyingTime, commentTime);
		commentRepository.save(comment);
	}
	//回复
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void reply(ReplyCommand command) throws NegativeException{
		Comment comment = commentRepository.findT(command.getCommentId());
		if(comment == null){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		comment.reply(command.getReplyContent());
		commentRepository.save(comment);
	}
	//置顶
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void top(TopCommand command) throws NegativeException{
		List<String> commentIdList = command.getCommentIdList();
		if(commentIdList == null || commentIdList.size() == 0){
			throw new NegativeException(NegativeCode.OPER_NOT_CHOICE_COMMENT,"请选择要操作的评论");
		}
		for(String commentId : commentIdList){
			Comment comment = commentRepository.findT(commentId);
			if(comment == null){
				throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
			}
			comment.top(command.getTopFlag());
			commentRepository.save(comment);
		}
		
	}
	//删除
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void delete(DeleteCommand command) throws NegativeException{
		List<String> commentIdList = command.getCommentIdList();
		if(commentIdList == null || commentIdList.size() == 0){
			throw new NegativeException(NegativeCode.OPER_NOT_CHOICE_COMMENT,"请选择要操作的评论");
		}
		for(String commentId : commentIdList){
			Comment comment = commentRepository.findT(commentId);
			if(comment == null){
				throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
			}
			comment.delete();
			commentRepository.save(comment);
		}
	}
	//隐藏
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void hide(HideCommand command) throws NegativeException{
		List<String> commentIdList = command.getCommentIdList();
		if(commentIdList == null || commentIdList.size() == 0){
			throw new NegativeException(NegativeCode.OPER_NOT_CHOICE_COMMENT,"请选择要操作的评论");
		}
		for(String commentId : commentIdList){
			Comment comment = commentRepository.findT(commentId);
			if(comment == null){
				throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
			}
			comment.hide(command.getHideFlag());
			commentRepository.save(comment);
		}
	}
	//用户删除
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void deleteByUser(DeteteByUserCommand command) throws NegativeException{
		String commentId = command.getCommentId();
		Comment comment = commentRepository.findT(commentId);
		if(comment == null){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		comment.deletedByUser();
		commentRepository.save(comment);
	}	
	//管理员删除
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void deleteByAdmin(DeleteByAdminCommand command) throws NegativeException{
		List<String> commentIdList = command.getCommentIdList();
		if(commentIdList == null || commentIdList.size() == 0){
			throw new NegativeException(NegativeCode.OPER_NOT_CHOICE_COMMENT,"请选择要操作的评论");
		}
		for(String commentId : commentIdList){
			Comment comment = commentRepository.findT(commentId);
			if(comment == null){
				throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
			}
			comment.deletedByAdmin();
			commentRepository.save(comment);
		}
	}
	
	//修改
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void appModComment(AppModCommentCommand command) throws NegativeException{
		String commentId = command.getCommentId();
		String content = command.getCommentContent();
		Integer starLevel = command.getStarLevel();
		Integer commentLevel = 1;
		Long deadline = command.getDeadline();
		if(starLevel == 1 || starLevel == 2 || starLevel == 3){
			commentLevel = 3;
		}else if(starLevel == 4){
			commentLevel = 2;
		}else if(starLevel == 5){
			commentLevel = 1;
		}
		Comment comment = commentRepository.findT(commentId);
		if(comment == null){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		comment.appModComment(content, commentLevel, starLevel,deadline);
		commentRepository.save(comment);
	}
	

}
