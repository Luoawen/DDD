package cn.m2c.scm.domain.model.push.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 消息推送
 */
public class MsgPushEvent implements DomainEvent {
    /**
     * 推送类型 1:物流助手 2:通知消息
     */
    private Integer msgType;
    /**
     * 被推送者ID
     */
    private String userId;
    /**
     * 被推送者昵称
     */
    private String userName;
    /**
     * 被推送者设备状态 0:登出解绑 1：登陆绑定
     */
    private String deviceState;
    /**
     * 设备号
     */
    private String deviceSn;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要文本内容
     */
    private String alert;

    /**
     * 业务内容[需要发送方与接收方共同商定里面具体的内容]
     */
    private String extra;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 发送时间
     */
    private Long sendDate;

    private Date occurredOn;
    private int eventVersion;

    public MsgPushEvent(Integer msgType, String userId, String userName, String deviceState, String deviceSn,
                        String title, String alert, String extra, String senderId, Long sendDate) {
        this.msgType = msgType;
        this.userId = userId;
        this.userName = userName;
        this.deviceState = deviceState;
        this.deviceSn = deviceSn;
        this.title = title;
        this.alert = alert;
        this.extra = extra;
        this.senderId = senderId;
        this.sendDate = sendDate;
        this.occurredOn = new Date();
        this.eventVersion = 0;
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
