package cn.m2c.scm.domain.model.expressPlatform;

public interface ExpressPlatformRepository {

	ExpressPlatform getExpressPlatform(String com, String nu);

	void saveOrUpdate(ExpressPlatform ep);

}
