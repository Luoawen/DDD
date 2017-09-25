package cn.m2c.scm.domain.model.order.comment;
import cn.m2c.scm.domain.NegativeException;
/**
 * 
 * @ClassName: CommentRepository
 * @Description: 评论
 * @author moyj
 * @date 2017年6月30日 上午11:46:47
 *
 */
public interface CommentRepository {
	/**
	 * 创建/更新评论
	 * @param order
	 * @return
	 * @throws NegativeException
	 */
	public void save(Comment comment) throws NegativeException;
	
	/**
	 * 通过订单编号获取订单信息
	 * @param orderId
	 * @return
	 * @throws NegativeException
	 */
	public Comment findT(String commentId) throws NegativeException;
}
