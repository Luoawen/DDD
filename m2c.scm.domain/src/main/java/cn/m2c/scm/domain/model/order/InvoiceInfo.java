package cn.m2c.scm.domain.model.order;

import cn.m2c.common.StringUtil;
import cn.m2c.ddd.common.domain.model.ValueObject;
import org.apache.commons.lang3.StringUtils;

/***
 * 发票信息值对象
 *
 * @author fanjc
 *         created date 2017年10月17日
 *         copyrighted@m2c
 */
public class InvoiceInfo extends ValueObject {
    /**
     * 抬头
     **/
    private String header;
    /**
     * 发票名称
     **/
    private String name;
    /**
     * 纳税人识别码
     **/
    private String code;
    /**
     * 发票说明
     **/
    private String invoiceDes;

    /**
     * 发票类型， 0个人， 1公司
     **/
    private int type = -1;

    public InvoiceInfo() {
        super();
    }

    public InvoiceInfo(String header, String name, String code, String des
            , int invoiceType) {
        this.code = code;
        this.header = header;
        this.name = name;
        invoiceDes = des;
        this.type = invoiceType;
        if (StringUtils.isNotEmpty(header)) {
            if (StringUtil.isEmpty(code)) {
                type = 0;
            } else {
                type = 1;
            }
        }

    }
    /***
     * 检查发票类型
     */
    public void checkType() {
    	if (StringUtils.isNotEmpty(header)) {
            if (StringUtil.isEmpty(code)) {
                type = 0;
            } else {
                type = 1;
            }
        }
    }
}
