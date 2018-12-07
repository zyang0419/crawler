package com.jin.crawler.util;

import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MonitorNameKeyUtils {
	
	
	public static void getSpiderData() throws Exception{
		String html="{\n" +
				"\"code\": \"200\",\n" +
				"\"msg\": \"成功\",\n" +
				"\"data\": [\n" +
				"{\n" +
				"\"excludeWords\": [\n" +
				"\"\"\n" +
				"],\n" +
				"\"keyWords\": [\n" +
				"\"圆通\",\n" +
				"\"圆通速递\",\n" +
				"\"圆通快递\"\n" +
				"],\n" +
				"\"name\": \"圆通速递\",\n" +
				"\"id\": \"project_39e3c3aff5ac45edae32359c4ae83f6c\"\n" +
				"},\n" +
				"{\n" +
				"\"excludeWords\": [\n" +
				"\"\"\n" +
				"],\n" +
				"\"keyWords\": [\n" +
				"\"众华会计\",\n" +
				"\"众华会计师\",\n" +
				"\"众华会计师事务所\"\n" +
				"],\n" +
				"\"name\": \"众华\",\n" +
				"\"id\": \"project_11313ab42f864a3086afd55ed006e704\"\n" +
				"},\n" +
				"{\n" +
				"\"excludeWords\": [\n" +
				"\"\"\n" +
				"],\n" +
				"\"keyWords\": [\n" +
				"\"三湘印象\",\n" +
				"\"观印象\",\n" +
				"\"罗筱溪\"\n" +
				"],\n" +
				"\"name\": \"三湘印象\",\n" +
				"\"id\": \"project_7658457ed13e44548e231c35ad6bf5c3\"\n" +
				"}\n" +
				"],\n" +
				"\"object\": null,\n" +
				"\"data3\": null,\n" +
				"\"data4\": null,\n" +
				"\"pageNum\": 0,\n" +
				"\"pageSize\": 0,\n" +
				"\"count\": 0,\n" +
				"\"type\": null,\n" +
				"\"key\": null,\n" +
				"\"url\": null\n" +
				"}";
		JSONObject jsonObject = JSONObject.fromObject(html);
		Object dataObject = jsonObject.get("data");
		if (null!=dataObject||""!=dataObject){
			JSONArray jsonArray = JSONArray.parseArray(dataObject.toString());
//			System.out.println(jsonArray.size());
//			List<String> names = new ArrayList<String>();
			if (!jsonArray.isEmpty()){
				for(Object obj:jsonArray){
					JSONObject jsonO = JSONObject.fromObject(obj);
					Object name = jsonO.get("name");
					Object id = jsonO.get("id");
//					names.add(name.toString());
					Object jsoObject = jsonO.get("keyWords");
					JSONArray jsonArray2 = JSONArray.parseArray(jsoObject.toString());
					System.out.println(""+ jsonArray2);
					for (Object obj2:jsonArray2) {
						System.out.println(obj2);
					}
				}
			}	
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		getSpiderData();
	}

}
