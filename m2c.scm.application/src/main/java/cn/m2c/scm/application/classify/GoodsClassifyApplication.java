package cn.m2c.scm.application.classify;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.scm.application.classify.command.GoodsClassifyAddCommand;
import cn.m2c.scm.application.classify.command.GoodsClassifyModifyCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.classify.GoodsClassify;
import cn.m2c.scm.domain.model.classify.GoodsClassifyRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    GoodsRepository goodsRepository;

    /**
     * 添加商品分类
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsClassify(GoodsClassifyAddCommand command) throws NegativeException {
        LOGGER.info("addGoodsClassify command >>{}", command);
        // 与当前分类中的不能重名
        if (goodsClassifyRepository.goodsClassifyNameIsRepeat(null, command.getClassifyName())) {
            throw new NegativeException(MCode.V_301, "商品分类名称已存在");
        }
        // 父级分类id为-1，则增加一级分类
        String classifyId = IDGenerator.get(IDGenerator.SCM_GOODS_CLASSIFY_PREFIX_TITLE);
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(classifyId);
        if (null != goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类ID已存在");
        }
        goodsClassify = new GoodsClassify(classifyId, command.getClassifyName(), command.getParentClassifyId(), command.getLevel());
        goodsClassifyRepository.save(goodsClassify);

        // 增加子分类
        List<String> subNames = JsonUtils.toList(command.getSubClassifyNames(), String.class);
        if (null != subNames && subNames.size() > 0) {
            for (String subName : subNames) {
                // 与当前分类中的不能重名
                if (goodsClassifyRepository.goodsClassifyNameIsRepeat(null, subName)) {
                    throw new NegativeException(MCode.V_301, "商品分类名称已存在");
                }
                String subClassifyId = IDGenerator.get(IDGenerator.SCM_GOODS_CLASSIFY_PREFIX_TITLE);
                GoodsClassify subGoodsClassify = goodsClassifyRepository.getGoodsClassifyById(subClassifyId);
                if (null != subGoodsClassify) {
                    throw new NegativeException(MCode.V_300, "商品分类ID已存在");
                }
                goodsClassify = new GoodsClassify(subClassifyId, subName, classifyId, command.getLevel() + 1);
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
        // 与当前分类中的不能重名
        if (goodsClassifyRepository.goodsClassifyNameIsRepeat(command.getClassifyId(), command.getClassifyName())) {
            throw new NegativeException(MCode.V_301, "商品分类名称已存在");
        }
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(command.getClassifyId());
        if (null == goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类不存在");
        }
        goodsClassify.modifyClassifyName(command.getClassifyName());
    }

    /**
     * 修改商品分类服务费率
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsClassifyServiceRate(GoodsClassifyModifyCommand command) throws NegativeException {
        LOGGER.info("modifyGoodsClassifyServiceRate command >>{}", command);
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(command.getClassifyId());
        if (null == goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类不存在");
        }
        goodsClassify.modifyClassifyServiceRate(command.getServiceRate());
    }

    /**
     * 删除商品分类
     *
     * @param classifyId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void deleteGoodsClassify(String classifyId) throws NegativeException {
        LOGGER.info("deleteGoodsClassify classifyId >>{}", classifyId);
        // 判断是否有商品，有商品不能删除
        if (goodsRepository.classifyIdIsUser(goodsClassifyRepository.recursionQueryGoodsSubClassifyId(classifyId, new ArrayList<>()))) {
            throw new NegativeException(MCode.V_300, "商品分类下有商品，不能删除");
        }
        GoodsClassify goodsClassify = goodsClassifyRepository.getGoodsClassifyById(classifyId);
        if (null == goodsClassify) {
            throw new NegativeException(MCode.V_300, "商品分类不存在");
        }
        goodsClassify.deleteClassify();
    }
}
