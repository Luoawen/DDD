package cn.m2c.scm.application.seller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.seller.command.SellerAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.seller.Seller;
import cn.m2c.scm.domain.model.seller.SellerRepository;

@Service
@Transactional
public class SellerApplication {
	private static final Logger log = LoggerFactory.getLogger(SellerApplication.class);
	
	@Autowired
	SellerRepository sellerRepository;

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addSeller(SellerAddOrUpdateCommand command) throws NegativeException {
		log.info("---添加经销商业务员",command.toString());
		Seller seller = sellerRepository.getSeller(command.getSellerId());
		if(seller!=null)
			throw new NegativeException(NegativeCode.SELLER_IS_EXIST, "此业务员已存在.");
		seller = new Seller();
		seller.add(command.getSellerId(),command.getSellerName(),command.getSellerPhone(),command.getSellerSex(),command.getSellerNo(),command.getSellerConfirmPass(),command.getSellerProvince(),command.getSellerCity(),command.getSellerArea(),command.getSellerPcode(),command.getSellerCcode(),command.getSellerAcode(),command.getSellerqq(),command.getSellerWechat(),command.getSellerRemark());
		sellerRepository.save(seller);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void update(SellerAddOrUpdateCommand command) throws NegativeException {
		// TODO Auto-generated method stub
		log.info("---修改经销商业务员",command.toString());
		Seller seller = sellerRepository.getSeller(command.getSellerId());
		if(seller==null)
			throw new NegativeException(NegativeCode.SELLER_IS_NOT_EXIST, "此业务员不存在.");
		seller = new Seller();
		seller.update(command.getSellerName(),command.getSellerPhone(),command.getSellerSex(),command.getSellerNo(),command.getSellerConfirmPass(),command.getSellerProvince(),command.getSellerCity(),command.getSellerArea(),command.getSellerPcode(),command.getSellerCcode(),command.getSellerAcode(),command.getSellerqq(),command.getSellerWechat(),command.getSellerRemark());
		sellerRepository.save(seller);
	}
}
