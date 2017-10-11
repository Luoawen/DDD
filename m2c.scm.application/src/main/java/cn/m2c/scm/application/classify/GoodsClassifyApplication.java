package cn.m2c.scm.application.classify;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.scm.application.classify.command.GoodsClassifyAddCommand;
import cn.m2c.scm.application.classify.command.GoodsClassifyModifyCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.classify.GoodsClassify;
import cn.m2c.scm.domain.model.classify.GoodsClassifyRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品分类
 */
@Service
@Transactional
public class GoodsClassifyApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsClassifyApplication.class);

    @Autowired
    GoodsClassifyRepository goodsClassifyRepository;

    /**
     * 添加商品分类
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsClassify(GoodsClassifyAddCommand command) throws NegativeException {
        LOGGER.info("addGoodsClassify command >>{}", command);
        // 父级分类名称不为空，则增加一级分类
        if (StringUtils.isNotEmpty(command.getParentClassifyName())) {
            GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(command.getParentClassifyId());
            if (null != goodsClassify) {
                throw new NegativeException(MCode.V_300, "商品分类ID已存在");
            }
            goodsClassify = new GoodsClassify(command.getParentClassifyId(), command.getParentClassifyName(), "-1");
            goodsClassifyRepository.save(goodsClassify);
        }

        // 增加子分类
        List<String> subNames = JsonUtils.toList(command.getSubClassifyNames(), String.class);
        if (null != subNames && subNames.size() > 0) {
            for (String subName : subNames) {
                String classifyId = IDGenerator.get(IDGenerator.SCM_GOODS_CLASSIFY_PREFIX_TITLE);
                GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(classifyId);
                if (null != goodsClassify) {
                    throw new NegativeException(MCode.V_300, "商品分类ID已存在");
                }
                goodsClassify = new GoodsClassify(classifyId, subName, command.getParentClassifyId());
                goodsClassifyRepository.save(goodsClassify);
            }
        }
    }

    /**
     * 修改商品分类名称
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsClassifyName(GoodsClassifyModifyCommand command) throws NegativeException {
        LOGGER.info("modifyGoodsClassifyName command >>{}", command);
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(command.getClassifyId());
        if (null == goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类不存在");
        }
        goodsClassify.modifyClassifyName(command.getClassifyName());
    }

    /**
     * 删除商品分类
     *
     * @param classifyId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void deleteGoodsClassifyName(String classifyId) throws NegativeException {
        LOGGER.info("deleteGoodsClassifyName classifyId >>{}", classifyId);
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(classifyId);
        if (null == goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类不存在");
        }
        goodsClassify.deleteClassify();
    }
}
