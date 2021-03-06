package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsGuaranteeApplication;
import cn.m2c.scm.application.goods.command.GoodsGuaranteeAddCommand;
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

import javax.servlet.http.HttpServletRequest;

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
    
    @Autowired
	private  HttpServletRequest request;
    
    /**
     * 查询商品保障（原接口查询系统默认保障，4个）
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
            LOGGER.error("获取GoodsGuaranteeId异常 Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    
    /**
     * 新增商品保障
     * @param guaranteeId   商品保障id
     * @param guaranteeName 商品保障名
     * @param guaranteeDesc 商品保障内容
     * @param dealerId      商家ID
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsGuarantee(
		@RequestParam(value="guaranteeId",required = false) String guaranteeId,     
		@RequestParam(value="guaranteeName",required = false) String guaranteeName, 
		@RequestParam(value="guaranteeDesc",required = false) String guaranteeDesc, 
		@RequestParam(value="dealerId",required = false) String dealerId            
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
	* @param guaranteeId   商品保障id
	* @param guaranteeName 商品保障名
	* @param guaranteeDesc 商品保障内容
	* @param dealerId      商家ID
	*/
	@RequestMapping(value = "/{guaranteeId}", method = RequestMethod.PUT)
	public ResponseEntity<MResult> modifyGoodsGuarantee(
		@PathVariable("guaranteeId") String guaranteeId,                            
		@RequestParam(value="guaranteeName",required = false) String guaranteeName, 
		@RequestParam(value="guaranteeDesc",required = false) String guaranteeDesc, 
		@RequestParam(value="dealerId",required = false) String dealerId            
	) {
		MResult result = new MResult(MCode.V_1);
		try{
			GoodsGuaranteeAddCommand command = new GoodsGuaranteeAddCommand(guaranteeId, guaranteeName, guaranteeDesc, dealerId);
			String _attach= request.getHeader("attach");
			goodsGuaranteeApplication.modifyGoodsGuarantee(command, _attach);
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
	 * @param guaranteeId   商品保障id
	 */
	@RequestMapping(value = "/del/{guaranteeId}", method = RequestMethod.DELETE)
	public ResponseEntity<MResult> deleteGoodsGuarantee(
		@PathVariable(value = "guaranteeId", required = false) String guaranteeId 
			){
		MResult result = new MResult(MCode.V_1);
		try {
			String _attach= request.getHeader("attach");
			goodsGuaranteeApplication.delGoodsGuarantee(guaranteeId, _attach);
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
	
	/**
	 * 新版本查询商品保障接口
	 * @param dealerId   商家ID
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryDealerGoodsGuarantee(
    		@RequestParam(value="dealerId",required = true) String dealerId 
    	) {
	 	MResult result = new MResult(MCode.V_1);
		try{
			List<GoodsGuaranteeBean> list = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByDealerId(dealerId);
			//最少会查出4个系统默认保障。其他查出商家自定义的保障(最多6个)。最多共(10个)
			List<GoodsGuaranteeRepresentation> resultList = new ArrayList<>();
			for (GoodsGuaranteeBean bean : list) {
				resultList.add(new GoodsGuaranteeRepresentation(bean));
			}
			result.setContent(resultList);
			result.setStatus(MCode.V_200);
		}catch(Exception e){
			LOGGER.error("queryGoodsGuarantee Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品保障失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	 }
    
}
