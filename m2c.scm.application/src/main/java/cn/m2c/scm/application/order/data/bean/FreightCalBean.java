package cn.m2c.scm.application.order.data.bean;

import cn.m2c.scm.application.order.query.dto.GoodsDto;

/***
 * 运费辅助计算实体
 * @author fanjc
 * created date 2017年11月15日
 * copyrighted@m2c
 */
public class FreightCalBean {
	
	private GoodsDto bean;
	
	private int nums;
	
	private float weight;

	public GoodsDto getBean() {
		return bean;
	}

	public void setBean(GoodsDto bean) {
		this.bean = bean;
	}

	public int getNums() {
		return nums;
	}

	public void setNums(int nums) {
		this.nums = nums;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}	
}
