package cn.m2c.scm.application.postage.command;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 运费规则
 */
public class PostageModelRuleCommand extends AssertionConcern implements Serializable {
    /**
     * 配送地址
     */
    private String address;

    /**
     * 配送地址结构（省市）
     */
    private String addressStructure;

    /**
     * 配送地址城市编码
     */
    private String cityCode;

    /**
     * 首重
     */
    private Float firstWeight;

    /**
     * 首件
     */
    private Integer firstPiece;

    /**
     * 首重/件的运费
     */
    private Long firstPostage;

    /**
     * 续重
     */
    private Float continuedWeight;

    /**
     * 续件
     */
    private Integer continuedPiece;

    /**
     * 续重/件的运费
     */
    private Long continuedPostage;

    /**
     * 全国（默认运费），0：是，1：不是
     */
    private Integer defaultFlag;

    public PostageModelRuleCommand(String address, String addressStructure, String cityCode, Float firstWeight,
                                   Integer firstPiece, Long firstPostage, Float continuedWeight, Integer continuedPiece, Long continuedPostage, Integer defaultFlag) {
        this.address = address;
        this.addressStructure = addressStructure;
        this.cityCode = cityCode;
        this.firstWeight = firstWeight;
        this.firstPiece = firstPiece;
        this.firstPostage = firstPostage;
        this.continuedWeight = continuedWeight;
        this.continuedPiece = continuedPiece;
        this.continuedPostage = continuedPostage;
        this.defaultFlag = defaultFlag;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressStructure() {
        return addressStructure;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Float getFirstWeight() {
        return firstWeight;
    }

    public Integer getFirstPiece() {
        return firstPiece;
    }

    public Long getFirstPostage() {
        return firstPostage;
    }

    public Float getContinuedWeight() {
        return continuedWeight;
    }

    public Integer getContinuedPiece() {
        return continuedPiece;
    }

    public Long getContinuedPostage() {
        return continuedPostage;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public static void main(String [] args){
        PostageModelRuleCommand command = new PostageModelRuleCommand("全国（默认运费）", "", "123,456", 2.00f,
                1, 10000l, 1.00f, 1, 5000l, 1);
        PostageModelRuleCommand command1 = new PostageModelRuleCommand("全国（默认运费）", "", "123,456", 2.00f,
                1, 10000l, 1.00f, 1, 5000l, 1);
        List<PostageModelRuleCommand> postageModelRuleCommands = new ArrayList<>();
        postageModelRuleCommands.add(command);
        postageModelRuleCommands.add(command1);
        System.out.print(JsonUtils.toStr(postageModelRuleCommands));
    }
}
