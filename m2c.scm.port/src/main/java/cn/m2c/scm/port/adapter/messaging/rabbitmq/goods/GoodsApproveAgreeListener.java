package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.command.GoodsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 商家管理平台商品审核同意
 */
public class GoodsApproveAgreeListener extends ExchangeListener {
    public GoodsApproveAgreeListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    GoodsApplication goodsApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String goodsId = reader.eventStringValue("goodsId");
        String dealerId = reader.eventStringValue("dealerId");
        String dealerName = reader.eventStringValue("dealerName");
        String goodsName = reader.eventStringValue("goodsName");
        String goodsSubTitle = reader.eventStringValue("goodsSubTitle");
        String goodsClassifyId = reader.eventStringValue("goodsClassifyId");
        String goodsBrandId = reader.eventStringValue("goodsBrandId");
        String goodsBrandName = reader.eventStringValue("goodsBrandName");
        String goodsUnitId = reader.eventStringValue("goodsUnitId");
        Integer goodsMinQuantity = reader.eventIntegerValue("goodsMinQuantity");
        String goodsPostageId = reader.eventStringValue("goodsPostageId");
        String goodsBarCode = reader.eventStringValue("goodsBarCode");
        String goodsKeyWord = reader.eventStringValue("goodsKeyWord");
        String goodsGuarantee = reader.eventStringValue("goodsGuarantee");
        String goodsMainImages = reader.eventStringValue("goodsMainImages");
        String goodsDesc = reader.eventStringValue("goodsDesc");
        Integer goodsShelves = reader.eventIntegerValue("goodsShelves");
        String goodsSkuApproves = reader.eventStringValue("goodsSkuApproves");
        String goodsSpecifications = reader.eventStringValue("goodsSpecifications");
        GoodsCommand command = new GoodsCommand(goodsId, dealerId, dealerName, goodsName, goodsSubTitle,
                goodsClassifyId, goodsBrandId, goodsBrandName, goodsUnitId, goodsMinQuantity,
                goodsPostageId, goodsBarCode, goodsKeyWord, goodsGuarantee,
                goodsMainImages, goodsDesc, goodsShelves, goodsSpecifications, goodsSkuApproves);
        goodsApplication.saveGoods(command);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsApproveAgreeEvent"};
    }
}
