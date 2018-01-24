package cn.m2c.scm.application.postage;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.logger.OperationLogManager;
import cn.m2c.scm.application.postage.command.PostageModelCommand;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import cn.m2c.scm.domain.model.postage.PostageModel;
import cn.m2c.scm.domain.model.postage.PostageModelRepository;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运费模板
 */
@Service
@Transactional
public class PostageModelApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostageModelApplication.class);

    @Autowired
    PostageModelRepository postageModelRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsApproveRepository goodsApproveRepository;

    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;
    
    @Resource
    private OperationLogManager operationLogManager;
    
    /**
     * 添加运费模板
     *
     * @param command
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addPostageModel(PostageModelCommand command) throws NegativeException {
        LOGGER.info("addPostageModel command >>{}", command);
        PostageModel postageModel = postageModelRepository.getPostageModelById(command.getModelId());
        if (null == postageModel) {
            if (postageModelRepository.postageNameIsRepeat(null, command.getDealerId(), command.getModelName())) {
                throw new NegativeException(MCode.V_300, "运费模板名称已存在");
            }
            if(command.getChargeType() == 2) {//2全国包邮模板
            	if(postageModelQueryApplication.queryDealerPostageNationwide(command.getDealerId(), command.getModelId())) {//true已存在，false不存在（全国包邮模板）
                	throw new NegativeException(MCode.V_300, "全国包邮模板已存在");
                }
            }
            postageModel = new PostageModel(command.getDealerId(), command.getModelId(), command.getModelName(), command.getChargeType(),
                    command.getModelDescription(), command.getPostageModelRule());
            postageModelRepository.save(postageModel);
        }
    }

    /**
     * 修改运费模板
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyPostageModel(PostageModelCommand command, String _attach) throws NegativeException {
        LOGGER.info("modifyPostageModel command >>{}", command);
        PostageModel postageModel = postageModelRepository.getPostageModelById(command.getModelId());
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        if (postageModelRepository.postageNameIsRepeat(command.getModelId(), command.getDealerId(), command.getModelName())) {
            throw new NegativeException(MCode.V_300, "运费模板名称已存在");
        }
        if(command.getChargeType()==2) {//全国包邮模板
        	if(postageModelQueryApplication.queryDealerPostageNationwide(command.getDealerId(), command.getModelId())) {////true已存在，false不存在（全国包邮模板）
        		throw new NegativeException(MCode.V_300, "全国包邮模板已存在");
        	}
        }
        if (StringUtils.isNotEmpty(_attach))
        	operationLogManager.operationLog("修改运费模板", _attach, postageModel, new String[]{"postageModel"}, null);
        postageModel.modifyPostageModel(command.getDealerId(), command.getModelId(), command.getModelName(), command.getChargeType(),
                command.getModelDescription(), command.getPostageModelRule());
    }

    /**
     * 删除运费模板
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void delPostageModel(PostageModelCommand command, String _attach) throws NegativeException {
        LOGGER.info("delPostageModel command >>{}", command);
        PostageModel postageModel = postageModelRepository.getPostageModelById(command.getModelId());
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        // 判断是否有商品，有商品不能删除
        if (goodsRepository.postageIdIsUser(command.getModelId()) || goodsApproveRepository.postageIdIsUser(command.getModelId())) {
            throw new NegativeException(MCode.V_301, "运费模板有商品使用，不能删除");
        }

        if (StringUtils.isNotEmpty(_attach))
        	operationLogManager.operationLog("删除运费模板", _attach, postageModel, new String[]{"postageModel"}, null);
        postageModel.deletePostageModel();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsUserNum(String modelId) throws NegativeException {
        LOGGER.info("addGoodsUserNum modelId >>{}", modelId);
        PostageModel postageModel = postageModelRepository.getPostageModelById(modelId);
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        postageModel.addGoodsUserNum();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void outGoodsUserNum(String modelId) throws NegativeException {
        LOGGER.info("outGoodsUserNum modelId >>{}", modelId);
        PostageModel postageModel = postageModelRepository.getPostageModelById(modelId);
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        postageModel.outGoodsUserNum();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsUserNum(String oldModelId, String newModelId) throws NegativeException {
        LOGGER.info("outGoodsUserNum oldModelId >>{},newModelId >>{}", oldModelId, newModelId);
        PostageModel postageModel = postageModelRepository.getPostageModelById(oldModelId);
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        postageModel.outGoodsUserNum();

        PostageModel newPostageModel = postageModelRepository.getPostageModelById(newModelId);
        if (null == newPostageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        newPostageModel.addGoodsUserNum();
    }
}
