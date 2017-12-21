package cn.m2c.scm.domain.model.dealer.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.Map;

/**
 * 商家报表统计事件
 */
public class DealerReportStatisticsEvent implements DomainEvent {

    private Date occurredOn;
    private int eventVersion;

    /**
     * 操作类型,见DealerReportType类定义
     */
    private String type;

    /**
     * 操作时间
     */
    private Date time;

    private Map dealerInfo;

    private Map goodsInfo;

    public DealerReportStatisticsEvent(Map dealerInfo, Map goodsInfo, String type, Date time) {
        this.type = type;
        this.time = time;
        this.dealerInfo = dealerInfo;
        this.goodsInfo = goodsInfo;
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
