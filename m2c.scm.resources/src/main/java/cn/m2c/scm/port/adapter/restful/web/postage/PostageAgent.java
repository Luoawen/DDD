package cn.m2c.scm.port.adapter.restful.web.postage;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.postage.PostageModelApplication;
import cn.m2c.scm.application.postage.command.PostageModelCommand;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.data.bean.representation.PostageModelRepresentation;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import org.apache.commons.lang3.StringUtils;
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
 * 运费模板
 *
 * @author ps
 */
@RestController
@RequestMapping("/postage")
public class PostageAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostageAgent.class);

    @Autowired
    PostageModelApplication postageModelApplication;
    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getPostageId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.GOODS_POSTAGE_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("postage id Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 添加运费模板
     *
     * @param modelId           模板ID
     * @param modelName         模板名称
     * @param chargeType        计费方式,0:按重量,1:按件数
     * @param postageModelRules 模板规则,list的json字符串，格式：[{"address":"全国（默认运费）","addressStructure":"","cityCode":"123,456","continuedPiece":1,"continuedPostage":5000,"continuedWeight":1.0,"defaultFlag":1,"firstPiece":1,"firstPostage":10000,"firstWeight":2.0}]
     * @param modelDescription  模板说明
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addPostageModel(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "chargeType", required = false) Integer chargeType,
            @RequestParam(value = "postageModelRules", required = false) String postageModelRules,
            @RequestParam(value = "modelDescription", required = false) String modelDescription) {
        MResult result = new MResult(MCode.V_1);

        if (StringUtils.isEmpty(dealerId)) {
            result = new MResult(MCode.V_1, "商家ID为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        if (StringUtils.isEmpty(modelId)) {
            result = new MResult(MCode.V_1, "运费模板ID为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        if (StringUtils.isEmpty(modelName)) {
            result = new MResult(MCode.V_1, "运费模板名称为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        if (null == chargeType) {
            result = new MResult(MCode.V_1, "运费模板计费方式为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        if (StringUtils.isEmpty(postageModelRules)) {
            result = new MResult(MCode.V_1, "运费模板规则为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        try {
            PostageModelCommand command = new PostageModelCommand(dealerId, modelId, modelName, chargeType, modelDescription, postageModelRules);
            postageModelApplication.addPostageModel(command);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("addPostageModel Exception e:", e);
            result = new MResult(MCode.V_400, "添加运费模板失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询运费模板
     *
     * @param dealerId 经销商ID
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MResult> getPostageModel(
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        if (StringUtils.isEmpty(dealerId)) {
            result = new MResult(MCode.V_1, "商家ID为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        try {
            List<PostageModelBean> postageModels = postageModelQueryApplication.queryPostageModelsByDealerId(dealerId);
            if (null != postageModels && postageModels.size() > 0) {
                List<PostageModelRepresentation> list = new ArrayList<PostageModelRepresentation>();
                for (PostageModelBean model : postageModels) {
                    list.add(new PostageModelRepresentation(model));
                }
                result.setContent(list);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getPostageModel Exception e:", e);
            result = new MResult(MCode.V_400, "查询运费模板失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}