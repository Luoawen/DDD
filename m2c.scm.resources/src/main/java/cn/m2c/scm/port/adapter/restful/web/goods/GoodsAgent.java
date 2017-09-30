package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods")
public class GoodsAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsAgent.class);

    /**
     * 商品筛选根据商品类别，名称、标题、编号筛选
     *
     * @param goodsClassifyId 商品类别
     * @param condition       名称、标题、编号
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = "/choice", method = RequestMethod.GET)
    public ResponseEntity<MPager> goodsChoice(
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            List<Map<String, Object>> goodsList = new ArrayList<>();
            Map map = new HashMap<>();
            map.put("goodsId", "SP449EF119C6974667B9F5881C080EE5D2");
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            map.put("goodsPrice", 249000);
            Map map1 = new HashMap<>();
            map1.put("goodsId", "SP38C4D0B014E24B64B021EAC4D813A696");
            map1.put("goodsName", "儿童自行车");
            map1.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/bx2L173127.jpg");
            map1.put("goodsPrice", 89900);

            goodsList.add(map);
            goodsList.add(map1);
            result.setContent(goodsList);
            result.setPager(2, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods choice Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 商品详情
     *
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<MPager> goodsDetail(
            @RequestParam(value = "goodsId", required = false) String goodsId) {
        MPager result = new MPager(MCode.V_1);
        try {
            Map map = new HashMap<>();
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            map.put("goodsPrice", 249000);
            result.setContent(map);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods Detail Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
