package cn.m2c.scm.application.dealer.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerBean {

	 @ColumnAlias(value = "dealer_name")
	 private String dealerName;
	 
	 @ColumnAlias(value = "dealer_id")
	 private String dealerId;
	 
	 @ColumnAlias(value = "user_name")
	 private String userName;
	 
	 @ColumnAlias(value = "user_phone")
	 private String userPhone;
	 

}
