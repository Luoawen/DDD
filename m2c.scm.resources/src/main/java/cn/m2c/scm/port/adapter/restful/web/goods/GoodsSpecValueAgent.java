package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsSpecValueApplication;
import cn.m2c.scm.application.goods.command.GoodsSpecValueCommand;
import cn.m2c.scm.application.goods.query.GoodsSpecValueQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSpecValueBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsSpecValueRepresentation;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
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
 * 商品规格值
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods/spec/value")
public class GoodsSpecValueAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsSpecValueAgent.class);
    @Autowired
    GoodsSpecValueApplication goodsSpecValueApplication;
    @Autowired
    GoodsSpecValueQueryApplication goodsSpecValueQueryApplication;

    /**
     * 添加规格值
     *
     * @param dealerId
     * @param specValue
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsSpecValue(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "standardId", required = false) String standardId,
            @RequestParam(value = "specValue", required = false) String specValue) {
        MResult result = new MResult(MCode.V_1);
        String specId = IDGenerator.get(IDGenerator.SCM_GOODS_SPEC_VALUE_PREFIX_TITLE);
        GoodsSpecValueCommand command = new GoodsSpecValueCommand(specId, dealerId, standardId, specValue);
        try {
            goodsSpecValueApplication.addGoodsSpecValue(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addGoodsSpecValue NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addGoodsSpecValue Exception e:", e);
            result = new MResult(MCode.V_400, "添加规格值失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询规格值
     *
     * @param dealerId
     * @param specValue
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsSpecValue(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "standardId", required = false) String standardId,
            @RequestParam(value = "specValue", required = false) String specValue) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsSpecValueBean> list = goodsSpecValueQueryApplication.queryGoodsSpecValueByName(dealerId, standardId, specValue);
            if (null != list && list.size() > 0) {
                List<GoodsSpecValueRepresentation> resultList = new ArrayList<>();
                for (GoodsSpecValueBean bean : list) {
                    resultList.add(new GoodsSpecValueRepresentation(bean));
                }
                result.setContent(resultList);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsSpecValue Exception e:", e);
            result = new MResult(MCode.V_400, "查询规格值失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
