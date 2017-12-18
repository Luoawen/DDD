package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsGuaranteeApplication;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeAddCommand;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeDelCommand;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsGuaranteeRepresentation;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品保障
 */
@RestController
@RequestMapping(value = {"/goods/guarantee","/web/goods/guarantee"})
public class GoodsGuaranteeAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGuaranteeAgent.class);

    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;

    @Autowired
    GoodsGuaranteeApplication goodsGuaranteeApplication;
    
    /**
     * 查询商品保障
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsGuarantee() {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsGuaranteeBean> list = goodsGuaranteeQueryApplication.queryGoodsGuarantee();
            if (null != list && list.size() > 0) {
                List<GoodsGuaranteeRepresentation> resultList = new ArrayList<>();
                for (GoodsGuaranteeBean bean : list) {
                    resultList.add(new GoodsGuaranteeRepresentation(bean));
                }
                result.setContent(resultList);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsGuarantee Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品保障失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    
    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsGuaranteeId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_GOODS_GUARANTEE_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getGoodsGuaranteeId Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    
    /**
     * 新增商品保障
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsGuarantee(
		@RequestParam(value="guaranteeId",required = false) String guaranteeId,     //商品保障id
		@RequestParam(value="guaranteeName",required = false) String guaranteeName, //商品保障名
		@RequestParam(value="guaranteeDesc",required = false) String guaranteeDesc, // 商品保障内容
		@RequestParam(value="dealerId",required = false) String dealerId            //商家ID
	) {
		MResult result = new MResult(MCode.V_1);
		try{
			GoodsGuaranteeAddCommand command = new GoodsGuaranteeAddCommand(guaranteeId, guaranteeName, guaranteeDesc, dealerId);
			goodsGuaranteeApplication.addGoodsGuarantee(command); 
			result.setStatus(MCode.V_200);
		}catch (NegativeException ne) {
            LOGGER.error("addGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        }catch(Exception e){
			LOGGER.error("addGoodsGuarantee Exception e:", e);
            result = new MResult(MCode.V_400, "增加商品保障失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
    
    /**
	* 修改商品保障
	*/
	@RequestMapping(value = "/{guaranteeId}", method = RequestMethod.PUT)
	public ResponseEntity<MResult> modifyGoodsGuarantee(
		@PathVariable("guaranteeId") String guaranteeId,                            //商品保障id
		@RequestParam(value="guaranteeName",required = false) String guaranteeName, //商品保障名
		@RequestParam(value="guaranteeDesc",required = false) String guaranteeDesc, //商品保障内容
		@RequestParam(value="dealerId",required = false) String dealerId            //商家ID
	) {
		MResult result = new MResult(MCode.V_1);
		try{
			GoodsGuaranteeAddCommand command = new GoodsGuaranteeAddCommand(guaranteeId, guaranteeName, guaranteeDesc, dealerId);
			goodsGuaranteeApplication.modifyGoodsGuarantee(command);
			result.setStatus(MCode.V_200);
		}catch (NegativeException ne) {
            LOGGER.error("addGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        }catch(Exception e){
			LOGGER.error("modifyGoodsGuarantee Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品保障失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 删除商品保障
	 * */
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResponseEntity<MResult> deleteGoodsGuarantee(
		@RequestParam(value = "guaranteeId", required = false) String guaranteeId, //保障id
		@RequestParam(value = "dealerId", required = false) String dealerId        //商家id
			){
		MResult result = new MResult(MCode.V_1);
		try {
			GoodsGuaranteeDelCommand command = new GoodsGuaranteeDelCommand(guaranteeId, dealerId);
			goodsGuaranteeApplication.delGoodsGuarantee(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("deleteUnit NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("deleteUnitException e:", e);
			result = new MResult(MCode.V_400, "删除商品保障失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
    
}
