package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.classify.GoodsClassify;
import cn.m2c.scm.domain.model.goods.classify.GoodsClassifyRepository;

@Service
@Transactional
public class GoodsClassifyApplication {
	private  Logger logger = LoggerFactory.getLogger(GoodsClassifyApplication.class);

	@Autowired
	GoodsClassifyRepository repository;
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeCountEvent(String goodsClassifyId, Integer changeCount) throws NegativeException {
		GoodsClassify classifyDetail = repository.getGoodsClassifyDetail(goodsClassifyId);
		if(classifyDetail==null)
			throw new NegativeException(NegativeCode.GOOD_CLASSIFY_IS_NOT_EXIST,"商品分类不存在");
		classifyDetail.changeCount(changeCount);
		repository.save(classifyDetail);
	}
	
}
