package cn.m2c.scm.domain.model.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品审核记录变更历史(商品审核库，修改需审核字段记录)
 */
public class GoodsApproveHistory extends IdentifiedValueObject {
    /**
     * 记录编号
     */
    private String historyId;
    /**
     * 操作批次编号
     */
    private String historyNo;

    /**
     * 商品审核
     */
    private GoodsApprove goodsApprove;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 变更类型，1：修改商品分类，2：修改拍获价，3：修改供货价，4：增加sku
     */
    private Integer changeType;

    /**
     * 变更前内容
     */
    private String beforeContent;

    /**
     * 变更后内容
     */
    private String afterContent;

    /**
     * 变更原因
     */
    private String changeReason;

    /**
     * 创建时间
     */
    private Date createTime;

    public GoodsApproveHistory() {
        super();
    }

    public GoodsApproveHistory(String historyId, String historyNo, GoodsApprove goodsApprove, String goodsId,
                               Integer changeType, String beforeContent,
                               String afterContent, String changeReason, Date createTime) {
        this.historyId = historyId;
        this.historyNo = historyNo;
        this.goodsApprove = goodsApprove;
        this.goodsId = goodsId;
        this.changeType = changeType;
        this.beforeContent = beforeContent;
        this.afterContent = afterContent;
        this.changeReason = changeReason;
        this.createTime = createTime;
    }

    public Map convertToMap() {
        Map map = new HashMap<>();
        map.put("historyId", this.historyId);
        map.put("historyNo", this.historyNo);
        map.put("goodsId", this.goodsId);
        map.put("changeType", this.changeType);
        map.put("beforeContent", this.beforeContent);
        map.put("afterContent", this.afterContent);
        map.put("changeReason", this.changeReason);
        map.put("modifyTime", this.createTime);
        return map;
    }

    public Integer changeType() {
        return changeType;
    }

    public void modifyApproveHistory(String afterContent, Date createTime) {
        this.afterContent = afterContent;
        this.createTime = createTime;
    }

    public boolean isModifyPhotographPrice(String skuId) {
        if (this.changeType == 2) {
            Map map = JsonUtils.toMap(this.beforeContent);
            String sku = (String) map.get("skuId");
            return sku.equals(skuId);
        }
        return false;
    }


    public boolean isModifySupplyPrice(String skuId) {
        if (this.changeType == 3) {
            Map map = JsonUtils.toMap(this.beforeContent);
            String sku = (String) map.get("skuId");
            return sku.equals(skuId);
        }
        return false;
    }

    public String afterContent() {
        return afterContent;
    }
}
