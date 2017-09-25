package cn.m2c.scm.port.adapter.restful.order.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ExpressUtil {
	
	 public HttpURLConnection getPostHttpConn(String url) throws Exception {
        URL object = new URL(url);
        HttpURLConnection conn;
        conn = (HttpURLConnection) object.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        return conn;
	}
	 
	public LogisticsRet logistics(String waybillNo,String expCompanyCode){
		try {
			String url = "http://api.kuaidi100.com/api?id=f2b9f516cd3f103f"
					    + "&com=" + expCompanyCode
					    + "&nu=" + waybillNo 
					    + "&show=0&muti=1&order=desc";
			System.out.println(url);
			HttpURLConnection conn =  getPostHttpConn(url);
			StringBuilder sb = new StringBuilder();
	        int httpRspCode = conn.getResponseCode();
	        if (httpRspCode == HttpURLConnection.HTTP_OK) {
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                sb.append(line);
	            }
	            br.close();
	           LogisticsRet ret = new Gson().fromJson(sb.toString(), new TypeToken<LogisticsRet>(){}.getType());
	           return ret;
	        }
		} catch (Exception e) {
		
		}
		return null;
	}
	
	
	public static void main(String[] args){
		new ExpressUtil().logistics("667746435865", "tiantian");
	}

}
