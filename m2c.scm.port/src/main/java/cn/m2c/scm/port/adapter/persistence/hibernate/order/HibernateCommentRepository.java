package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.comment.Comment;
import cn.m2c.scm.domain.model.order.comment.CommentRepository;


/**
 * 
 * @ClassName: HibernateOrderRepository
 * @Description:评论
 * @author moyj
 * @date 2017年6月30日 下午1:57:00
 *
 */
@Repository
public class HibernateCommentRepository extends HibernateSupperRepository implements CommentRepository{

	@Override
	public void save(Comment comment) throws NegativeException {
		try{
			this.session().merge(comment);
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOrderRepository.add(..) exception.", e);
		}
		
	}

	@Override
	public Comment findT(String commentId) throws NegativeException {
		try{
			assertArgumentNotEmpty(commentId, "HibernateCommentRepository.findT(..) commentId is must be not null");
			Comment comment = (Comment)this.session().createQuery(" FROM Comment WHERE commentId = :commentId ").setParameter("commentId", commentId).uniqueResult();
			return comment;
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateCommentRepository.findT(..) exception.", e);
		}
		
	}
	
	

}
