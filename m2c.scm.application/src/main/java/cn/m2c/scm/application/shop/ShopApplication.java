package cn.m2c.scm.application.shop;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.logger.OperationLogManager;
import cn.m2c.scm.application.shop.command.ShopBackImgCommand;
import cn.m2c.scm.application.shop.command.ShopInfoUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.shop.Shop;
import cn.m2c.scm.domain.model.shop.ShopRepository;

@Service
public class ShopApplication {
	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);
	
	@Autowired
	ShopRepository shopRepository;
	
	@Resource
    private OperationLogManager operationLogManager;
	/**
	 * 修改店铺信息
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updateShopInfo(ShopInfoUpdateCommand command,String _attach) throws NegativeException {
		// TODO Auto-generated method stub
		log.info("---修改经销商店铺信息");
		Shop shop = shopRepository.getShopByShopID(command.getShopId());
		if(shop==null)
			throw new NegativeException(NegativeCode.DEALER_SHOP_IS_NOT_EXIST, "此经销商店铺不存在.");
		if (StringUtils.isNotEmpty(_attach))
			operationLogManager.operationLog("修改店铺信息", _attach, shop);
		shop.updateShopInfo(command.getShopName(),command.getShopIntroduce(),command.getShopIcon(),command.getShopReceipt(),command.getCustomerServiceTel());
		shopRepository.save(shop);
	}

	/**
	 * 新增店铺
	 * @param command
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addShopInfo(ShopInfoUpdateCommand command) throws NegativeException {
			// TODO Auto-generated method stub
			log.info("---新增经销商店铺信息");
			Shop shop = shopRepository.getShopByShopID(command.getShopId());
			if(shop!=null)
				throw new NegativeException(NegativeCode.DEALER_SHOP_IS_EXIST, "此经销商店铺已存在.");
			shop = new Shop(command.getShopId(),command.getDealerId(),command.getShopName(),command.getShopIcon(),command.getShopIntroduce(),command.getShopReceipt(),command.getCustomerServiceTel());
			shopRepository.save(shop);
		}
	
	
	/**
	 * 新增店铺门头图
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addShopBackImg(ShopBackImgCommand command) throws NegativeException {
		log.info("---新增店铺门头图");
		Shop shop = shopRepository.getShopByShopID(command.getShopId());
		if(shop==null)
			throw new NegativeException(NegativeCode.DEALER_SHOP_IS_NOT_EXIST, "此经销商店铺不存在.");
		shop.addShopBackImg(command.getShopBackImg());
		shopRepository.save(shop);
	}
		

}
