package cn.m2c.scm.domain.model.goods.templet;

public interface TransportFeeRepository {

	void save(TransportFee transportFee);

	TransportFee getDetail(String transportFeeId);
	
}
