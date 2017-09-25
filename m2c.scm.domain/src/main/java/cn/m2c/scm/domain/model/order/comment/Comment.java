package cn.m2c.scm.domain.model.order.comment;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * @ClassName: Comment
 * @Description: 评论
 * @author moyj
 * @date 2017年6月30日 上午10:34:19
 *
 */
public class Comment extends ConcurrencySafeEntity {
	private static final long serialVersionUID = -3289870828276134679L;
	private String commentId;		//评论编号
	private String orderId;			//订单ID
	private String orderNo;			//订单编号
	private String goodsId;			//货品编号
	private String goodsName;		//货品名称
	private Long goodsUnitPrice;	//货品单价
	private String goodsIcon;		//货品图标
	private String dealerId;		//供应商编号
	private String dealerName;		//供应商名称
	private String buyerId;			//买家编号
	private String buyerName;		//买家名称
	private String buyerIcon;		//买家头像
	private Integer commentLevel;	//评论级别  1好  2中  3差
	private Integer starLevel;		//星级 1 2 3 4 5
	private String commentContent;	//评论内容
	private String replyContent;	//回复内容
	private Integer replyStatus;	//回复状态  1未回复  2 已回复
	private Integer topStatus;		//置顶状态  1不置顶  2 置顶
	private Integer hideStatus;		//隐藏状态  1不隐藏 2 隐藏
	private Long   commentTime;	    //评论时间
	private Long   modCommentTime;  //修改评论时间
	private Long   replyTime;	    //回复时间
	private Long buyingTime;		//购买时间
	private Integer deletedByUser;	//是否已被用户删除         1否 2是
	private Integer deletedByAdmin; //是否已被管理员删除    1否  2是
	private Integer commentStatus;	//状态  1正常 2删除
	
