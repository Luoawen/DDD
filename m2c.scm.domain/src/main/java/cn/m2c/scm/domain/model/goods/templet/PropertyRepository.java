package cn.m2c.scm.domain.model.goods.templet;

public interface PropertyRepository {

	void save(Property property);

	Property getDetail(String propertyId);
	
}
