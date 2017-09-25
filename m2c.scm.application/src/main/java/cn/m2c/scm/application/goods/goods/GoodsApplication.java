package cn.m2c.scm.application.goods.goods;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.goods.domain.classify.GCountEvent;
import cn.m2c.goods.domain.classify.GoodsClassify;
import cn.m2c.goods.domain.classify.GoodsClassifyRepository;
import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.dealer.DealerRepository;
import cn.m2c.goods.domain.goods.ChangeGoodsName;
import cn.m2c.goods.domain.goods.Goods;
import cn.m2c.goods.domain.goods.GoodsAddEvent;
import cn.m2c.goods.domain.goods.GoodsRepository;
import cn.m2c.goods.domain.goods.GoodsStatusEvent;
import cn.m2c.goods.domain.goods.MDGRecEvent;
import cn.m2c.goods.domain.goods.MDGViewEvent;
import cn.m2c.goods.exception.NegativeCode;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.command.GoodsAddMediaCommand;
import cn.m2c.scm.application.goods.goods.command.GoodsAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.command.MDViewGoodsCommand;
import cn.m2c.scm.application.goods.goods.command.RecognizedPicCommand;
import cn.m2c.support.interfaces.dubbo.RecognizeService;


@Service
@Transactional
public class GoodsApplication {
	private  Logger logger = LoggerFactory.getLogger(GoodsApplication.class);

	

	@Autowired
	DealerRepository dealerRepository;
	@Autowired
	GoodsRepository goodsRepository;
	@Autowired
	GoodsClassifyRepository goodsClassifyRepository;
	
