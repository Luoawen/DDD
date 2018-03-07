package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 商品拍获事件
 */
public class GoodsAppCapturedMDEvent extends AssertionConcern implements DomainEvent {

    private String sn;
    private String os;
    private String appVersion;
    private String osVersion;
    private Long triggerTime;
    private String userId;
    private String userName;
    private String goodsId;
    private String goodsName;
    private String mediaId;
    private String mediaName;
    private String mresId;
    private String mresName;
    private Date currentTime;
    private Date occurredOn;
    private int eventVersion;
    //private Integer source;  //来源(1：app , 2：小程序)


    public GoodsAppCapturedMDEvent() {
        super();
    }


    public GoodsAppCapturedMDEvent(String sn, String os, String appVersion,
                                   String osVersion, Long triggerTime, String userId, String userName,
                                   String goodsId, String goodsName, String mediaId, String mediaName,
                                   String mresId, String mresName) {
        this.sn = sn;
        this.os = os;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.triggerTime = triggerTime;
        this.userId = userId;
        this.userName = userName;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.mediaId = mediaId;
        this.mediaName = mediaName;
        this.mresId = mresId;
        this.mresName = mresName;
        this.currentTime = new Date();
        this.occurredOn = new Date();
        this.eventVersion = 1;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

}
