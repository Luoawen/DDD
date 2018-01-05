package cn.m2c.scm.domain.model.config;

/**
 * 配置 
 */
public interface ConfigRepository {

	Config queryConfigByKey(String configKey);

	void save(Config config);

}