	@Autowired
	RecognizeService recognizeService;
	/**
	 * 添加商品,发出事件，商品分类的数目加一
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void addGood(GoodsAddOrUpdateCommand command) throws NegativeException {
		logger.info("goods add command >>{}", command);		
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		//--------------------------------------
		DomainEvent first= new GCountEvent(command.getFirstClassify(),1);
		DomainEventPublisher.instance().publish(first);
		DomainEvent second= new GCountEvent(command.getSecondClassify(),1);
		DomainEventPublisher.instance().publish(second);
		
		
		//-----------------------------业务员事件
		String sellerId = dealer.getSellerId();//获取业务员id
		if(sellerId!=null && !"".equals(sellerId)){
			DomainEvent staffGoods = new GoodsAddEvent(command.getGoodsId(),sellerId, System.currentTimeMillis());
			DomainEventPublisher.instance().publish(staffGoods);
		}
		Goods goods = new Goods();
		goods.add(command.getGoodsId(),command.getDealerId(),dealer.getDealerName(),dealer.getSellerId(),dealer.getSellerName(),dealer.getCooperationMode(),command.getFirstClassify(),command.getSecondClassify(),command.getGoodsNo(),command.getBarNo(),command.getGoodsName(),command.getSubtitle(),command.getSalePrice(),command.getMarketPrice(),command.getBrandId(),
				command.getPropertyId(),command.getTransportFeeId(),command.getGuarantee(),command.getAdvertisementPic(),command.getGallery(),command.getGoodsDesc(),command.getDivideScale());
		goodsRepository.save(goods);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void updateGood(GoodsAddOrUpdateCommand command) throws NegativeException {
		logger.info("goods update command >>{}", command);		
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Goods goods = goodsRepository.getGoodsDetail(command.getGoodsId());
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		//--------------------------------------
		if(goods.getSecondClassify()!=command.getSecondClassify()){
			System.out.println("-------------");
			if(goods.getFirstClassify()!=command.getFirstClassify()){
				DomainEvent newFirst= new GCountEvent(command.getFirstClassify(),1);
				DomainEventPublisher.instance().publish(newFirst);
				DomainEvent oldFirst= new GCountEvent(goods.getFirstClassify(),-1);
				DomainEventPublisher.instance().publish(oldFirst);
			}
			DomainEvent newSecond= new GCountEvent(command.getSecondClassify(),1);
			DomainEventPublisher.instance().publish(newSecond);
			DomainEvent oldSecond= new GCountEvent(goods.getSecondClassify(),-1);
			DomainEventPublisher.instance().publish(oldSecond);
		}
		//--------修改商品名字事件
		logger.info("----新商品名："+command.getGoodsName()+"--------老商品名"+goods.getGoodsName());
		if(hasText(command.getGoodsName()) && !goods.getGoodsName().equals(command.getGoodsName())){
			logger.info("发出修改商品名称事件");
			DomainEvent changeGoodsName= new ChangeGoodsName(goods.getGoodsId(),command.getGoodsName());
			DomainEventPublisher.instance().publish(changeGoodsName);
		}
		
		
		//-----------------------------业务员事件
		if(!goods.getDealerId().equals(command.getDealerId())){
			logger.info("----更新商品操作时候业务员业绩统计--old dealer"+goods.getDealerId()+"-----------new dealer-----"+command.getDealerId());
			Dealer olddealer = dealerRepository.getDealerDetail(goods.getDealerId());
			if(olddealer!=null && !olddealer.getSellerId().equals(dealer.getSellerId())){
				logger.info("发出更新商品统计业绩事件");
				DomainEvent staffGoods = new GoodsAddEvent(command.getGoodsId(),dealer.getSellerId(), System.currentTimeMillis());
				DomainEventPublisher.instance().publish(staffGoods);
			}
		}
		
		logger.info("=======pc更新商品的参数"+goods.getFirstClassify()+"->"+command.getFirstClassify()+"==============="+goods.getSecondClassify()+"->"+command.getSecondClassify());
		goods.update(command.getDealerId(),dealer.getDealerName(),dealer.getSellerId(),dealer.getSellerName(),dealer.getCooperationMode(),command.getFirstClassify(),command.getSecondClassify(),command.getGoodsNo(),command.getBarNo(),command.getGoodsName(),command.getSubtitle(),command.getSalePrice(),command.getMarketPrice(),command.getBrandId(),
				command.getPropertyId(),command.getTransportFeeId(),command.getGuarantee(),command.getAdvertisementPic(),command.getGallery(),command.getGoodsDesc(),command.getDivideScale());
		goodsRepository.save(goods);
		
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void delGood(String goodsId) throws NegativeException {
		logger.info("goods delete command >>{}", goodsId);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		//--------------------------------------
		DomainEvent first= new GCountEvent(goods.getFirstClassify(),-1);
		DomainEventPublisher.instance().publish(first);
		DomainEvent second= new GCountEvent(goods.getSecondClassify(),-1);
		DomainEventPublisher.instance().publish(second);

		GoodsClassify goodsClassifyDetail = goodsClassifyRepository.getGoodsClassifyDetail(goods.getSecondClassify());
		String goodsClassifyId = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyId();
		String goodsClassifyName = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyName();
		DomainEvent delGoods= new GoodsStatusEvent(goods.getGoodsId(),goods.getGoodsName(),goods.getMediaId(),goods.getMediaName(),goods.getMresId(),goods.getMresName(),goods.getGoodsStatus(),2,goodsClassifyId,goodsClassifyName);
		DomainEventPublisher.instance().publish(delGoods);
		goods.delete();
		goodsRepository.save(goods);
	}
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void updateStatus(String goodsId, Integer goodStatus) throws NegativeException {
		logger.info("goods updateStatus command >>{}", goodsId,goodStatus);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
//		if(goods.getGoodsStatus()!=goodStatus && goodStatus==3)
//		{//上架事件
		
		if(goods.getGoodsStatus()!=goodStatus){
			GoodsClassify goodsClassifyDetail = goodsClassifyRepository.getGoodsClassifyDetail(goods.getSecondClassify());
			String goodsClassifyId = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyId();
			String goodsClassifyName = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyName();
			DomainEvent up= new GoodsStatusEvent(goods.getGoodsId(),goods.getGoodsName(),goods.getMediaId(),goods.getMediaName(),goods.getMresId(),goods.getMresName(),goods.getGoodsStatus(),goodStatus,goodsClassifyId,goodsClassifyName);
			DomainEventPublisher.instance().publish(up);
			goods.setUpGoodsDate(new Date());
		}
			
			/**
			 * 图片上下架操作
			 */
		
