package com.xiaor.archetype.common.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	private Properties properties = null;

	public Configuration(String configureFile){
		loadProperties(configureFile);
	}
	
	public void loadProperties(String configureFile){
		try {
			this.properties = PropertiesUtils.getInstance().loadConfig(configureFile);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public String getConfigProperty(String key) {
		String result = properties.getProperty(key);
		if (result == null)
			result = "";
		return result;
	}
	
	public Map<String, String> getConfigMap(String prefix) {
		Map<String, String> tmp = new HashMap<String, String>();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = entry.getKey().toString().trim();
			if (!key.startsWith(prefix.concat("."))) {
				continue;
			}
			key = key.substring(prefix.length() + 1);
			tmp.put(key, (String) entry.getValue());
		}
		return tmp;
	}

	public Map<String, String> mergeConfig(Map<String, String> destMap, Map<String, String> srcMap) {
		for (Entry<String, String> entry : srcMap.entrySet()) {
			if (!destMap.containsKey(entry.getKey())) {
				destMap.put(entry.getKey(), entry.getValue());
			}
		}
		return destMap;
	}
	
	public String getProperty(String key, String defaultValue){
		return properties.getProperty(key, defaultValue);
	}

	public int getIntProperty(String key, int defaultValue) {
		return Integer.parseInt(properties.getProperty(key, Integer.toString(defaultValue)));
	}
	
	public double getDoubleProperty(String key, double defaultValue) {
		return Double.parseDouble(properties.getProperty(key, Double.toString(defaultValue)));
	}
	
	public boolean getBooleanProperty(String key){
		String result = properties.getProperty(key);
		if(StringUtils.isBlank(result)){
			return false;
		}
		return Boolean.parseBoolean(result);
	}
}
