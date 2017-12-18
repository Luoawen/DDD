package cn.m2c.scm.application.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeAddCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsGuarantee;
import cn.m2c.scm.domain.model.goods.GoodsGuaranteeRepository;

/**
 * 商品保障
 * */
@Service
@Transactional
public class GoodsGuaranteeApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGuaranteeApplication.class);
	
	@Autowired
	GoodsGuaranteeRepository goodsGuaranteeRepository;
	
	/**
	* 新增商品保障，商品保障名不可重复，根据商品保障名和商家id查是否存在（1~10字符）
	 * @throws NegativeException 
	*/
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void addGoodsGuarantee(GoodsGuaranteeAddCommand command) throws NegativeException {
		LOGGER.info("addGoodsGuarantee command >>{}", command);
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeById(command.getGuaranteeId());
		if(null != goodsGuarantee){
			throw new NegativeException(MCode.V_300,"商品保障已存在");
		}
		//查重名
		boolean goodsGuaranteeByName = goodsGuaranteeRepository.goodsGuaranteeNameIsRepeat(command.getGuaranteeName(), command.getDealerId());
		if(goodsGuaranteeByName) {
			throw new NegativeException(MCode.V_300,"商品保障名称已存在");
		}
		goodsGuarantee = new GoodsGuarantee(command.getGuaranteeId(), command.getGuaranteeName(), command.getGuaranteeDesc(), command.getDealerId());
		goodsGuaranteeRepository.save(goodsGuarantee);
	}
	
	
	
	
	
	
}
