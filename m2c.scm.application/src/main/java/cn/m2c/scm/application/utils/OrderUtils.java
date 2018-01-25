package cn.m2c.scm.application.utils;

/**
 * 订单工具类
 */
public class OrderUtils {
    public static String getStatusStr(Integer status) {
        String statusStr = "";
        if (null != status) {
            switch (status) {
                case 0:
                    statusStr = "待付款";
                    break;
                case 1:
                    statusStr = "待发货";
                    break;
                case 2:
                    statusStr = "待收货";
                    break;
                case 3:
                    statusStr = "已完成";
                    break;
                case 4:
                    statusStr = "交易完成";
                    break;
                case 5:
                    statusStr = "交易关闭";
                    break;
                case -1:
                    statusStr = "已取消";
                    break;
            }
        }
        return statusStr;
    }

    // 订单类型，0换货， 1退货，2仅退款
    public static String getAfterType(Integer status) {
        String statusStr = "";
        if (null != status) {
            switch (status) {
                case 0:
                    statusStr = "换货";
                    break;
                case 1:
                    statusStr = "退货退款";
                    break;
                case 2:
                    statusStr = "仅退款";
                    break;
            }
        }
        return statusStr;
    }

    // 状态：待商家同意，待顾客寄回商品，待商家确认退款，待商家发货，待顾客收货，售后已完成，售后已取消，商家已拒绝
    public static String getAfterStatusStr(Integer afterType, Integer status) {
        String statusStr = "";
        if (null != afterType && null != status) {
            switch (afterType) {
                case 0://换货
                    switch (status) {
                        case -1:
                            statusStr = "售后已取消";
                            break;
                        case 3:
                            statusStr = "商家已拒绝";
                            break;
                        case 1:
                            statusStr = "待商家同意";
                            break;
                        case 4:
                            statusStr = "待顾客寄回商品";
                            break;
                        case 5:
                        case 6:
                            statusStr = "待商家发货";
                            break;
                        case 7:
                            statusStr = "待顾客收货";
                            break;
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                            statusStr = "售后已完成";
                            break;
                    }
                    break;
                case 1://退货
                    switch (status) {
                        case -1:
                            statusStr = "售后已取消";
                            break;
                        case 3:
                            statusStr = "商家已拒绝";
                            break;
                        case 0:
                            statusStr = "待商家同意";
                            break;
                        case 4:
                            statusStr = "待顾客寄回商品";
                            break;
                        case 5:
                        case 6:
                            statusStr = "待商家确认退款";
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                            statusStr = "售后已完成";
                            break;
                    }
                    break;
                case 2://仅退款
                    switch (status) {
                        case -1:
                            statusStr = "售后已取消";
                            break;
                        case 3:
                            statusStr = "商家已拒绝";
                            break;
                        case 2:
                            statusStr = "待商家同意";
                            break;
                        case 4:
                            statusStr = "待商家确认退款";
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                            statusStr = "售后已完成";
                            break;
                    }
                    break;
            }
        }
        return statusStr;
    }

    public static String getPayStatusStr(Integer orderStatus) {
        String payStatusStr = "";
        if (null != orderStatus) {
            if (orderStatus != 0 && orderStatus != -1) {
                payStatusStr = "已支付";
            } else {
                payStatusStr = "未支付";
            }
        }
        return payStatusStr;
    }
    
    public static String getPayStatusStr2(Integer orderStatus) {
    	String payStatusStr = "";
        if (null != orderStatus) {
            if(orderStatus == -1) {
            	payStatusStr = "已取消";
            }
            if(orderStatus == 0) {
                payStatusStr = "待支付";
            }
            if (orderStatus >= 1 && orderStatus <= 5) {
                payStatusStr = "已支付";
            }
        }
        return payStatusStr;
    }
    
    public static String getPayWayStr(Integer payWay) {
    	String payWayStr = "";
    	if(null != payWay) {
    		if(payWay == 1) {
    			payWayStr = "支付宝";
			}
			if(payWay == 2) {
				payWayStr = "微信";
			}
			if(payWay == 3) {
				payWayStr = "其他";
			}
    	}
    	return payWayStr;
    }
}
