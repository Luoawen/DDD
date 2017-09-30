package cn.m2c.scm.domain.model.postage;

/**
 * 运费模板仓库
 */
public interface PostageModelRepository {

    /**
     * 获取运费模板
     * @param modelId
     * @return
     */
    public PostageModel getPostageModel(String modelId);

    /**
     * 保存运费模板
     * @param postageModel
     */
    public void save(PostageModel postageModel);

}
