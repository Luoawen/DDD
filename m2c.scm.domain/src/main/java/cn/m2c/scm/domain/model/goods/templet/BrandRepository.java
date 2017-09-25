package cn.m2c.scm.domain.model.goods.templet;

public interface BrandRepository {

	void save(Brand brand);

	Brand getBrandDetail(String brandId);


}
