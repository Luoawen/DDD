package cn.m2c.scm.application.shop.data.bean;

import java.util.Calendar;
import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShopCreatedDateBean {
	
	@ColumnAlias(value = "dealer_id")
	private String dealerId;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	
	private Integer dayTimes;

	public String getDealerId() {
		return dealerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Integer getDayTimes() {
		this.dayTimes = createdDays(this.createdDate,new Date());
		return this.dayTimes;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setDayTimes(Integer dayTimes) {
		this.dayTimes = dayTimes;
	}
	
	public Integer createdDays(Date createdDate,Date thisDate) {
		Calendar cal1 = Calendar.getInstance();
        cal1.setTime(createdDate);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(thisDate);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //不同年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //同一年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
	}
	
	
	
	

}
