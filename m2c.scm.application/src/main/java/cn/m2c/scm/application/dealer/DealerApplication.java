package cn.m2c.scm.application.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.dealer.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.application.dealer.command.ShopInfoUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.dealer.Dealer;
import cn.m2c.scm.domain.model.dealer.DealerRepository;


@Service
@Transactional
public class DealerApplication {
	private static final Logger log = LoggerFactory.getLogger(DealerApplication.class);

	@Autowired
	DealerRepository dealerRepository;
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addDealer(DealerAddOrUpdateCommand command) throws NegativeException {
		log.info("---添加经销商参数",command.toString());
		Dealer dealer = dealerRepository.getDealer(command.getDealerId());
		if(dealer!=null)
			throw new NegativeException(NegativeCode.DEALER_IS_EXIST, "此经销商已存在.");
		dealer = new Dealer();
		dealer.add(command.getDealerId(),command.getUserId(),command.getUserName(),command.getUserPhone(),command.getDealerName(),command.getDealerClassify(),command.getCooperationMode(),command.getStartSignDate(),command.getEndSignDate(),command.getDealerProvince(),command.getDealerCity(),command.getDealerArea(),command.getDealerPcode(),command.getDealerCcode(),command.getDealerAcode(),command.getDealerDetailAddress(),command.getCountMode(),command.getDeposit(),command.getIsPayDeposit(),command.getManagerName(),command.getManagerPhone(),command.getManagerqq(),command.getManagerWechat(),command.getManagerEmail(),command.getManagerDepartment(),command.getSellerId(),command.getSellerName(),command.getSellerPhone());
		dealerRepository.save(dealer);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updateDealer(DealerAddOrUpdateCommand command) throws NegativeException {
		log.info("---修改经销商参数",command.toString());
		Dealer dealer = dealerRepository.getDealer(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		dealer.update(command.getUserId(),command.getUserName(),command.getUserPhone(),command.getDealerName(),command.getDealerClassify(),command.getCooperationMode(),command.getStartSignDate(),command.getEndSignDate(),command.getDealerProvince(),command.getDealerCity(),command.getDealerArea(),command.getDealerPcode(),command.getDealerCcode(),command.getDealerAcode(),command.getDealerDetailAddress(),command.getCountMode(),command.getDeposit(),command.getIsPayDeposit(),command.getManagerName(),command.getManagerPhone(),command.getManagerqq(),command.getManagerWechat(),command.getManagerEmail(),command.getManagerDepartment(),command.getSellerId(),command.getSellerName(),command.getSellerPhone());
		dealerRepository.save(dealer);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updateShopInfo(ShopInfoUpdateCommand command) throws NegativeException {
		// TODO Auto-generated method stub
		log.info("---修改经销商店铺信息");
		Dealer dealer = dealerRepository.getDealer(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		dealer.updateShopInfo(command.getShopName(),command.getShopIntroduce(),command.getShopIcon(),command.getCustomerServiceTel());
		dealerRepository.save(dealer);
	}
	
	
	
}
