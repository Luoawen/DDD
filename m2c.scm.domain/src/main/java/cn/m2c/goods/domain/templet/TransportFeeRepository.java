package cn.m2c.goods.domain.templet;

public interface TransportFeeRepository {

	void save(TransportFee transportFee);

	TransportFee getDetail(String transportFeeId);
	
}
