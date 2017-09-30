package cn.m2c.scm.port.adapter.restful.app.goods;

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
@RequestMapping("/goods/app")
public class AppGoodsAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(AppGoodsAgent.class);

    /**
     * 商品猜你喜欢
     *
     * @param totalNum        总数
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public ResponseEntity<MPager> goodsGuess(
            @RequestParam(value = "totalNum", required = false) Integer totalNum,
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
            result.setPager(totalNum, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods guess Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