	//创建
	public void appCreate(
			String commentId,
			String orderId,
			String orderNo,
			String goodsId,
			String goodsName,
			Long goodsUnitPrice,
			String goodsIcon,
			String dealerId,
			String dealerName,
			String buyerId,
			String buyerName,
			String buyerIcon,
			Integer commentLevel,
			Integer starLevel,
			String commentContent,
			Long buyingTime)throws NegativeException{
		this.commentId = commentId;
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerIcon = buyerIcon;
		this.commentLevel = commentLevel;
		this.starLevel = starLevel;
		this.commentContent = commentContent;
		this.buyingTime = buyingTime;
		this.commentTime = System.currentTimeMillis();
		this.replyStatus = ReplyStatus.UNREPLY.getId();
		this.topStatus = TopStatus.UNTOP.getId();
		this.hideStatus = HideStatus.SHOW.getId();
		this.commentStatus = CommentStatus.NORMAL.getId();	
		this.deletedByAdmin =1;
		this.deletedByUser = 1;
		this.modCommentTime = commentTime;
		this.goodsUnitPrice = goodsUnitPrice;
		this.goodsIcon = goodsIcon;
		
		//已评论事件
		Commented commented = new Commented(this.commentId,this.goodsId,this.buyerId);
		DomainEventPublisher.instance().publish(commented);
	}
	//修改
	public void appModComment (
			String commentContent,
			Integer commentLevel,
			Integer starLevel,
			Long deadline) throws NegativeException{
		if(commentContent != null && commentContent.trim().length() > 0){
			this.commentContent = commentContent;
		}
		if(commentLevel != null){
			this.commentLevel = commentLevel;
		}
		if(starLevel != null){
			this.starLevel =  starLevel;
		}
		if(this.modCommentTime == null || this.modCommentTime + deadline < System.currentTimeMillis()){
			throw new NegativeException(NegativeCode.COMMENT_UNALLOW_MOD,"评论48小时 后不允许修改");
		}
		this.modCommentTime = System.currentTimeMillis();
	}
	//创建
	public void adminCreate(
				String commentId,
				String orderId,
				String goodsId,
				String goodsName,
				String dealerId,
				String dealerName,
				String buyerId,
				String buyerName,
				String buyerIcon,
				Integer commentLevel,
				Integer starLevel,
				String commentContent,
				Long buyingTime,
				Long commentTime
				) throws NegativeException{
		this.commentId = commentId;
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerIcon = buyerIcon;
		this.commentLevel = commentLevel;
		this.starLevel = starLevel;
		this.commentContent = commentContent;
		this.buyingTime = buyingTime;
		this.commentTime = commentTime;
		this.replyStatus = ReplyStatus.UNREPLY.getId();
		this.topStatus = TopStatus.UNTOP.getId();
		this.hideStatus = HideStatus.SHOW.getId();
		this.commentStatus = CommentStatus.NORMAL.getId();
		this.deletedByAdmin =1;
		this.deletedByUser = 1;
		this.modCommentTime = commentTime;
		//已评论事件
		Commented commented = new Commented(this.commentId,this.goodsId,this.buyerId);
		DomainEventPublisher.instance().publish(commented);
		
	}
	//回复
	public void reply(String replyContent) throws NegativeException{
		if(this.commentStatus != CommentStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		if(this.deletedByAdmin != null && this.deletedByAdmin == 2 ){
			throw new NegativeException(NegativeCode.COMMENT_DELETED_BY_ADMIN,"被管理员删除的评论不能回复");
		}
		if(this.replyStatus != null && this.replyStatus  != ReplyStatus.REPLYED.getId()){
			this.replyContent = replyContent;
			this.replyStatus = ReplyStatus.REPLYED.getId();
			this.replyTime = System.currentTimeMillis();
		}else{
			throw new NegativeException(NegativeCode.COMMENT_REPLYED," 评论已经回复过");
		}
		
	}
	//置顶
	public void top(Integer topFlag) throws NegativeException{
		assertArgumentNotNull(topFlag, "topFlag is not be null.");
		assertArgumentRange(topFlag,1,2,"topFlag is not in 1 2.");
		if(this.commentStatus != CommentStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		if(topFlag == 1){
			this.topStatus = TopStatus.TOP.getId();
		}else if(topFlag == 2){
			this.topStatus = TopStatus.UNTOP.getId();
		}
		
	}
	//隐藏
	public void hide(Integer topFlag) throws NegativeException{
		assertArgumentNotNull(topFlag, "topFlag is not be null.");
		assertArgumentRange(topFlag,1,2,"topFlag is not in 1 2.");
		if(this.commentStatus != CommentStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		this.hideStatus = HideStatus.HIDE.getId();
		if(topFlag == 1){
			this.hideStatus = HideStatus.HIDE.getId();
		}else if(topFlag == 2){
			this.hideStatus = HideStatus.SHOW.getId();
		}
	}
	//用户删除评论
	public void deletedByUser() throws NegativeException{
		if(this.deletedByUser == 2){
			throw new NegativeException(NegativeCode.COMMENT_DELETED_BY_USER,"评论已被删除");
		}
		this.deletedByUser = 2;
	}
	//管理员删除评论
	public void deletedByAdmin() throws NegativeException{
		if(this.deletedByAdmin == 2){
			throw new NegativeException(NegativeCode.COMMENT_DELETED_BY_ADMIN,"评论已被删除");
		}
		this.deletedByAdmin = 2;
	}
	//删除
	public void delete()  throws NegativeException{
		if(this.commentStatus != CommentStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,"评论不存在");
		}
		this.commentStatus = CommentStatus.DELETED.getId();
	}
	
	
	public String getCommentId() {
		return commentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public Integer getCommentStatus() {
		return commentStatus;
	}
	public Integer getTopStatus() {
		return topStatus;
	}
	public Long getCommentTime() {
		return commentTime;
	}
	public Long getReplyTime() {
		return replyTime;
	}
	public Integer getCommentLevel() {
		return commentLevel;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public Integer getReplyStatus() {
		return replyStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getHideStatus() {
		return hideStatus;
	}
	public String getBuyerIcon() {
		return buyerIcon;
	}
	public Long getBuyingTime() {
		return buyingTime;
	}
	public String getDealerId() {
		return dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	public String getOrderNo() {
		return orderNo;
	}

	public Integer getDeletedByUser() {
		return deletedByUser;
	}

	public Integer getDeletedByAdmin() {
		return deletedByAdmin;
	}
	public Long getModCommentTime() {
		return modCommentTime;
	}
	public Long getGoodsUnitPrice() {
		return goodsUnitPrice;
	}
	public String getGoodsIcon() {
		return goodsIcon;
	}
	
	
	
	

}
