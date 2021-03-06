package cn.m2c.scm.application.goods;


import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.ddd.common.logger.OperationLogManager;
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
	
	@Resource
    private OperationLogManager operationLogManager;
	
	/**
	 * 新增商品保障，商品保障名不可重复，根据商品保障名和商家id查是否存在（1~10字符）
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void addGoodsGuarantee(GoodsGuaranteeAddCommand command) throws NegativeException {
		LOGGER.info("addGoodsGuarantee command >>{}", command);
		//查是否超过10个
		List<GoodsGuarantee> list = goodsGuaranteeRepository.queryGoodsGuaranteeByDealerId(command.getDealerId());
		if(null != list && list.size() >= 10) {
			throw new NegativeException(MCode.V_300,"商品保障已满10个");
		}
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeByIdAndDealerId(command.getGuaranteeId(), command.getDealerId());
		if(null != goodsGuarantee){
			throw new NegativeException(MCode.V_300,"商品保障已存在");
		}
		//查重名
		boolean goodsGuaranteeByName = goodsGuaranteeRepository.goodsGuaranteeNameIsRepeat(command.getGuaranteeName(), command.getDealerId(), command.getGuaranteeId());
		if(goodsGuaranteeByName) {
			throw new NegativeException(MCode.V_300,"标题已存在");
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
	public void modifyGoodsGuarantee(GoodsGuaranteeAddCommand command, String _attach) throws NegativeException {
		LOGGER.info("modifyGoodsGuarantee command >>{}", command);
		//goodsGuaranteeId查GoodsGuarantee是否存在，存在可修改
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeByIdAndDealerId(command.getGuaranteeId(), command.getDealerId());
		if(null == goodsGuarantee) {
			throw new NegativeException(MCode.V_300,"商品保障不存在");
		}
		//查重名
		boolean guaranteeNameIsRepeat = goodsGuaranteeRepository.goodsGuaranteeNameIsRepeat(command.getGuaranteeName(), command.getDealerId(), command.getGuaranteeId());
		if(guaranteeNameIsRepeat) {
			throw new NegativeException(MCode.V_300,"标题已存在");
		}
		if (StringUtils.isNotEmpty(_attach))
			operationLogManager.operationLog("修改商品保障", _attach, goodsGuarantee);
		goodsGuarantee.modifyGoodsGuarantee(command.getGuaranteeName(), command.getGuaranteeDesc());
		goodsGuaranteeRepository.save(goodsGuarantee);
	}

	/**
	 * 删除商品保障
	 * @param command
	 * @throws NegativeException 
	 */
	@EventListener(isListening = true)
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void delGoodsGuarantee(String guaranteeId, String _attach) throws NegativeException {
		//查是否存在
		GoodsGuarantee goodsGuarantee = goodsGuaranteeRepository.queryGoodsGuaranteeById(guaranteeId);
		if(null == goodsGuarantee) {
			throw new NegativeException(MCode.V_300,"商品保障不存在");
		}
		if (StringUtils.isNotEmpty(_attach))
			operationLogManager.operationLog("删除商品保障", _attach, goodsGuarantee);
		goodsGuarantee.remove();
		goodsGuaranteeRepository.save(goodsGuarantee);
	}
	
	
	
	
	
	
}
