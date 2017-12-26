package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.m2c.scm.application.utils.expressUtil.Result;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatform;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatformRepository;

@Service
@Transactional
public class ExpressPlatformApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressPlatformApplication.class);

	@Autowired
	ExpressPlatformRepository expressPlatformRepository;
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void addOrUpdate(Result res) throws NegativeException{
		if(StringUtils.isEmpty(res.getCom()))
			throw new NegativeException(400, "快递公司编码不能为空");
		if(StringUtils.isEmpty(res.getNu()))
			throw new NegativeException(400, "快递公司单号不能为空");
		ExpressPlatform ep = expressPlatformRepository.getExpressPlatform(res.getCom(),res.getNu());
		System.out.println(res.getCom());
		System.out.println(res.getNu());
		System.out.println(res.toString());
		if(ep==null)
			ep = new ExpressPlatform();
		ep.add(res.getCom(),res.getNu(),res.toString());
		expressPlatformRepository.saveOrUpdate(ep);
	}

	
	
}
