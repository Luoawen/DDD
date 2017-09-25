package cn.m2c.goods.domain.templet;

public interface BrandRepository {

	void save(Brand brand);

	Brand getBrandDetail(String brandId);


}
