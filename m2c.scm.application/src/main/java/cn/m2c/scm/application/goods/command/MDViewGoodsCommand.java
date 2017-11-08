package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

public class MDViewGoodsCommand extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = -4779357210480022017L;


    private String sn;
    private String os;
    private String appVersion;
    private String osVersion;
    private Long triggerTime;
    private Long lastTime;
    private String userId;
    private String userName;
    private String goodsId;
    private String goodsName;
    private String mresId;

    public MDViewGoodsCommand(String sn, String os, String appVersion, String osVersion,
                              Long triggerTime, Long lastTime, String userId, String userName,
                              String goodsId, String goodsName, String mresId) {
        this.sn = sn;
        this.os = os;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.triggerTime = triggerTime;
        this.lastTime = lastTime;
        this.userId = userId;
        this.userName = userName;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.mresId = mresId;
    }

    public String getSn() {
        return sn;
    }

    public String getOs() {
        return os;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getMresId() {
        return mresId;
    }

    @Override
    public String toString() {
        return "MDViewGoodsCommand{" +
                "sn='" + sn + '\'' +
                ", os='" + os + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", triggerTime=" + triggerTime +
                ", lastTime=" + lastTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", mresId='" + mresId + '\'' +
                '}';
    }
}
