package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.goods.goods.command.LocationAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.location.LocRepository;
import cn.m2c.scm.domain.model.goods.location.Location;



@Service
@Transactional
public class LocApplication {
	private  Logger logger = LoggerFactory.getLogger(LocApplication.class);
	
	@Autowired
	LocRepository locRepository;

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void add(LocationAddOrUpdateCommand command) throws NegativeException {
		logger.info("------轮播图或者推荐商品参数"+command.toString());
		Location location = locRepository.getLocDetail(command.getLocationId());
		if(location!=null)
			throw new NegativeException(NegativeCode.LOCATION_IS_EXIST, "此数据已存在");
		location = new Location();
		location.add(command.getLocationId(),command.getLocSelect(),command.getTitle(),command.getIsOnline(),command.getEffectiveTime(),command.getDisplayOrder(),command.getImgUrl(),command.getLocType(),command.getRedirectUrl(),command.getGoodsId(),command.getGoodsName(),command.getGoodsPrice());
		locRepository.save(location);
	}
	
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void update(LocationAddOrUpdateCommand command) throws NegativeException {
		logger.info("------轮播图或者推荐商品参数"+command.toString());
		Location location = locRepository.getLocDetail(command.getLocationId());
		if(location==null)
			throw new NegativeException(NegativeCode.LOCATION_IS_NOT_EXIST, "此数据不存在");
		location.update(command.getLocationId(),command.getLocSelect(),command.getTitle(),command.getIsOnline(),command.getEffectiveTime(),command.getDisplayOrder(),command.getImgUrl(),command.getLocType(),command.getRedirectUrl(),command.getGoodsId(),command.getGoodsName(),command.getGoodsPrice());
		locRepository.save(location);
	}

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void del(String locationId) throws NegativeException {
		logger.info("------删除轮播图或者推荐商品参数"+locationId);
		Location location = locRepository.getLocDetail(locationId);
		if(location==null)
			throw new NegativeException(NegativeCode.LOCATION_IS_NOT_EXIST, "此数据不存在");
		location.del();
		locRepository.save(location);
	}
	
	
	
	
}
