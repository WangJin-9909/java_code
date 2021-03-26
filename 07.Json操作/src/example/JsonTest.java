package example;

import java.util.Random;

public class JsonTest {
	
	public static void main(String[] args) {
		//解析Json数组
		//------------------------------------------
		//Object转Josn
//		params = JSONObject.toJSONString(jsonObject);
		//String转JSON
//		SensorBean sensor = JSONObject.parseObject(motion,
//				SensorBean.class);
		
		
		
		Random random = new Random();
		for(int i  = 0; i < 100; i++)
			System.out.println(random.nextInt(3));
	}

}
