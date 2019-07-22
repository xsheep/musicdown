package com.xiao.musicdown.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName JSONReader
 * @Description JSON读取操作
 * @author XiaoRong
 * @Date 2017-11-14 下午1:50:24
 * @version 1.0.0
 */
public class JSONReader {

	/**
	 * @Description 根据路径获取json中的对象
	 * @author XiaoRong
	 * @Date 2017-11-14 下午1:50:35
	 * @param inputJson
	 * @param path:root/test
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(JSONObject inputJson, String path) throws Exception {
		if (inputJson == null || inputJson.isEmpty()) {
			throw new Exception("this json is null");
		}
		if (StringUtils.isBlank(path)) {
			throw new Exception("this json key is null");
		}
		String[] paths = path.split("/");
		Object jsonObject = inputJson;
		for (int i = 0, len = paths.length; i < len; i++) {
			String key = paths[i];
			if (StringUtils.isBlank(key)) {
				continue;
			}
			if (jsonObject == null) {
				return null;
			}
			if (jsonObject instanceof JSONObject) {
				jsonObject = ((JSONObject)jsonObject).get(key);
			} else if (jsonObject instanceof JSONArray) {
				int idx = 0;
				if (key.endsWith("]") && key.contains("[")) {
					String idxVal = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
					if (StringUtils.isNotBlank(idxVal)) {
						idx = Integer.parseInt(idxVal);
					}
					key = key.substring(0, key.indexOf("["));
				}
				jsonObject = ((JSONArray)jsonObject).getJSONObject(idx).get(key);
				break;
			} else {
				jsonObject = null;
				break;
			}
		}
		return jsonObject;
	}
	
	/**
	 * @Description 获取JSONArray对象，如果对象为JSONObject，那么返回一个size=1的JSONArray
	 * @author XiaoRong
	 * @Date 2017-11-14 下午6:20:37
	 * @param jsonObject
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String path) throws Exception {
		Object object = getObject(jsonObject,path);
		if(object != null){
			if (object instanceof JSONArray) {
				return (JSONArray)object;
			} else if (object instanceof JSONObject) {
				//如果是JSONObject类型，则创建一个size=1的JSONArray
				JSONArray jsonArray = new JSONArray();
				jsonArray.add((JSONObject) object);
				return jsonArray;
			}
		}
		return null;
	}
	
	/**
	 * @Description 获取指定路径下的String对象
	 * @author XiaoRong
	 * @Date 2017-11-14 下午6:21:03
	 * @param jsonObj
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getString(JSONObject jsonObj, String path) throws Exception {
		String result = String.valueOf(getObject(jsonObj, path));
		if ("null".equals(result)) {
			return "";
		}
		return result;
	}
	
	/**
	 * @Description 获取指定路径下的Integer对象
	 * @author XiaoRong
	 * @Date 2017-11-14 下午6:21:28
	 * @param jsonObj
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Integer getInt(JSONObject jsonObj, String path) throws Exception {
		String result = String.valueOf(getObject(jsonObj, path));
		if ("null".equals(result)) {
			return null;
		}
		if (StringUtils.isNumeric(result)) {
			return Integer.parseInt(result);
		}
		return null;
	}
}
