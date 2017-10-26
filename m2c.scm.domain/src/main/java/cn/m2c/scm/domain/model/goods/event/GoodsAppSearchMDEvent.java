package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * app搜索事件
 */
public class GoodsAppSearchMDEvent implements DomainEvent {
    private String sn;
    private String userId;
    private String searchFrom;//HOT_KEYWORD,INPUT
    private String keyWord;
    private Date time;
    private Date occurredOn;
    private int eventVersion;

    public GoodsAppSearchMDEvent(String sn, String userId, String searchFrom, String keyWord) {
        this.sn = sn;
        this.userId = userId;
        this.searchFrom = searchFrom;
        this.keyWord = keyWord;
        this.time = new Date();
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
