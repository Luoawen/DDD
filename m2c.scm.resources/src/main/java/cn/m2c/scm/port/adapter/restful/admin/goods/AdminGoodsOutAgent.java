package cn.m2c.scm.port.adapter.restful.admin.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsRandomRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品对运营后台提供的接口
 */
@RestController
@RequestMapping("/admin/goods-out")
public class AdminGoodsOutAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsOutAgent.class);
	
	@Autowired
	GoodsQueryApplication goodsQueryApplication;
	
	/**
	 * 运营平台根据商品名模糊查询最早十个商品
	 * @param goodsName
	 * @return
	 */
	@RequestMapping(value = "/pioneer/ten", method = RequestMethod.GET)
	public ResponseEntity<MResult> queryGoodsPioneerTenByGoodsName(
		@RequestParam(value = "goodsName", required = false) String goodsName
		){
		MResult result = new MResult(MCode.V_1);
		try {
			List<GoodsBean> goodsBeans = goodsQueryApplication.queryGoodsPioneerTenByGoodsName(goodsName);
			if (null != goodsBeans && goodsBeans.size() > 0) {
				List<GoodsRandomRepresentation> representations = new ArrayList<>();
				for (GoodsBean goodsBean : goodsBeans) {
					representations.add(new GoodsRandomRepresentation(goodsBean));
				}
				result.setContent(representations);
			}
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("queryGoodsPioneerTenByGoodsName Exception e:", e);
			result = new MResult(MCode.V_400, "查询最早十个商品失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
}
