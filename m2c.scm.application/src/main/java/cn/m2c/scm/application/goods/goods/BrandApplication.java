package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.goods.goods.command.BrandAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.dealer.Dealer;
import cn.m2c.scm.domain.model.goods.dealer.DealerRepository;
import cn.m2c.scm.domain.model.goods.templet.Brand;
import cn.m2c.scm.domain.model.goods.templet.BrandRepository;


@Service
@Transactional
public class BrandApplication {
	
	private Logger logger = LoggerFactory.getLogger(BrandApplication.class);
	
	
	@Autowired
	DealerRepository dealerRepository;
	@Autowired
	BrandRepository brandRepository;
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addBrand(BrandAddOrUpdateCommand command) throws NegativeException {
		logger.info("brand add command >>{}", command);		
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Brand brand = new Brand();
		brand.add(command.getModelName(),command.getBrandId(),command.getBrandName(),command.getBrandPic(),command.getBrandDesc(),command.getDealerId());
		brandRepository.save(brand);
	}

	/**
	 * 更新品牌
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updateBrand(BrandAddOrUpdateCommand command) throws NegativeException {
		logger.info("brand update command >>{}", command);
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Brand brand = brandRepository.getBrandDetail(command.getBrandId());
		if(brand==null)
			throw new NegativeException(NegativeCode.BRAND_IS_NOT_EXIST, "此品牌不存在.");
		brand.update(command.getModelName(),command.getBrandName(),command.getBrandPic(),command.getBrandDesc());
		brandRepository.save(brand);
	}

	/**
	 * 删除
	 * @param brandId
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void delBrand(String brandId) throws NegativeException {
		Brand brand = brandRepository.getBrandDetail(brandId);
		if(brand==null)
			throw new NegativeException(NegativeCode.BRAND_IS_NOT_EXIST, "此品牌不存在.");
		brand.delBrand();
		brandRepository.save(brand);
	}
	
}
