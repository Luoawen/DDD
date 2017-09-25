package cn.m2c.goods.domain.templet;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class TransportFee  extends ConcurrencySafeEntity{
	private static final long serialVersionUID = 1759974280588400683L;
	
	private String transportFeeId;
	private String modelName;
	private Long fee;
	private String dealerId;
	private Integer transportFeeStatus = 1;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	
	public TransportFee() {
		super();
	}
	
	public TransportFee(String transportFeeId, String modelName, Long fee,
			String dealerId) {
		super();
		this.transportFeeId = transportFeeId;
		this.modelName = modelName;
		this.fee = fee;
		this.dealerId = dealerId;
	}

	public String getTransportFeeId() {
		return transportFeeId;
	}
	public String getModelName() {
		return modelName;
	}
	public Long getFee() {
		return fee;
	}

	public String getDealerId() {
		return dealerId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public Integer getTransportFeeStatus() {
		return transportFeeStatus;
	}

	/**
	 * 添加
	 * @param transportFeeId
	 * @param modelName
	 * @param fee
	 * @param dealerId
	 */
	public void add(String transportFeeId, String modelName, Long fee,
			String dealerId) {
		this.transportFeeId = transportFeeId;
		this.modelName = modelName;
		this.fee = fee;
		this.dealerId = dealerId;
	}

	public void update(String modelName, Long fee) {
		this.modelName = modelName;
		this.fee = fee;
	}

	public void del() {
		this.transportFeeStatus = 2;
	}
	
	
}
