package cn.m2c.goods.domain.templet;

public interface PropertyRepository {

	void save(Property property);

	Property getDetail(String propertyId);
	
}
