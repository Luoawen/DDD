package cn.m2c.scm.application.dealer.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商家每天报表统计
 */
public class DealerDayReportCommand extends AssertionConcern implements Serializable {
    private String dealerId;
    private String type;
    private Integer num;
    private Long money;
    private Integer day;

    public DealerDayReportCommand(String dealerId, String type, Integer num, Long money, Integer day) {
        this.dealerId = dealerId;
        this.type = type;
        this.num = num;
        this.money = money;
        this.day = day;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getType() {
        return type;
    }

    public Integer getNum() {
        return num;
    }

    public Long getMoney() {
        return money;
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "DealerDayReportCommand{" +
                "dealerId='" + dealerId + '\'' +
                ", type='" + type + '\'' +
                ", num=" + num +
                ", money=" + money +
                ", day=" + day +
                '}';
    }
}
