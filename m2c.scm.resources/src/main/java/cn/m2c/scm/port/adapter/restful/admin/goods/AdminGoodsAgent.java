package cn.m2c.scm.port.adapter.restful.admin.goods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.domain.NegativeException;
/**
 * 商品
 */
@RestController
@RequestMapping(value = {"web/goods", "/admin/goods"})
public class AdminGoodsAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsAgent.class);
	
	@Autowired
    GoodsApplication goodsApplication;
	
	/**
     * 商品批量上架,未鉴权
     */
    @RequestMapping(value = {"/up/shelfbatch"}, method = RequestMethod.PUT)
    public ResponseEntity<MResult> upShelfGoodsBatch(
    		@RequestParam("goodsIds") List goodsIds
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
            goodsApplication.upShelfGoodsBatch(goodsIds);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("upShelfGoodsBatch NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("upShelfGoodsBatch Exception e:", e);
            result = new MResult(MCode.V_400, "商品批量上架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 商品批量下架,未鉴权
     * @param goodsIds
     * @return
     */
    @RequestMapping(value = {"/off/shelfbatch"}, method = RequestMethod.PUT)
    public ResponseEntity<MResult> offShelfGoodsBatch(
    		@RequestParam("goodsIds") List goodsIds
    		){
    	MResult result = new MResult(MCode.V_1);
        try {
            goodsApplication.offShelfGoodsBatch(goodsIds);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("offShelfGoodsBatch NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("offShelfGoodsBatch Exception e:", e);
            result = new MResult(MCode.V_400, "商品批量下架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
