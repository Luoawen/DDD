package cn.m2c.scm.application.dealerreport.data.bean.representation;

/**
 * 最近7天销售统计
 */
public class DealerNearlyReportRepresentation {
    private String time;
    private Integer ratioFlag;
    private Integer ratio;
    private Long sellMoney;

    public DealerNearlyReportRepresentation(String time, Integer ratioFlag, Integer ratio, Long sellMoney) {
        this.time = time;
        this.ratioFlag = ratioFlag;
        this.ratio = ratio;
        this.sellMoney = sellMoney / 10000;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Long getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(Long sellMoney) {
        this.sellMoney = sellMoney;
    }

    public Integer getRatioFlag() {
        return ratioFlag;
    }

    public void setRatioFlag(Integer ratioFlag) {
        this.ratioFlag = ratioFlag;
    }
}
