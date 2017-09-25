package cn.m2c.scm.application.goods.goods.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Repository
public class SpringJdbcGoodsQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcGoodsQuery.class);
	private final static String goodsDescUri = "m2c.goods/goods/appGoodsDesc";
	
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	@Autowired
	SpringJdbcGuaranteeQuery query;
	
	String field = "d.goods_id goodsId,"+
	"d.goods_name goodsName,"+
	"d.dealer_id dealerId,"+
	"d.dealer_name dealerName,"+
	"d.recognized_id recognizedId,"+
	"d.recognized_url recognizedUrl,"+
	"d.recognized_id recognizedId,"+
	"d.goods_status goodsStatus,"+
	"d.order_num orderNum,"+
	"d.seller_num sellerNum,"+
	"d.collection_num collectionNum,"+
	"d.share_num shareNum,"+
	"d.first_classify firstClassify,"+
	"d.second_classify secondClassify,"+
	"d.dealer_name dealerName,"+
	"d.cooperation_mode cooperationMode,"+
	"d.divide_scale divideScale,"+
	"d.media_name mediaName,"+
	"d.mres_name mresName,"+
	"d.mres_num mresNum,"+
	"d.seller_name sellerName,"+
	"d.created_date createdDate";

	

	public List<Map<String, Object>> getlist(String goodsName,
			String dealerName, String firstClassify, String secondClassify,
			Integer goodsStatus, 
			Integer cooperationMode, String mediaId, String startTime,
			String endTime, Integer rows, Integer pageNum) {

		
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT " + field+" FROM t_goods_goods d WHERE d.goods_status<>2");
			if(goodsName!=null&&!"".equals(goodsName)){
				sql.append(" AND d.goods_name LIKE concat('%', ?,'%') ");
				params.add(goodsName);
			}
			if(dealerName!=null&&!"".equals(dealerName)){
				
				sql.append(" AND d.dealer_name LIKE concat('%', ?,'%')");
				params.add(dealerName);
			}
			if(firstClassify!=null&&!"".equals(firstClassify)){
				
				sql.append(" AND d.first_classify=?");
				params.add(firstClassify);
			}
			if(secondClassify!=null&&!"".equals(secondClassify)){
				
				sql.append(" AND d.second_classify=?");
				params.add(secondClassify);
			}
			if(goodsStatus!=null){
	
				sql.append(" AND d.goods_status=?");
				params.add(goodsStatus);
			}
			if(mediaId!=null&&!"".equals(mediaId)){
				
				sql.append(" AND d.media_id=?");
				params.add(mediaId);
			}
			if(cooperationMode!=null){
				
				sql.append(" AND d.cooperation_mode=?");
				params.add(cooperationMode);
			}
			if(startTime !=null&&!"".equals(startTime)){
				
				sql.append(" AND d.created_date>=?");
				params.add(startTime);
			}
			if(endTime!=null&&!"".equals(endTime)){
				
				sql.append(" AND d.created_date<=?");
				params.add(endTime+" 23:59:59");
			}
			sql.append(" ORDER BY d.created_date DESC ");
			sql.append(" LIMIT ?,?");
			params.add(rows*(pageNum - 1));
			params.add(rows);
			System.out.println("-----------------"+sql);
			List<Map<String,Object>> dealerLsit = jdbcTemplate.queryForList(sql.toString(),params.toArray());
			return dealerLsit;
		}catch(Exception e){
			LOGGER.error("查询商品列表出错");
			return null;
		}
	}


	/**
	 * 查询总数
	 * @param goodsName
	 * @param dealerName
	 * @param firstClassify
	 * @param secondClassify
	 * @param goodsStatus
	 * @param cooperationMode
	 * @param mediaId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getGoodsCount(String goodsName, String dealerName,
			String firstClassify, String secondClassify, Integer goodsStatus,
			Integer cooperationMode, String mediaId, String startTime,
			String endTime) {
		Long result = -1L;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT COUNT(*) totalCount FROM t_goods_goods d WHERE d.goods_status<>2");
		if(goodsName!=null&&!"".equals(goodsName)){
			sql.append(" AND d.goods_name LIKE concat('%', ?,'%') ");
			params.add(goodsName);
		}
		if(dealerName!=null&&!"".equals(dealerName)){
			
			sql.append(" AND d.dealer_name LIKE concat('%', ?,'%') ");
			params.add(dealerName);
		}
		if(firstClassify!=null&&!"".equals(firstClassify)){
			
			sql.append(" AND d.first_classify=?");
			params.add(firstClassify);
		}
		if(secondClassify!=null&&!"".equals(secondClassify)){
			
			sql.append(" AND d.second_classify=?");
			params.add(secondClassify);
		}
		if(goodsStatus!=null){

			sql.append(" AND d.goods_status=?");
			params.add(goodsStatus);
		}
		if(mediaId!=null&&!"".equals(mediaId)){
			
			sql.append(" AND d.media_id=?");
			params.add(mediaId);
		}
		if(cooperationMode!=null){
			
			sql.append(" AND d.cooperation_mode=?");
			params.add(cooperationMode);
		}
		if(startTime !=null&&!"".equals(startTime)){
			
			sql.append(" AND d.created_date>=?");
			params.add(startTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			
			sql.append(" AND d.created_date<=?");
			params.add(endTime+" 23:59:59");
		}
		
		Map<String, Object> countMap = jdbcTemplate.queryForMap(sql.toString(),params.toArray());
		result = (Long) countMap.get("totalCount");
		return result.intValue();
	}


	/**
	 * 根据商品id和propertyId拉去数据（dubbo服务）
	 * @param goodsId
	 * @param propertyId
	 * @return
	 */
	public Map<String,Object> getPropertyInfo(String goodsId, String propertyId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		Map<String,Object> result = null;
		sql.append("select d.dealer_name AS dealerName,d.dealer_id AS dealerId ,d.dealer_mobile AS dealerMobile,"
				+"p.property_id AS propertyId,g.goods_id goodsId,d.user_id AS userId,g.goods_no AS goodsNo,g.bar_no AS barNo,g.up_goods_date AS upGoodsDate,g.down_goods_date AS downGoodsDate,g.gallery AS gallery,"
				+"g.goods_name AS goodsName,g.sale_price AS salePrice,g.cooperation_mode AS cooperationMode,g.market_price AS marketPrice,tf.transport_fee AS transportFee,"
				+"p.property_value AS propertyValue  ,c.goods_classify_id AS classifyId,c.goods_classify_name AS classifyName"
				+" FROM t_goods_dealer d,t_goods_goods g,t_goods_transport_fee tf,t_goods_property p  ,t_goods_classify c"
				+" WHERE g.goods_status=3 AND d.dealer_id=g.dealer_id AND tf.transport_fee_id=g.transport_fee_id AND p.property_id=g.property_id AND c.goods_classify_id=g.second_classify ");
		if(goodsId!=null && !"".equals(goodsId)){
			sql.append(" AND g.goods_id=?");
			params.add(goodsId);
		}
		if(propertyId!=null && !"".equals(propertyId)){
			sql.append(" AND p.property_id=?");
			params.add(propertyId);
		}
		System.out.println("-----------------"+sql);
		try {
			 result = jdbcTemplate.queryForMap(sql.toString(),params.toArray());
			 if(result!=null){//取出第一张图片
					List<String> galleryList = null;
					for(String key:result.keySet()){
						if(key.equals("gallery")){
							galleryList = new Gson().fromJson((String) result.get("gallery"), new TypeToken<List<String>>(){}.getType());
							if(galleryList!=null){
								String pic = galleryList.get(0);
								result.put("gallery", pic);
							}else{
								result.put("gallery", "");
							}
						}
					}
				}
			 
		} catch (Exception e) {
			LOGGER.error("查询出错！",e);
		}
		return result;
	
	}

	/**
	 * 根据商品id检查商品是否存在
	 * @param goodsId
	 * @return
	 */

	public Map<String, Object> getGoodStatus(String goodsId) {
		Map<String,Object> result = null;
		
		String sql = "SELECT COUNT(*) AS goodsCount ,g.goods_status AS goodsStatus FROM t_goods_goods g WHERE 1=1 AND goods_id=?";
		try {
			result = jdbcTemplate.queryForMap(sql.toString(),goodsId);
		} catch (Exception e) {
			LOGGER.error("查询商品状态出错",e);
		}
		return result;
	}


	/**
	 * 根据商品id获取收藏商品的信息
	 * @param goodsId
	 * @return
	 */
	public Map<String, Object> getFavoriteGoods(String goodsId) {
		Map<String,Object> result = null;
		String sql = "SELECT g.gallery AS gallery ,g.goods_name AS goodsName ,g.sale_price AS goodsPrice,g.market_price AS marketPrice FROM t_goods_goods g WHERE 1=1 AND goods_id=?";
		try {
			result=jdbcTemplate.queryForMap(sql, goodsId);
			if(result!=null){
				List<String> galleryList = null;
				for(String key:result.keySet()){
					if(key.equals("gallery")){
						galleryList = new Gson().fromJson((String) result.get("gallery"), new TypeToken<List<String>>(){}.getType());
						if(galleryList!=null){
							String pic = galleryList.get(0);
							result.put("gallery", pic);
						}else{
							result.put("gallery", "");
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询商品状态出错",e);
		}
		return result;
	}

	/**
	 * app获取商品列表
	 * @param rows
	 * @param pageNum
	 * @return
	 */

	public List<Map<String, Object>> getappGoodslist(String goodsName,Integer rows,
			Integer pageNum) {
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> result = null;
		StringBuffer  sql =  new StringBuffer();
		sql.append("SELECT g.goods_id AS goodsId,g.dealer_id AS dealerId,g.dealer_name AS dealerName,g.goods_name AS goodsName ,g.subtitle AS subtitle ,g.sale_price AS salePrice ,g.market_price AS marketPrice,g.gallery AS gallery,g.goods_desc AS goodsDesc,g.media_id AS mediaId,g.goods_status AS goodsStatus");
		sql.append(" FROM  t_goods_goods g LEFT JOIN t_goods_brand b on g.brand_id = b.brand_id LEFT JOIN	t_goods_property p on	g.property_id = p.property_id left join t_goods_transport_fee tf on g.transport_fee_id = tf.transport_fee_id");
		sql.append(" WHERE g.goods_status=3 ");
		if(goodsName!=null && !"".equals(goodsName)){
			sql.append(" AND g.goods_name LIKE concat('%', ?,'%') ");
			params.add(goodsName);
		}
//		sql.append(" AND g.brand_id=b.brand_id AND g.property_id=p.property_id AND g.transport_fee_id = f.transport_fee_id");
		sql.append(" ORDER BY g.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows*(pageNum - 1));
		params.add(rows);
		System.out.println("-----------------"+sql);
		try {
		result = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<Map<String,Object>>(){
			@Override
			public Map<String,Object> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();				
				Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
				for (int i = 1; i <= columnCount; i++) {
					String key = JdbcUtils.lookupColumnName(rsmd, i);
					Object obj = JdbcUtils.getResultSetValue(rs, i);
					if(key.equals("gallery")){
						List<String> galleryList = null;
						try{
							galleryList = new Gson().fromJson((String) obj, new TypeToken<List<String>>(){}.getType());
						}catch(Exception e){
							e.printStackTrace();
						}
						if(galleryList!=null && galleryList.size()>0){
							String tt = galleryList.get(0);
							mapOfColValues.put(key, tt);
						}else{
							mapOfColValues.put(key, "");
						}
					}else{
						mapOfColValues.put(key, obj);
					}
				}
				return mapOfColValues;
			}
			
		});	
		
		} catch (Exception e) {
			LOGGER.error("查询商品状态出错",e);
		}
		return result;
	}


	/**
	 * 获取商品总数
	 * @return
	 */
	public Integer getGoodsCount(String goodsName) {
		Long result = -1L;
		Map<String, Object> countMap = null;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) totalCount FROM t_goods_goods g WHERE g.goods_status=3";
		if(goodsName!=null && !"".equals(goodsName)){
			sql+=" AND g.goods_name LIKE concat('%', ?,'%') ";
			params.add(goodsName);
			countMap = jdbcTemplate.queryForMap(sql.toString(),params.toString());
		}else{
			countMap = jdbcTemplate.queryForMap(sql.toString());
		}
		System.out.println("-------------"+sql);
		result = (Long) countMap.get("totalCount");
		return result.intValue();
	}


	/**
	 * 查询商品状态
	 * app根据商品id查询商品详情（查询所有状态）
	 * @param goodsId
	 * @return
	 */
	public Map<String, Object> getappGoodsDetail(String goodsId) {
		Map<String, Object> result = null;
		StringBuffer  sql =  new StringBuffer();
		sql.append("SELECT "); 
		sql.append("g.goods_id goodsId,");
		sql.append("g.gallery gallery,");
		sql.append("g.goods_name goodsName,");
		sql.append("g.media_id mediaId,");
		sql.append("g.media_name mediaName,");
		sql.append("g.mres_name mresName,");
		sql.append("g.subtitle subtitle,");
		sql.append("g.goods_status goodsStatus,");
		sql.append("g.sale_price salePrice,");
		sql.append("g.market_price marketPrice,");
		sql.append("g.brand_id brandId,");
		sql.append("g.property_id propertyId,");
		sql.append("tf.transport_fee transportFee,");
		sql.append("p.property_value propertyValue,");
		sql.append("g.guarantee guarantee,");
		sql.append("b.brand_pic brandPic,");
		sql.append("b.brand_name brandName,");
		sql.append("b.brand_desc brandDesc");
		sql.append(" FROM t_goods_goods g LEFT JOIN	t_goods_brand b on g.brand_id = b.brand_id LEFT JOIN	t_goods_property p on	g.property_id = p.property_id left join t_goods_transport_fee tf on g.transport_fee_id = tf.transport_fee_id WHERE  g.goods_id =?");
		System.out.println("----"+sql.toString());
		System.out.println("----goodsId:"+goodsId);
		try {
			result = jdbcTemplate.queryForMap(sql.toString(),goodsId);
			//处理app端json数据
			List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
			
				Map<String,Object> allProMap = new HashMap<String, Object>();
				if(result!=null){
					allProMap = new Gson().fromJson((String) result.get("propertyValue"), new TypeToken<Map<String,Object>>(){}.getType());
				
				if(allProMap!=null){
				for(String k:allProMap.keySet()){
		            System.out.println(k+":"+allProMap.get(k));
		            Map<String,Object> pro = new HashMap<String, Object>();
		            pro.put("paramKey", k);
		            String[] paramVal = allProMap.get(k).toString().split(",");
		            List<Map<String, Object>> arrList = new ArrayList<Map<String,Object>>();
	            	Map<String, Object> arrMap  = null;
		            if(paramVal.length>0){
		            	//循环
		            	for (int i = 0; i < paramVal.length; i++) {
		            		arrMap = new HashMap<String, Object>();
		            		arrMap.put("item", paramVal[i]);
		            		arrList.add(arrMap);
		            	}
		            	pro.put("paramVal", arrList);
		            }
		            paramList.add(pro);
		        }
				result.put("paramList", paramList);
				result.remove("propertyValue");
			}
		
			System.out.println(result.get("guarantee")+"=======");
			List<Map<String, Object>> guaranteeList = query.getGuaranteeList();
			List<Map<String, Object>> returnGurantee = new ArrayList<Map<String,Object>>();
				List<Integer> guarantees = new Gson().fromJson((String) result.get("guarantee"), new TypeToken<List<Integer>>(){}.getType());
				if(guarantees!=null){
					for (int i = 0; i < guarantees.size(); i++) {
						if(guarantees.get(i)==1){
							returnGurantee.add(guaranteeList.get(i));
						}
					}
					result.put("guaranteeList", returnGurantee);
					result.remove("guarantee");
				}
				System.out.println("-----------"+guarantees);
			
			//--------------------------解析图库
				List<String> gallerys = new Gson().fromJson((String) result.get("gallery"), new TypeToken<List<String>>(){}.getType());
				if(gallerys!=null){
					result.put("gallerys", gallerys);
					result.remove("gallery");
					}
				}
			result.put("mresId", "");
		} catch (Exception e) {
			LOGGER.error("查询详情失败",e);
		}
		return result;
	}


	/**
	 * 根据商品id查询商品详情
	 * @param goodsId
	 * @return
	 */
	public Map<String, Object> getGoodsDetail(String goodsId) {
		Map<String, Object> result = null;
		StringBuffer  sql =  new StringBuffer();
		sql.append("SELECT "); 
		sql.append("g.goods_id goodsId,");
		sql.append("g.dealer_id dealerId,");
		sql.append("g.dealer_name dealerName,");
		sql.append("g.first_classify firstClassify,");
		sql.append("g.second_classify secondClassify,");
		sql.append("g.goods_no goodsNo,");
		sql.append("g.bar_no barNo,");
		sql.append("g.goods_name goodsName,");
		sql.append("g.subtitle subtitle,");
		sql.append("g.sale_price salePrice,");
		sql.append("g.market_price marketPrice,");
		sql.append("g.brand_id brandId,");
		sql.append("g.property_id propertyId,");
		sql.append("g.transport_fee_id transportFeeId,");
		sql.append("g.guarantee guarantee,");
		sql.append("g.advertisement_pic advertisementPic,");
		sql.append("g.gallery gallery,");
		sql.append("g.goods_desc goodsDesc ");
		sql.append(" FROM t_goods_goods g WHERE g.goods_status<>2 AND g.goods_id=?");
		try {
			System.out.println("----------------"+sql.toString());
			result = jdbcTemplate.queryForMap(sql.toString(),goodsId);
		} catch (Exception e) {
			LOGGER.error("查询详情失败",e);
		}
		return result;
	}

	/**
	 * 根据识别图片id查询识别商品详情
	 * @param rids
	 * @return
	 */
	public List<Map<String, Object>> getrecognizedGoods(List<String> rids,final String barNo) {
		List<Map<String, Object>> result = null;
		StringBuffer  sql =  new StringBuffer();
		sql.append("SELECT "); 
		sql.append("g.goods_id goodsId,");
		sql.append("g.gallery gallery,");
		sql.append("g.goods_name goodsName,");
		sql.append("g.media_id mediaId,");
		sql.append("g.media_name mediaName,");
		sql.append("g.mres_name mresName,");
		sql.append("g.subtitle subtitle,");
		sql.append("g.sale_price salePrice,");
		sql.append("g.market_price marketPrice,");
		sql.append("g.brand_id brandId,");
		sql.append("g.property_id propertyId,");
		sql.append("p.property_value propertyValue,");
		sql.append("g.guarantee guarantee,");
		sql.append("b.brand_pic brandPic,");
		sql.append("b.brand_name brandName,");
		sql.append("b.brand_desc brandDesc,");
		sql.append("g.recognized_id recognizedId");
		sql.append(" FROM t_goods_goods g LEFT JOIN t_goods_brand b ON g.brand_id = b.brand_id Left Join t_goods_property p ON  g.property_id = p.property_id");
		sql.append(" WHERE g.goods_status=3 AND g.recognized_id in (");
			for (int i = 0; i < rids.size(); i++) {
				String recognizedId =rids.get(i);
				sql.append("'");
				sql.append(recognizedId);
				sql.append("'");
				if(i < (rids.size()-1)) {
					sql.append(",");
			    }
			}
			sql.append(")");
			
			
		sql.append(" order by field(recognizedId,");	
		for (int i = 0; i < rids.size(); i++) {
			String recognizedId =rids.get(i);
			sql.append("'");
			sql.append(recognizedId);
			sql.append("'");
			if(i < (rids.size()-1)) {
				sql.append(",");
		    }
		}
		sql.append(")");
		
		
		System.out.println("----"+sql.toString());
		try {
			result = jdbcTemplate.query(sql.toString(), new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();				
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);			
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
						if(key.equals("propertyValue")){
						//解析属性数据
						List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
						if(obj!=null && !"".equals(obj)){
							Map<String,Object> allProMap = new Gson().fromJson((String) obj, new TypeToken<Map<String,Object>>(){}.getType());
							for(String k:allProMap.keySet()){
					            System.out.println(k+":"+allProMap.get(k));
					            Map<String,Object> pro = new HashMap<String, Object>();
					            pro.put("paramKey", k);
					            String[] paramVal = allProMap.get(k).toString().split(",");
					            List<Map<String, Object>> arrList = new ArrayList<Map<String,Object>>();
				            	Map<String, Object> arrMap  = null;
					            if(paramVal.length>0){
					            	//循环
					            	for (int j = 0; j < paramVal.length; j++) {
					            		arrMap = new HashMap<String, Object>();
					            		arrMap.put("item", paramVal[j]);
					            		arrList.add(arrMap);
					            	}
					            	pro.put("paramVal", arrList);
					            }
					            paramList.add(pro);
					        }
						}
						mapOfColValues.put("paramList", paramList);
						mapOfColValues.remove("propertyValue");
					}
					if(key.equals("guarantee")){
							List<Map<String, Object>> guaranteeList = query.getGuaranteeList();
							List<Map<String, Object>> returnGurantee = new ArrayList<Map<String,Object>>();
							if(obj!=null && !"".equals(obj)){
							List<Integer> guarantees = new Gson().fromJson((String) obj, new TypeToken<List<Integer>>(){}.getType());
							if(guarantees!=null){
								for (int k = 0; k < guarantees.size(); k++) {
									if(guarantees.get(k)==1){
										returnGurantee.add(guaranteeList.get(k));
								}
								}
							}
						mapOfColValues.put("guaranteeList", returnGurantee);
						mapOfColValues.remove("guarantee");
						}
					}
					
					if(key.equals("gallery")){
						List<String> gallerys = new Gson().fromJson((String) obj, new TypeToken<List<String>>(){}.getType());
						if(gallerys!=null){
							mapOfColValues.put("gallerys", gallerys);
							mapOfColValues.remove("gallery");
						}
					}
						
				}
					
					String appGoodsDesc = goodsDescUri+"?goodsId="+mapOfColValues.get("goodsId");
					System.out.println("------------"+appGoodsDesc);
					mapOfColValues.put("appGoodsDesc", appGoodsDesc);
					
					mapOfColValues.put("mresId", barNo);
					return mapOfColValues;
				}
				
			});	
			
			} catch (Exception e) {
				LOGGER.error("查询商品状态出错",e);
			}
		return result;
	}

	/**
	 * 根据经纬度查询商品信息
	 * @param goodsIdList
	 * @return
	 */

	public Map<String, Object> getRecognizedlocationGoods(
			String goodsId) {
		List<Map<String, Object>> result = null;
		StringBuffer  sql =  new StringBuffer();
		sql.append("SELECT "); 
		sql.append("g.goods_id goodsId,");
		sql.append("g.gallery gallery,");
		sql.append("g.goods_name goodsName,");
		sql.append("g.media_id mediaId,");
		sql.append("g.media_name mediaName,");
		sql.append("g.mres_name mresName,");
		sql.append("g.subtitle subtitle,");
		sql.append("g.sale_price salePrice,");
		sql.append("g.market_price marketPrice,");
		sql.append("g.brand_id brandId,");
		sql.append("g.property_id propertyId,");
		sql.append("p.property_value propertyValue,");
		sql.append("g.guarantee guarantee,");
		sql.append("b.brand_pic brandPic,");
		sql.append("b.brand_name brandName,");
		sql.append("b.brand_desc brandDesc,");
		sql.append("g.mres_id mresId,");
		sql.append("g.recognized_id recognizedId");
		sql.append(" FROM t_goods_goods g LEFT JOIN t_goods_brand b ON g.brand_id = b.brand_id Left Join t_goods_property p ON  g.property_id = p.property_id");
		sql.append(" WHERE g.goods_status=3 AND  g.goods_id =?");
		System.out.println("---------------"+sql.toString());
		try {
			result = jdbcTemplate.query(sql.toString(), new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();				
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
						if(key.equals("propertyValue")){
							//解析属性数据
						List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
						Map<String,Object> allProMap = new Gson().fromJson((String) obj, new TypeToken<Map<String,Object>>(){}.getType());
						for(String k:allProMap.keySet()){
				            System.out.println(k+":"+allProMap.get(k));
				            Map<String,Object> pro = new HashMap<String, Object>();
				            pro.put("paramKey", k);
				            String[] paramVal = allProMap.get(k).toString().split(",");
				            List<Map<String, Object>> arrList = new ArrayList<Map<String,Object>>();
			            	Map<String, Object> arrMap  = null;
				            if(paramVal.length>0){
				            	//循环
				            	for (int j = 0; j < paramVal.length; j++) {
				            		arrMap = new HashMap<String, Object>();
				            		arrMap.put("item", paramVal[j]);
				            		arrList.add(arrMap);
				            	}
				            	pro.put("paramVal", arrList);
				            }
				            paramList.add(pro);
				        }
						mapOfColValues.put("paramList", paramList);
						mapOfColValues.remove("propertyValue");
					}
						
						if(key.equals("guarantee")){
							List<Map<String, Object>> guaranteeList = query.getGuaranteeList();
							List<Map<String, Object>> returnGurantee = new ArrayList<Map<String,Object>>();
							List<Integer> guarantees = new Gson().fromJson((String) obj, new TypeToken<List<Integer>>(){}.getType());
							if(guarantees!=null){
								for (int k = 0; k < guarantees.size(); k++) {
									if(guarantees.get(k)==1){
										returnGurantee.add(guaranteeList.get(k));
								}
							}
								
								
								String appGoodsDesc = goodsDescUri+"?goodsId="+mapOfColValues.get("goodsId");
								System.out.println("------------"+appGoodsDesc);
								mapOfColValues.put("appGoodsDesc", appGoodsDesc);
						mapOfColValues.put("guaranteeList", returnGurantee);
						mapOfColValues.remove("guarantee");
						}
						System.out.println("-----------"+guarantees);
					}
						
						

						if(key.equals("gallery")){
							List<String> gallerys = new Gson().fromJson((String) obj, new TypeToken<List<String>>(){}.getType());
							if(gallerys!=null){
								mapOfColValues.put("gallerys", gallerys);
								mapOfColValues.remove("gallery");
							}
						}
						
					}
					return mapOfColValues;
				}
				
			});	
		} catch (Exception e) {
				LOGGER.error("查询商品状态出错",e);
		}
		return result==null?null:result.get(0);
	}


	/**
	 * 删除之前根据经销商id查询是否经销商下还有未下架的商品
	 * @param dealerId
	 * @return
	 */
	public Integer checkDealerGoods(String dealerId) throws Exception{
		String sql = "SELECT COUNT(*) goodsCount FROM t_goods_goods WHERE goods_status<>2 AND dealer_id=?";
		Integer	num = jdbcTemplate.queryForObject(sql, Integer.class,dealerId);
		return num;
	}


	/**
	 * 根据商品id获取商品详情
	 * @param goodsId
	 * @return
	 */
	public Map<String, Object> getGoodsInfo(String goodsId) {
		Map<String, Object> result = null;
		String sql = "SELECT g.goods_id AS goodsId,"
				+ "g.goods_name AS goodsName,"
				+ "g.second_classify AS secondClassify,"
				+ "c.goods_classify_name AS goodsClassifyName,"
				+ "g.goods_status AS goodsStatus,"
				+ "g.up_goods_date AS upGoodsDate1,"
				+ "g.goods_no AS goodsNo,"
				+ "g.down_goods_date AS downGoodsDate1"
				+ " FROM t_goods_goods g LEFT JOIN t_goods_classify c"
				+ "  on g.second_classify=c.goods_classify_id where g.goods_status<>2 and g.goods_id=?";
		try {
			result = jdbcTemplate.queryForMap(sql,goodsId);
			if(result!=null){
				Timestamp  upGoodsDate = (Timestamp) result.get("upGoodsDate1");
				result.put("upGoodsDate", upGoodsDate!=null?upGoodsDate.getTime():null);
				Timestamp  downGoodsDate = (Timestamp) result.get("downGoodsDate1");
				result.put("downGoodsDate", downGoodsDate!=null?downGoodsDate.getTime():null);
			}
		} catch (Exception e) {
			LOGGER.error("根据id查询商品信息出错",e);
		}
		return result;
	}


	/**
	 * 商品详情
	 * @param goodsId
	 * @return
	 */
	public Map<String, Object> getappGoodsDesc(String goodsId) {
		Map<String, Object> result = null;
		String sql = "SELECT goods_desc goodsDesc FROM t_goods_goods g WHERE g.goods_id=?";
		try {
			System.out.println("-----------"+sql);
			result = jdbcTemplate.queryForMap(sql,goodsId);
		} catch (Exception e) {
			LOGGER.error("根据id查询商品详情");
		}
		return result;
	}


	/**
	 * 
	 * @return
	 */
	public Map<String, Object> unBindAd() {
		Map<String, Object> result = null;
		String sql = "SELECT COUNT(*) unBindCount FROM t_goods_goods g WHERE  g.goods_status<>2 AND g.mres_id is NULL";
		try {
			System.out.println("-----------"+sql);
			result = jdbcTemplate.queryForMap(sql);
		} catch (Exception e) {
			LOGGER.error("查商品绑定媒体数出错",e);
		}
		return result;
	}


	/**
	 * 获取识别图片
	 * @return
	 */
	public List<Map<String, Object>> getRecognizedPic() {
		List<Map<String, Object>> result = null;
		try {
			String sql = "SELECT g.goods_id AS  goodsId,"
					+ "g.recognized_id AS recognizedId, "    
					+ "g.recognized_url AS recognizedUrl "
					+ " FROM t_goods_goods g WHERE g.goods_status <> 2 ";
			
			result = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("获取识别图片失败");
		}
		return result;
	}
	
	
	
}