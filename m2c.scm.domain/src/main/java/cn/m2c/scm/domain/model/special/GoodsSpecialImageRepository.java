package cn.m2c.scm.domain.model.special;

public interface GoodsSpecialImageRepository {

	GoodsSpecialImage queryGoodsSpecialImage(String imageId);

	void saveImage(GoodsSpecialImage goodsSpecialImage);
	
	//查询第一条数据
	GoodsSpecialImage queryGoodsSpecialImageById();
}
