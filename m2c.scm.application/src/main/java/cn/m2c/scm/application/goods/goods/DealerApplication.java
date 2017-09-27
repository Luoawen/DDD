package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.operate.interfaces.system.SalerService;
import cn.m2c.operate.interfaces.system.representation.Saler;
import cn.m2c.trading.interfaces.dubbo.AccountService;
import cn.m2c.scm.application.goods.goods.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.dealer.Dealer;
import cn.m2c.scm.domain.model.goods.dealer.DealerRepository;
import cn.m2c.scm.domain.model.goods.dealer.StaffAEvent;
import cn.m2c.scm.domain.model.goods.dealer.UorADealerEvent;
import cn.m2c.users.interfaces.dubbo.UserService;

@Service
@Transactional
public class DealerApplication {
	private Logger logger = LoggerFactory.getLogger(DealerApplication.class);
	
	@Autowired
	DealerRepository dealerRepository;
	@Autowired
	SalerService salerService;
	@Autowired
	AccountService accountService;
	@Autowired
	UserService userService;
	/**
	 * 添加经销商
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void add(DealerAddOrUpdateCommand command) throws NegativeException {
		logger.info("dealer add command >>{}", command.getMobile());
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer!=null)
			throw new NegativeException(NegativeCode.DEALER_IS_EXIST, "此经销商已存在.");
		if(command.getMobile()!=null && !"".equals(command.getMobile())){
			Dealer mobileDealer = dealerRepository.getDealerByMobile(command.getMobile());
			if(mobileDealer!=null)
				throw new NegativeException(NegativeCode.DEALER_IS_EXIST, "此经销商已存在.");
		}
		Saler seller = null;
		if(command.getSellerId()!=null && !"".equals(command.getSellerId())){
			seller = salerService.getSalerById(command.getSellerId());
			if(seller==null){
				throw new NegativeException(NegativeCode.SELLER_IS_NOT_EXIST, "此经销商业务员不存在.");
			}
		}
		dealer = new Dealer();
		
//		DomainEvent upOrAddDealerEvent = new UorADealerEvent(command.getDealerId(),command.getDealerName(),command.getDealerMobile(),new Date().getTime());
//		DomainEventPublisher.instance().publish(upOrAddDealerEvent);
		boolean iscreatePayAccount = accountService.createPayAccount(command.getDealerId(), 2, command.getDealerName(), command.getDealerMobile(), null);
		if(!iscreatePayAccount){
			throw new NegativeException(NegativeCode.CREATE_PAY_ACCOUNT_FAIL, "添加经销商支付信息失败");
		}
		
		dealer.add(command.getDealerId(),command.getMobile(),command.getDealerName(),
				command.getFirstClassification(),command.getSecondClassification(),command.getCooperationMode(),
				command.getDealerProvince(),command.getDealerCity(),command.getDealerArea(),command.getProvinceCode(),command.getCityCode(),command.getAreaCode(),
				command.getDetailAddress(),command.getDealerMobile(),command.getSellerId(),command.getUserId(),command.getUsername(),seller==null?"":seller.getTelNo(),seller==null?"":seller.getName());
		//----------------------------业绩统计事件
		if(command.getSellerId()!=null && !"".equals(command.getSellerId())){
			logger.info("经销商开始统计业绩-----");
			logger.info("新经销商业务员id"+command.getSellerId());
			DomainEvent staffAEvent = new StaffAEvent(command.getSellerId(),seller.getName(), command.getDealerId(),System.currentTimeMillis());
			DomainEventPublisher.instance().publish(staffAEvent);
		}
		
		dealerRepository.save(dealer);
	}
	
	/**
	 * 更新经销商
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void update(DealerAddOrUpdateCommand command) throws NegativeException {
		logger.info("dealer update command >>{}", command);
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Saler seller = null;
		if(command.getSellerId()!=null && !"".equals(command.getSellerId())){
			 seller = salerService.getSalerById(command.getSellerId());
			if(seller==null)
				throw new NegativeException(NegativeCode.SELLER_IS_NOT_EXIST, "此经销商业务员不存在.");
		}
		DomainEvent upOrAddDealerEvent = new UorADealerEvent(command.getDealerId(),command.getDealerName(),command.getDealerMobile(),dealer.getCreatedDate().getTime());
		DomainEventPublisher.instance().publish(upOrAddDealerEvent);
		//----------------------------统计业绩事件
		if(command.getSellerId()!=null && !"".equals(command.getSellerId())){
			if(!command.getSellerId().equals(dealer.getSellerId())){
				DomainEvent staffAEvent = new StaffAEvent(command.getSellerId(),seller.getName(), command.getDealerId(),System.currentTimeMillis());
				DomainEventPublisher.instance().publish(staffAEvent);
			}
		}
		//解绑经销商用户降级为普通用户
		if(hasText(dealer.getUsername()) && !dealer.getUsername().equals(command.getUsername())){
			userService.deleteUser(dealer.getUserId());
		}
		
		dealer.update(command.getDealerId(),command.getMobile(),command.getDealerName(),
				command.getFirstClassification(),command.getSecondClassification(),command.getCooperationMode(),
				command.getDealerProvince(),command.getDealerCity(),command.getDealerArea(),command.getProvinceCode(),command.getCityCode(),command.getAreaCode(),
				command.getDetailAddress(),command.getDealerMobile(),command.getSellerId(),command.getUserId(),command.getUsername(),seller==null?"":seller.getTelNo(),seller==null?"":seller.getName());
		
		dealerRepository.save(dealer);
	}

	/**
	 * 删除经销商操作
	 * @param dealerId
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void delDealer(String dealerId) throws NegativeException {
		logger.info("dealer delete command >>{}", dealerId);
		Dealer dealer = dealerRepository.getDealerDetail(dealerId);
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		dealer.del();
		try {
			userService.deleteUser(dealer.getUserId());
		} catch (Exception e) {
			logger.error("-----------------降级经销商用户为普通用户失败",e);
			throw new NegativeException(NegativeCode.DEALER_DELETE_FAILED, "此经销商不存在.");
		}
		dealerRepository.save(dealer);
	}
	/**
	 * 经销商和用户解绑
	 * @param userId
	 * @param mobile
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeUnBind(String userId,String mobile) throws NegativeException {
		// TODO Auto-generated method stub
		logger.info("解绑 >>{}", mobile);
		Dealer dealer = dealerRepository.getDealerByMobile(mobile);
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		dealer.unbind();
		dealerRepository.save(dealer);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeUserName(String userId, String userName) throws NegativeException {
		logger.info("消费更新用户名>>{}", userId,userName);
		Dealer dealer = dealerRepository.getDealerByUserId(userId);
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		dealer.updateUserInfo(userName);
		dealerRepository.save(dealer);
	}
	
	private static boolean  hasText(String text){
		if(text!=null && !"".equals(text)){
			return true;
		}else{
			return false;
		}
	}

}
