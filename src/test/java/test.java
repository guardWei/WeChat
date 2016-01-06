import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {
	@Test
	public void getSystemEncoding() {
		// 获取系统默认编码
		System.out.println(System.getProperty("file.encoding"));
		// 获取系统默认语言
		System.out.println(System.getProperty("user.language"));
		// 获取系统属性列表
		System.getProperties().list(System.out);
		// 获取系统默认的字符编码
		System.out.println(Charset.defaultCharset());
		//操作系统构架
		System.out.println(System.getProperty("os.arch"));
		// 设置编码
		// System.getProperties().put("file.encoding", "GBK");
	}
	@Test
	public void testFace() throws Exception{
		 String apiKey = "75cfac59471542897ebd927043c0ac70";
		String apiSecret = "DX2aLJRM5vbNstSeQaz1XA_EwFfg5uyI";
		 String imageUrl = "http://pica.nipic.com/2007-12-16/20071216105113620_2.jpg";
		String apiUrl = "http://apicn.faceplusplus.com/v2/detection/detect?api_key=" + apiKey + "&api_secret="
				+ apiSecret + "&url=" + imageUrl;
		
		//创建HTTP客户端发送的对象
		URL getUrl = new URL(apiUrl);
		
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.connect();
		
		//取得输入流，并使用Reader读取
		InputStream in = connection.getInputStream();
		InputStreamReader inR = new InputStreamReader(in, "utf-8");
		BufferedReader reader = new BufferedReader(inR);
		
		StringBuffer sb = new StringBuffer();
        String line = "";
        while((line = reader.readLine())!=null){
        	sb.append(line);
        }
		
        //断开输入流
        in.close();
        inR.close();
        reader.close();
        
        //断开连接
        connection.disconnect();
        System.out.println(sb.toString());
        
        JSONObject jsonObj = JSONObject.fromObject(sb.toString());
        
        JSONArray jsonArray = jsonObj.getJSONArray("face");
        
        StringBuffer result = new StringBuffer();
        result.append("共检测出人脸数："+jsonArray.size()+"\n");
        for(int i=0;i<jsonArray.size();i++){
        	JSONObject faceObj = (JSONObject) jsonArray.get(i);
        	JSONObject attributeObj = faceObj.getJSONObject("attribute");
        	
        	JSONObject genderObj = attributeObj.getJSONObject("gender");
        	JSONObject ageObj = attributeObj.getJSONObject("age");
        	JSONObject raceObj = attributeObj.getJSONObject("race");
        	JSONObject smilingObj = attributeObj.getJSONObject("smiling");
        	
        	result.append("第"+(i+1)+"个检测结果如下：\n");
        	result.append("性别："+(genderObj.getString("value").equals("Female") ? "女":"男")+"(置信度："+genderObj.getString("confidence")+"%)\n");
        	result.append("年龄："+ageObj.getString("value")+"岁 (误差：±"+ageObj.getString("range")+"岁)\n");
        	result.append("种族："+ (raceObj.getString("value").equals("Asian") ? "黄种人" :raceObj.getString("value").equals("White") ? "白种人":"黑种人") +"(置信度："+genderObj.getString("confidence")+"%)\n");
        	result.append("微笑程度："+smilingObj.getString("value")+"%\n");
        	result.append("\n");
        }
        System.out.println(result.toString());
	}
}
