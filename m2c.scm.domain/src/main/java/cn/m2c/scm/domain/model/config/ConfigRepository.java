package cn.m2c.scm.domain.model.config;

/**
 * 配置 
 */
public interface ConfigRepository {

	/**
	 * 根据configKey查询配置
	 * @param configKey
	 * @return
	 */
	Config queryConfigByKey(String configKey);

	/**
	 * 保存
	 * @param config
	 */
	void save(Config config);

}
