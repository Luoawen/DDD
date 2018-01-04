package cn.m2c.scm.port.adapter.persistence.hibernate.special;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.special.GoodsSpecialImage;
import cn.m2c.scm.domain.model.special.GoodsSpecialImageRepository;

/**
 * 特惠价图片 
 */
@Repository
public class HibernateGoodsSpecialImageRepository extends HibernateSupperRepository implements GoodsSpecialImageRepository{

	@Override
	public GoodsSpecialImage queryGoodsSpecialImage(String imageId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_goods_special_image WHERE image_id =:image_id"); 
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecialImage.class);
		query.setParameter("image_id", imageId);
		return (GoodsSpecialImage) query.uniqueResult();
	}

	@Override
	public void saveImage(GoodsSpecialImage goodsSpecialImage) {
		this.session().save(goodsSpecialImage);
	}

	@Override
	public GoodsSpecialImage queryGoodsSpecialImageById() {
		StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_goods_special_image WHERE id = 1"); 
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecialImage.class);
		return (GoodsSpecialImage) query.uniqueResult();
	}
}
