package cn.m2c.scm.application.seller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.seller.command.SellerCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.seller.Seller;
import cn.m2c.scm.domain.model.seller.SellerRepository;
import cn.m2c.scm.domain.service.seller.SellerService;

@Service
@Transactional
public class SellerApplication {
	private static final Logger log = LoggerFactory.getLogger(SellerApplication.class);

	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	SellerService sellerService;
	

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void addSeller(SellerCommand command) throws NegativeException {
		log.info("---添加经销商业务员", command.toString());
		if(!sellerService.isSellerPhoneExist(command.getSellerPhone())){
			throw new NegativeException(NegativeCode.SELLER_PHONE_IS_EXIST, "此业务员手机号已存在.");
		}
			Seller seller = sellerRepository.getSeller(command.getSellerId());
//			if (seller != null)
//				throw new NegativeException(NegativeCode.SELLER_IS_EXIST, "此业务员已存在.");
			if(seller==null){
				seller = new Seller();
				seller.add(command.getSellerId(), command.getSellerName(), command.getSellerPhone(), command.getSellerSex(),
						command.getSellerNo(), command.getSellerConfirmPass(), command.getSellerProvince(),
						command.getSellerCity(), command.getSellerArea(), command.getSellerPcode(), command.getSellerCcode(),
						command.getSellerAcode(), command.getSellerqq(), command.getSellerWechat(), command.getSellerRemark());
				sellerRepository.save(seller);
			}
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void update(SellerCommand command) throws NegativeException {
		// TODO Auto-generated method stub
		log.info("---修改经销商业务员", command.toString());
		if(!sellerService.isSellerPhoneExist(command.getSellerPhone())){
			throw new NegativeException(NegativeCode.SELLER_PHONE_IS_EXIST, "此业务员手机号已存在.");
		}
			Seller seller = sellerRepository.getSeller(command.getSellerId());
			if (seller == null)
				throw new NegativeException(NegativeCode.SELLER_IS_NOT_EXIST, "此业务员不存在.");
			seller.update(command.getSellerName(), command.getSellerPhone(), command.getSellerSex(), command.getSellerNo(),
					command.getSellerConfirmPass(), command.getSellerProvince(), command.getSellerCity(),
					command.getSellerArea(), command.getSellerPcode(), command.getSellerCcode(), command.getSellerAcode(),
					command.getSellerqq(), command.getSellerWechat(), command.getSellerRemark());
			sellerRepository.save(seller);
	}

	
//	/**
//	 * 业务员添加或者更新事件
//	 * @param command
//	 * @throws NegativeException
//	 */
//	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
//	@EventListener(isListening = true)
//	public void addOrUpdateSeller(SellerCommand command) throws NegativeException {
//		log.info("SellerUpdate command >>{}", command);
//		Seller seller = sellerRepository.getSeller(command.getSellerId());
//		if (null == seller) {
//			throw new NegativeException(MCode.V_300, "业务员不存在");
//		}
//		seller.addOrUpdateEvent();
//	}
}
