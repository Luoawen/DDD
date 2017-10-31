package cn.m2c.scm.application.postage;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.postage.command.PostageModelCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.postage.PostageModel;
import cn.m2c.scm.domain.model.postage.PostageModelRepository;
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
    public void modifyPostageModel(PostageModelCommand command) throws NegativeException {
        LOGGER.info("modifyPostageModel command >>{}", command);
        PostageModel postageModel = postageModelRepository.getPostageModelById(command.getModelId());
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
        postageModel.modifyPostageModel(command.getDealerId(), command.getModelId(), command.getModelName(), command.getChargeType(),
                command.getModelDescription(), command.getPostageModelRule());
    }

    /**
     * 删除运费模板
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void delPostageModel(PostageModelCommand command) throws NegativeException {
        LOGGER.info("delPostageModel command >>{}", command);
        PostageModel postageModel = postageModelRepository.getPostageModelById(command.getModelId());
        if (null == postageModel) {
            throw new NegativeException(MCode.V_300, "运费模板不存在");
        }
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
}
