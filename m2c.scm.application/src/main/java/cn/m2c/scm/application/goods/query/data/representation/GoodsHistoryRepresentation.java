package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsHistoryBean;
import cn.m2c.scm.application.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 商品变更历史
 */
public class GoodsHistoryRepresentation {
    // 变更时间
    private String changeTime;
    // 变更类型，1：修改商品分类，2：修改拍获价，3：修改供货价，4：增加sku
    private Integer changeType;
    // 变更内容
    private String changeContent;
    // 变更前
    private String beforeContent;
    // 变更后
    private String afterContent;

    public GoodsHistoryRepresentation(GoodsHistoryBean history) {
        this.changeTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getCreateTime());
        this.changeType = history.getChangeType();
        Map beforeMap = JsonUtils.toMap(history.getBeforeContent());
        Map afterMap = JsonUtils.toMap(history.getAfterContent());
        if (history.getChangeType() == 1) {
            this.changeContent = "修改商品分类";

            // 变更前 {"goodsClassifyName":"手机,iOS系统","goodsClassifyId":"SPFL6104939BD41E4E2CB4715F19306C3478","serviceRate":"0","settlementMode":"1"}
            StringBuffer beforeSb = new StringBuffer();
            beforeSb.append(beforeMap.get("goodsClassifyName"));
            Integer settlementMode = Integer.parseInt(beforeMap.get("settlementMode").toString());
            if (settlementMode == 2) {
                beforeSb.append("(费率").append(beforeMap.get("serviceRate")).append("%)");
            }
            this.beforeContent = beforeSb.toString();

            // 变更后 {"goodsClassifyName":"手机,iOS系统","goodsClassifyId":"SPFL6104939BD41E4E2CB4715F19306C3478","serviceRate":"0"}
            StringBuffer afterSb = new StringBuffer();
            afterSb.append(afterMap.get("goodsClassifyName"));
            if (settlementMode == 2) {
                afterSb.append("(费率").append(afterMap.get("serviceRate")).append("%)");
            }
            this.afterContent = afterSb.toString();

        } else if (history.getChangeType() == 2) {
            this.changeContent = "修改拍获价";
            // 变更前 {"skuName":"5寸,红色","photographPrice":9970000,"skuId":"20171205155603147254"}
            StringBuffer beforeSb = new StringBuffer();
            String beforePrice = Utils.moneyFormatCN(Long.parseLong(String.valueOf(beforeMap.get("photographPrice"))));
            beforeSb.append(beforePrice).append("元");
            if (null != beforeMap.get("skuName") && !"".equals(beforeMap.get("skuName"))) {
                beforeSb.append("（规格值：").append(beforeMap.get("skuName")).append(")");
            }
            this.beforeContent = beforeSb.toString();

            // 变更后 {"photographPrice":9980000}
            StringBuffer afterSb = new StringBuffer();
            String afterPrice = Utils.moneyFormatCN(Long.parseLong(String.valueOf(afterMap.get("photographPrice"))));
            afterSb.append(afterPrice).append("元");
            if (null != beforeMap.get("skuName") && !"".equals(beforeMap.get("skuName"))) {
                afterSb.append("（规格值：").append(beforeMap.get("skuName")).append(")");
            }
            this.afterContent = afterSb.toString();
        } else if (history.getChangeType() == 3) {
            this.changeContent = "修改供货价";
            // 变更前 {"skuName":"5寸,白色","supplyPrice":9950000,"skuId":"20171205160651663483"}
            StringBuffer beforeSb = new StringBuffer();
            String beforePrice = Utils.moneyFormatCN(Long.parseLong(String.valueOf(beforeMap.get("supplyPrice"))));
            beforeSb.append(beforePrice).append("元");
            if (null != beforeMap.get("skuName") && !"".equals(beforeMap.get("skuName"))) {
                beforeSb.append("（规格值：").append(beforeMap.get("skuName")).append(")");
            }
            this.beforeContent = beforeSb.toString();

            // 变更后 {"supplyPrice":9960000}
            StringBuffer afterSb = new StringBuffer();
            String afterPrice = Utils.moneyFormatCN(Long.parseLong(String.valueOf(afterMap.get("supplyPrice"))));
            afterSb.append(afterPrice).append("元");
            if (null != beforeMap.get("skuName") && !"".equals(beforeMap.get("skuName"))) {
                afterSb.append("（规格值：").append(beforeMap.get("skuName")).append(")");
            }
            this.afterContent = afterSb.toString();
        } else {
            this.changeContent = "增加sku";
            // 变更后 {"skuName":"3寸,白色","marketPrice":null,"supplyPrice":9960000.0,"weight":0.2,"photographPrice":9990000.0,"showStatus":2.0,"availableNum":222.0,"goodsCode":"87","skuId":"20180119173349110091"}
            String supplyPrice = null != afterMap.get("supplyPrice") ? Utils.moneyFormatCN(Long.parseLong(String.valueOf(afterMap.get("supplyPrice")))) : null;
            String marketPrice = null != afterMap.get("marketPrice") ? Utils.moneyFormatCN(Long.parseLong(String.valueOf(afterMap.get("marketPrice")))) : null;
            String photographPrice = null != afterMap.get("photographPrice") ? Utils.moneyFormatCN(Long.parseLong(String.valueOf(afterMap.get("photographPrice")))) : null;
            afterMap.put("photographPrice", photographPrice);
            afterMap.put("supplyPrice", supplyPrice);
            afterMap.put("marketPrice", marketPrice);
            this.afterContent = JsonUtils.toStr(afterMap);
        }
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getBeforeContent() {
        return beforeContent;
    }

    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent;
    }

    public String getAfterContent() {
        return afterContent;
    }

    public void setAfterContent(String afterContent) {
        this.afterContent = afterContent;
    }
}
