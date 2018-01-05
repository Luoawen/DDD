package cn.m2c.scm.port.adapter.persistence.hibernate.config;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.config.Config;
import cn.m2c.scm.domain.model.config.ConfigRepository;

@Repository
public class HibernateConfigRepository  extends HibernateSupperRepository implements ConfigRepository{

	@Override
	public Config queryConfigByKey(String configKey) {
		StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_config WHERE config_key =:config_key");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Config.class);
		query.setParameter("config_key", configKey);
		return (Config) query.uniqueResult();
	}

	@Override
	public void save(Config config) {
		this.session().save(config);
	}

}
