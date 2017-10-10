package cn.m2c.scm.port.adapter.messaging.rabbitmq.brand;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.brand.BrandApplication;
import cn.m2c.scm.application.brand.command.BrandCommand;
import cn.m2c.scm.domain.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.util.Date;

/**
 * 商家管理平台审核品牌-同意
 */
public class BrandApproveAgreeListener extends ExchangeListener {
    public BrandApproveAgreeListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    BrandApplication brandApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String brandId = reader.eventStringValue("brandId");
        String brandName = reader.eventStringValue("brandName");
        String brandNameEn = reader.eventStringValue("brandNameEn");
        String brandLogo = reader.eventStringValue("brandLogo");
        String firstAreaCode = reader.eventStringValue("firstAreaCode");
        String twoAreaCode = reader.eventStringValue("twoAreaCode");
        String threeAreaCode = reader.eventStringValue("threeAreaCode");
        String firstAreaName = reader.eventStringValue("firstAreaName");
        String twoAreaName = reader.eventStringValue("twoAreaName");
        String threeAreaName = reader.eventStringValue("threeAreaName");
        Date applyDate = reader.eventDateValue("applyDate");
        String dealerId = reader.eventStringValue("dealerId");
        // 操作标识，1：品牌新增或待审批品牌修改，2：已审批品牌修改
        Integer optFlag = reader.eventIntegerValue("optFlag");

        if (optFlag == 1) {
            brandId = IDGenerator.get(IDGenerator.SCM_BRAND_PREFIX_TITLE);
        }
        BrandCommand command = new BrandCommand(brandId, brandName, brandNameEn, brandLogo, firstAreaCode,
                twoAreaCode, threeAreaCode, firstAreaName, twoAreaName,
                threeAreaName, applyDate, dealerId, 0);
        if (optFlag == 1) {
            brandApplication.addBrand(command);
        } else {
            brandApplication.modifyBrand(command);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.brand.event.BrandApproveAgreeEvent"};
    }
}
