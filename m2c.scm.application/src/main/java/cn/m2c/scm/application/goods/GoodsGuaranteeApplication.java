package cn.m2c.scm.application.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeAddCommand;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeDelCommand;
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
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeByIdAndDealerId(command.getGuaranteeId(), command.getDealerId());
		if(null != goodsGuarantee){
			throw new NegativeException(MCode.V_300,"商品保障已存在");
		}
		//查重名
		boolean goodsGuaranteeByName = goodsGuaranteeRepository.goodsGuaranteeNameIsRepeat(command.getGuaranteeName(), command.getDealerId(), command.getGuaranteeId());
		if(goodsGuaranteeByName) {
			throw new NegativeException(MCode.V_300,"商品保障名称已存在");
		}
		goodsGuarantee = new GoodsGuarantee(command.getGuaranteeId(), command.getGuaranteeName(), command.getGuaranteeDesc(), command.getDealerId());
		goodsGuaranteeRepository.save(goodsGuarantee);
	}

	/**
	 * 修改商品保障
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void modifyGoodsGuarantee(GoodsGuaranteeAddCommand command) throws NegativeException {
		LOGGER.info("modifyGoodsGuarantee command >>{}", command);
		//goodsGuaranteeId查GoodsGuarantee是否存在，存在可修改
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeByIdAndDealerId(command.getGuaranteeId(), command.getDealerId());
		if(null == goodsGuarantee) {
			throw new NegativeException(MCode.V_300,"商品保障不存在");
		}
		//查重名
		boolean guaranteeNameIsRepeat = goodsGuaranteeRepository.goodsGuaranteeNameIsRepeat(command.getGuaranteeName(), command.getDealerId(), command.getGuaranteeId());
		if(guaranteeNameIsRepeat) {
			throw new NegativeException(MCode.V_300,"商品保障名称已存在");
		}
		goodsGuarantee.modifyGoodsGuarantee(command.getGuaranteeName(), command.getGuaranteeDesc());
		goodsGuaranteeRepository.save(goodsGuarantee);
	}

	/**
	 * 删除商品保障
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void delGoodsGuarantee(GoodsGuaranteeDelCommand command) throws NegativeException {
		LOGGER.info("delGoodsGuarantee command >>{}", command);
		//查是否存在
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeByIdAndDealerId(command.getGuaranteeId(), command.getDealerId());
		if(null == goodsGuarantee) {
			throw new NegativeException(MCode.V_300,"商品保障不存在");
		}
		goodsGuaranteeRepository.remove(goodsGuarantee);
	}
	
	
	
	
	
	
}