		if(goods.getGoodsStatus()!=goodStatus && goodStatus==3){
			
			if(hasText(goods.getRecognizedId())&& hasText(goods.getRecognizedUrl())){
				boolean updateRecoImgStatus = recognizeService.updateRecoImgStatus(goods.getRecognizedId(), goods.getRecognizedUrl(), 1);
				if(updateRecoImgStatus){
					logger.info("上架商品调用support 切换识别图片状态成功");
				}else{
					logger.info("上架商品调用support 切换识别图片状态失败");
					throw new NegativeException(NegativeCode.GOODS_DOWN_RECOGNIZED_PIC_FAILED, "下架商品识别图片切换失败");
				}
			}
		}
			
//		if(goods.getGoodsStatus()!=goodStatus && goodStatus==4)
//		{//下架事件
//			GoodsClassify goodsClassifyDetail = goodsClassifyRepository.getGoodsClassifyDetail(goods.getSecondClassify());
//			String goodsClassifyId = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyId();
//			String goodsClassifyName = goodsClassifyDetail==null?"":goodsClassifyDetail.getGoodsClassifyName();
//			DomainEvent down= new GoodsStatusEvent(goods.getGoodsId(),goods.getGoodsName(),goods.getMediaId(),goods.getMediaName(),goods.getMresId(),goods.getMresName(),goodStatus,goodsClassifyId,goodsClassifyName);
//			DomainEventPublisher.instance().publish(down);
//			
//			goods.setDownGoodsDate(new Date());
			
//			if(!"".equals(goods.getRecognizedId()) && !"".equals(goods.getRecognizedUrl())){
//				boolean updateRecoImgStatus = recognizeService.updateRecoImgStatus(goods.getRecognizedId(), goods.getRecognizedUrl(), 0);
//				if(updateRecoImgStatus){
//					logger.info("下架商品调用support 切换识别图片状态成功");
//				}else{
//					logger.info("下架商品调用support 切换识别图片状态失败");
//				}
//			}
//		}
		if(goods.getGoodsStatus()!=goodStatus && goods.getGoodsStatus()==3){
			if(hasText(goods.getRecognizedId())&& hasText(goods.getRecognizedUrl())){
				boolean updateRecoImgStatus = recognizeService.updateRecoImgStatus(goods.getRecognizedId(), goods.getRecognizedUrl(), 0);
				if(updateRecoImgStatus){
					logger.info("下架商品调用support 切换识别图片状态成功");
				}else{
					logger.info("下架商品调用support 切换识别图片状态失败");
					throw new NegativeException(NegativeCode.GOODS_DOWN_RECOGNIZED_PIC_FAILED, "下架商品识别图片切换失败");
				}
			}
		}
		goods.updateStatus(goodStatus);
		goodsRepository.save(goods);
		
	}
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void getMediaInfo(GoodsAddMediaCommand command, String preGoodsId) throws NegativeException {
		logger.info("goods update getMediaInfo command >>{}", command,preGoodsId);	
		Goods goods = goodsRepository.getGoodsDetail(command.getGoodsId());
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.updateMediaInfo(command.getMediaId(),command.getMediaName(),command.getMresId(),command.getMresName());
		goodsRepository.save(goods);
		if(preGoodsId!=null && !"".equals(preGoodsId)){
			Goods preGoods = goodsRepository.getGoodsDetail(preGoodsId);
			if(preGoods==null)
				throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
			preGoods.unbind();
			goodsRepository.save(preGoods);
		}
	}
	/**
	 * 更新识别图片
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updateRecognizedPic(RecognizedPicCommand command) throws NegativeException {
		logger.info("goods update updateRecognizedPic command >>{}", command);	
		Goods goods = goodsRepository.getGoodsDetail(command.getGoodsId());
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		//将原来的识别图设置为空
		if(goods.getRecognizedId()!=null && !"".equals(goods.getRecognizedId())){
			boolean updateRecoImgStatus = recognizeService.updateRecoImgStatus(goods.getRecognizedId(), goods.getRecognizedUrl(), 0);
			if(updateRecoImgStatus){
				logger.info("下架商品调用support 切换识别图片状态成功");
			}else{
				logger.info("下架商品调用support 切换识别图片状态失败");
				throw new NegativeException(NegativeCode.GOODS_DOWN_RECOGNIZED_PIC_FAILED, "下架商品识别图片切换失败");
			}
		}
		//新的识别图片id加上
		if(command.getRecognizedId()!=null && !"".equals(command.getRecognizedId())){
			boolean updateRecoImgStatus = recognizeService.updateRecoImgStatus(command.getRecognizedId(), command.getRecognizedUrl(), 1);
			if(updateRecoImgStatus){
				logger.info("上架商品调用support 切换识别图片状态成功");
			}else{
				logger.info("上架商品调用support 切换识别图片状态失败");
				throw new NegativeException(NegativeCode.GOODS_UP_RECOGNIZED_PIC_FAILED, "上架商品识别图片切换失败");
			}
		}
		
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		goods.updateRecognizedPic(command.getGoodsId(),command.getGoodsName(),command.getDealerId(),command.getDealerName(),command.getRecognizedId(),command.getRecognizedUrl());
		goodsRepository.save(goods);
	}
	/**
	 * 消费商品收藏事件
	 * @param goodsId
	 * @param favoriteCountChange
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void countFavorite(String goodsId, Integer favoriteCountChange) throws NegativeException {
		logger.info("goods update updateRecognizedPic command >>{}", goodsId,favoriteCountChange);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.changeFavoriteCount(favoriteCountChange);
		goodsRepository.save(goods);
	}
	/**
	 * 消费订单信息
	 * @param goodsId
	 * @param goodsNum
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeOrderInfo(String goodsId, Integer goodsNum) throws NegativeException {
		logger.info("goods update updateRecognizedPic command >>{}", goodsId,goodsNum);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.orderInfo(goodsNum);
		goodsRepository.save(goods);
	}

	/**
	 * 分享商品
	 * @param goodsId
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void shareNum(String goodsId) throws NegativeException {
		logger.info("goods shareNum command >>{}", goodsId);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.updateShare();
		goodsRepository.save(goods);
	}
	/**
	 * 商品访问埋点
	 * @param command
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void MDViewGoods(MDViewGoodsCommand command) {
		logger.info("MDViewGoodsCommand  command >>{}", command);	
		DomainEvent goodsViewEvent= new MDGViewEvent(command.getSn(),command.getOs(),command.getAppVersion(),command.getOsVersion(),command.getTriggerTime(),command.getLastTime(),command.getUserId(),command.getUserName(),command.getAreaProvince(),command.getAreaDistrict(),command.getProvinceCode(),command.getDistrictCode(),command.getGoodsId(),command.getGoodsName(),command.getMediaId(),command.getMediaName(),command.getMresId(),command.getMresName());
		DomainEventPublisher.instance().publish(goodsViewEvent);
	}
	
	/**
	 * 商品识别埋点
	 * @param sn
	 * @param os
	 * @param appVersion
	 * @param osVersion
	 * @param triggerTime
	 * @param userId
	 * @param userName
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	@EventListener
	public void recognizedMD(MDViewGoodsCommand command) {
		logger.info("MDrecognizedGoodsCommand  command >>{}", command);	
		DomainEvent goodsRecognizedEvent= new MDGRecEvent(command.getSn(),command.getOs(),command.getAppVersion(),command.getOsVersion(),command.getTriggerTime(),command.getUserId(),command.getUserName(),command.getGoodsId(),command.getGoodsName(),command.getMediaId(),command.getMediaName(),command.getMresId(),command.getMresName());
		DomainEventPublisher.instance().publish(goodsRecognizedEvent);
	}	
		


	private boolean hasText(String param){
		boolean result = false;
		if(param!=null && !"".equals(param)){
			result = true;
		}
		return result;
	}
	/**
	 * 订单换货事件
	 * @param goodsId
	 * @param goodsNum
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeGoodsChange(String goodsId, Integer goodsNum) throws NegativeException {
		logger.info("商品换货事件>>{}", goodsId,goodsNum);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.changeGoods(goodsNum);
		goodsRepository.save(goods);
	}
	/**
	 * 订单退货事件
	 * @param goodsId
	 * @param goodsNum
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void goodsPay(String goodsId, Integer goodsNum) throws NegativeException {
		logger.info("商品退货事件>>{}", goodsId,goodsNum);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.payGoods(goodsNum);
		goodsRepository.save(goods);
	}
	/**
	 * 订单支付成功事件
	 * @param goodsId
	 * @param goodsNum
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeGoodsreturn(String goodsId, Integer goodsNum) throws NegativeException {
		logger.info("商品退货事件>>{}", goodsId,goodsNum);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.returnGoods(goodsNum);
		goodsRepository.save(goods);
		
	}
	/**
	 * 消费媒体资源变更
	 * @param goodsId
	 * @param preGoodsId
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void getMediaInfo(String goodsId, String preGoodsId) throws NegativeException {
		logger.info("媒体资源变更事件>>{}", goodsId,preGoodsId);	
		Goods goods = goodsRepository.getGoodsDetail(preGoodsId);
		Goods newMresGoods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null || newMresGoods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.unbind();
		goodsRepository.save(goods);
		newMresGoods.bind();
		goodsRepository.save(newMresGoods);
		
	}
	/**
	 * 媒体资源删除事件
	 * @param goodsId
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void deleteMres(String goodsId) throws NegativeException {
		logger.info("媒体资源删除事件>>{}", goodsId);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.unbind();
		goodsRepository.save(goods);
	}
	/**
	 * 媒体资源绑定成功
	 * @param goodsId
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void bindMres(String goodsId) throws NegativeException {
		logger.info("媒体资源绑定事件>>{}", goodsId);	
		Goods goods = goodsRepository.getGoodsDetail(goodsId);
		if(goods==null)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		goods.bind();
		goodsRepository.save(goods);
		
	}
	/**
	 * 修改商品中心业务员id和名字
	 * @param dealerId
	 * @param sellerId
	 * @param sellerName
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeSellerNameChange(String dealerId, String sellerId,
			String sellerName) throws NegativeException {
		logger.info("修改商品中心业务员id和名字>>{}", dealerId);	
		List<Goods> goodsList = goodsRepository.getGoodsList(dealerId);
		if(goodsList==null || goodsList.size()==0)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		for (int i = 0; i < goodsList.size(); i++) {
			goodsList.get(i).changeSellName(sellerId,sellerName);
			goodsRepository.save(goodsList.get(i));
		}
	}
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void consumeDealerNameChange(String dealerId, String dealerName) throws NegativeException {
		logger.info("修改商品中心经销商名字>>{}", dealerId,dealerName);	
		List<Goods> goodsList = goodsRepository.getGoodsList(dealerId);
		if(goodsList==null || goodsList.size()==0)
			throw new NegativeException(NegativeCode.GOODS_IS_NOT_EXIST, "此商品不存在.");
		for (int i = 0; i < goodsList.size(); i++) {
			goodsList.get(i).changeDealerName(dealerName);
			goodsRepository.save(goodsList.get(i));
		}
		
	}
}