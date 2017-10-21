package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.order.command.AddSaleAfterCmd;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.SaleAfterOrder;
import cn.m2c.scm.domain.model.order.SaleAfterOrderRepository;

/***
 * 售后应用层服务
 * @author 89776
 * created date 2017年10月21日
 * copyrighted@m2c
 */
@Service
public class SaleAfterOrderApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(SaleAfterOrderApp.class);
	@Autowired
	SaleAfterOrderRepository saleAfterRepository;
	
	/***
	 * 创建售后单
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void createSaleAfterOrder(AddSaleAfterCmd cmd) throws NegativeException {
		// 获取订单SKU详情看是否满足售后申请
		DealerOrderDtl itemDtl = saleAfterRepository.getDealerOrderDtlBySku(cmd.getDealerOrderId(), 
				cmd.getSkuId());
		
		if (!itemDtl.canApplySaleAfter()) {
			throw new NegativeException(MCode.V_100, "商品处于不可申请售后状态！");
		}
		// 生成售后单保存
		long money = 1000;
		SaleAfterOrder afterOrder = new SaleAfterOrder(cmd.getSaleAfterNo(), cmd.getUserId(), cmd.getOrderId(),
				cmd.getDealerOrderId(), cmd.getDealerId(), cmd.getGoodsId(), cmd.getSkuId(), cmd.getReason()
				, cmd.getBackNum(), 0, cmd.getType(), money, cmd.getReasonCode());
		
		saleAfterRepository.save(afterOrder);
		LOGGER.info("新增加售后申请成功！");
	}
	/***
	 * 申请售后退货或退款提示
	 * @param cmd
	 */
	public void applySaleAfter(AddSaleAfterCmd cmd) {
		// 获取订单SKU详情看是否满足售后申请
		// 生成售后单保存
	}
}
