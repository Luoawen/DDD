package cn.m2c.scm.port.adapter.restful.web.postage;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.postage.PostageModelApplication;
import cn.m2c.scm.application.postage.command.PostageModelCommand;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.data.representation.PostageModelDetailRepresentation;
import cn.m2c.scm.application.postage.data.representation.PostageModelRepresentation;
import cn.m2c.scm.application.postage.data.representation.PostageModelRuleRepresentation;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 运费模板
 *
 * @author ps
 */
@RestController
public class PostageAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostageAgent.class);

    @Autowired
    PostageModelApplication postageModelApplication;
    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/web/postage/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getPostageId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_POSTAGE_PREFIX_TITLE);
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
     * @param dealerId          经销商ID
     * @param modelId           模板ID
     * @param modelName         模板名称
     * @param chargeType        计费方式,0:按重量,1:按件数,2:全国包邮
     * @param postageModelRules 模板规则,list的json字符串，格式：[{"address":"全国（默认运费）","cityCode":"123,456","continuedPiece":1,"continuedPostage":5000,"continuedWeight":1.0,"defaultFlag":1,"firstPiece":1,"firstPostage":10000,"firstWeight":2.0}]
     * @param modelDescription  模板说明
     * @return
     */
    @RequestMapping(value = "/web/postage", method = RequestMethod.POST)
    public ResponseEntity<MResult> addPostageModel(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "chargeType", required = false) Integer chargeType,
            @RequestParam(value = "postageModelRules", required = false) String postageModelRules,
            @RequestParam(value = "modelDescription", required = false) String modelDescription) {
        MResult result = new MResult(MCode.V_1);
        try {
            PostageModelCommand command = new PostageModelCommand(dealerId, modelId, modelName, chargeType, modelDescription, postageModelRules);
            postageModelApplication.addPostageModel(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addPostageModel NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addPostageModel Exception e:", e);
            result = new MResult(MCode.V_400, "添加运费模板失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改运费模板
     *
     * @param dealerId          经销商ID
     * @param modelId           模板ID
     * @param modelName         模板名称
     * @param chargeType        计费方式,0:按重量,1:按件数
     * @param postageModelRules 模板规则,list的json字符串，格式：[{"address":"全国（默认运费）","cityCode":"123,456","continuedPiece":1,"continuedPostage":5000,"continuedWeight":1.0,"defaultFlag":1,"firstPiece":1,"firstPostage":10000,"firstWeight":2.0}]
     * @param modelDescription  模板说明
     * @return
     */
    @RequestMapping(value = "/web/postage", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyPostageModel(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "modelName", required = false) String modelName,
            @RequestParam(value = "chargeType", required = false) Integer chargeType,
            @RequestParam(value = "postageModelRules", required = false) String postageModelRules,
            @RequestParam(value = "modelDescription", required = false) String modelDescription) {
        MResult result = new MResult(MCode.V_1);
        try {
            PostageModelCommand command = new PostageModelCommand(dealerId, modelId, modelName, chargeType, modelDescription, postageModelRules);
            String _attach = request.getHeader("attach");
            postageModelApplication.modifyPostageModel(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyPostageModel NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyPostageModel Exception e:", e);
            result = new MResult(MCode.V_400, "修改运费模板失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/web/postage", method = RequestMethod.DELETE)
    public ResponseEntity<MResult> delPostageModel(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "modelId", required = false) String modelId) {
        MResult result = new MResult(MCode.V_1);
        try {
            PostageModelCommand command = new PostageModelCommand(dealerId, modelId);
            String _attach = request.getHeader("attach");
            postageModelApplication.delPostageModel(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("delPostageModel NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("delPostageModel Exception e:", e);
            result = new MResult(MCode.V_400, "删除运费模板失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询运费模板
     *
     * @param dealerId 经销商ID
     * @return
     */
    @RequestMapping(value = "/web/postage", method = RequestMethod.GET)
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

    @RequestMapping(value = "/web/postage/{modelId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> postageModelDetail(
            @PathVariable("modelId") String modelId) {
        MResult result = new MResult(MCode.V_1);
        try {
            PostageModelBean bean = postageModelQueryApplication.queryPostageModelsByModelId(modelId);
            if (null != bean) {
                result.setContent(new PostageModelDetailRepresentation(bean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("postageModelDetail Exception e:", e);
            result = new MResult(MCode.V_400, "查询运费模板详情失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/postage/rule", method = RequestMethod.GET)
    public ResponseEntity<MResult> postageModelRule(
            @RequestParam(value = "goodsIds", required = false) List<String> goodsIds,
            @RequestParam(value = "cityCode", required = false) String cityCode) {
        MResult result = new MResult(MCode.V_1);
        try {
            Map<String, PostageModelRuleRepresentation> map = postageModelQueryApplication.getGoodsPostageRuleByGoodsId(goodsIds, cityCode);
            result.setContent(map);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("postageModelRule Exception e:", e);
            result = new MResult(MCode.V_400, "查询运费模板规则失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 查询商家是否已经创建全国包邮模板
     * @param dealerId 商家id
     * @return
     */
    @RequestMapping(value = "/web/postage/nationwide", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryDealerPostageNationwide(
    		@RequestParam(value = "dealerId", required = false) String dealerId){
    	MResult result = new MResult(MCode.V_1);
    	if (StringUtils.isEmpty(dealerId)) {
            result = new MResult(MCode.V_1, "商家ID为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
    	try {
            Boolean flag = postageModelQueryApplication.queryDealerPostageNationwide(dealerId);
            if(flag == true) {//true商家可创建包邮模板
            	result.setContent(flag);
                result.setStatus(MCode.V_200);
            } else {
            	result = new MResult(MCode.V_300, "全国包邮模板已存在");
            }
        } catch (Exception e) {
            LOGGER.error("queryDealerPostageNationwide Exception e:", e);
            result = new MResult(MCode.V_400, "查询商家是否已经创建全国包邮模板失败");
        }
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
