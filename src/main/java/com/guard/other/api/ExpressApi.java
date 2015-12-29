package com.guard.other.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.guard.other.apiVO.ExpressVO.Express;
import com.guard.other.apiVO.ExpressVO.ExpressResult;
import com.guard.other.apiVO.ExpressVO.LogisticsDetail;

import net.sf.json.JSONObject;

/**
 * 聚合数据平台快递单号查询API
 * @author guard
 * @version 2015年12月29日16:31:21
 */
public class ExpressApi {
	private static Map<String, String> params = new HashMap<String, String>();

	static {
		params.put("顺丰", "sf");
		params.put("申通", "sto");
		params.put("圆通", "yt");
		params.put("韵达", "yd");
		params.put("天天", "tt");
		params.put("EMS", "ems");
		params.put("中通", "zto");
		params.put("汇通", "ht");
	}

	public static String getExpressInformation(String text) throws Exception {
		String com = "";// 快递公司
		String no = "";// 快递单号

		com = getCompany(text);
		no = getNum(text);
		if("".equals(com)){
			return "查询信息中不含快递公司信息！";
		}
		if("".equals(no)){
			return "查询信息中不含快递单号信息！";
		}
		String apiUrl = "http://v.juhe.cn/exp/index?key=ed04ec1629dbdbff1bbe651fdd82b683&com=" + com + "&no=" + no;
		//创建HTTP客户端发送的对象
		URL getUrl = new URL(apiUrl);
		
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.connect();
		
		//取得输入流，并使用Reader读取
		InputStream in = connection.getInputStream();
		InputStreamReader inR = new InputStreamReader(in,"utf-8");
		BufferedReader reader = new BufferedReader(inR);
		//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		in.close();
		inR.close();
		reader.close();
		
		//断开连接
		connection.disconnect();
		System.out.println(sb.toString());
		return jsonStrTo(sb.toString());
	}
	public static String getCompany(String str){
    	for(Entry<String, String> entry :params.entrySet()){
    		String key = entry.getKey();
    		if(str.contains(key)){
    		    return entry.getValue();
    		}
    	}
    	return "";
    }

	public static String getNum(String str) {
		String num = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c)) {
				num += c;
			}
		}
		return num;
	}
	private static String jsonStrTo(String jsonStr){
		System.out.println(jsonStr);
		System.out.println("-----------------------------");
		
    	JSONObject jsonObj = JSONObject.fromObject(jsonStr);
    	Map<String,Class> classMap = new HashMap<String, Class>();
    	classMap.put("list", LogisticsDetail.class);
    	classMap.put("result", ExpressResult.class);
    	
    	Express express = (Express) JSONObject.toBean(jsonObj, Express.class, classMap);
		StringBuffer result = new StringBuffer();
		if("200".equals(express.getResultcode())){
			result.append(express.getReason());
			result.append("\n");
			
			ExpressResult expressResult = express.getResult();
			if(expressResult != null){
				result.append("所属快递公司："+expressResult.getCompany());
				result.append("\n");
				result.append("快递单号："+expressResult.getNo());
				result.append("\n");
				
				List<LogisticsDetail> logisticsDetailLists = expressResult.getList();
				for(int i=0;i<logisticsDetailLists.size();i++){
					LogisticsDetail logisticsDetail = logisticsDetailLists.get(i);
					result.append(logisticsDetail.getDatetime());
					result.append("的时候：");
					result.append(logisticsDetail.getRemark());
					result.append("   所在地：");
					result.append(logisticsDetail.getZone());
					result.append("\n");
				}
			}
			return result.toString();
		}else{
			return "您所查询的快递信息不存在，请核对快递单号和快递公司！";
		}
	}
}
