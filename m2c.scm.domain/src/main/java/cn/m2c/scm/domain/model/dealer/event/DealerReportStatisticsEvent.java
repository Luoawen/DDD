package cn.m2c.scm.domain.model.dealer.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 商家报表统计事件
 */
public class DealerReportStatisticsEvent implements DomainEvent {

    private Date occurredOn;
    private int eventVersion;

    /**
     * 商家id
     */
    private String dealerId;

    /**
     * 操作类型,见DealerReportType类定义
     */
    private String type;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 金额
     */
    private Long money;

    /**
     * 操作时间
     */
    private Date time;

    public DealerReportStatisticsEvent(String dealerId, String type, Integer num, Long money, Date time) {
        this.dealerId = dealerId;
        this.type = type;
        this.num = num;
        this.money = money;
        this.time = time;
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
