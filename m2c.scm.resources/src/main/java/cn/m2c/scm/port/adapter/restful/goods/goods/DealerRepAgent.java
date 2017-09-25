package cn.m2c.scm.port.adapter.restful.goods.goods;

import java.util.List;
import java.util.Map;

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
import cn.m2c.common.MPager;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcDealerRepQuery;



@RestController
@RequestMapping("/dealerRep")
public class DealerRepAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(DealerRepAgent.class);
	
	@Autowired
	SpringJdbcDealerRepQuery query;
	
	@RequestMapping(value = "/onLineGoodsList",method = RequestMethod.GET)
	public ResponseEntity<MPager> list(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		try {
			List<Map<String,Object>> onLineGoodsList = query.getDealerOnLineGoods(dealerId,pageNum,rows);
			Integer count = query.getDealerOnLineGoodsCount(dealerId);
			result.setPager(count, pageNum, rows);
			result.setContent(onLineGoodsList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("经销商的在线商品统计失败", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("经销商的在线商品统计失败", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 下线商品查询
	 * @param token
	 * @param dealerId
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/downLineGoodsList",method = RequestMethod.GET)
	public ResponseEntity<MPager> downLineGoodsList(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		try {
			List<Map<String,Object>> downLineGoodsList = query.getDealerDownLineGoods(dealerId,pageNum,rows);
			Integer count = query.getDealerDownLineGoodsCount(dealerId);
			result.setPager(count, pageNum, rows);
			result.setContent(downLineGoodsList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("经销商的下线商品统计失败", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("经销商的下线商品统计失败", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
}
