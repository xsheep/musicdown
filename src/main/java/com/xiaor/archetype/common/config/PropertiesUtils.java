package com.xiaor.archetype.common.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @ClassName PropertiesUtils
 * @Description 配置文件读取工具类
 * @author XiaoRong
 * @Date 2017-9-22 下午3:23:33
 * @version 1.0.0
 */
public class PropertiesUtils extends PreferencesPlaceholderConfigurer{
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	/**
	 * @Description 读取项目内部配置文件
	 * @author XiaoRong
	 * @Date 2017-9-22 下午3:23:47
	 * @param location
	 * @return
	 */
	public Properties loadConfig(String location){
		try {
			if(StringUtils.isBlank(location)){
				throw new IllegalArgumentException("location is null");
			}
			Properties prop = loadFilePathConfig(location);
			if(prop != null){
				return prop;
			}else{
				Resource resource = new ClassPathResource(location);
				setLocation(resource);
				setFileEncoding("UTF-8");
				return mergeProperties();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @Description 读取外部配置文件
	 * @author XiaoRong
	 * @Date 2017-9-22 下午3:24:36
	 * @param location
	 * @return
	 */
	public Properties loadFilePathConfig(String location){
		try {
			if(StringUtils.isBlank(location)){
				throw new IllegalArgumentException("location is null");
			}
			Resource resource = new FileSystemResource(location);
			setLocation(resource);
			setFileEncoding("UTF-8");
			return mergeProperties();
		} catch (Exception e) {
		}
		return null;
	}
	
    private static class HolderSingletonHolder {
        static PropertiesUtils instance = new PropertiesUtils();
    }

    public static PropertiesUtils getInstance() {
        return HolderSingletonHolder.instance;
    }
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtils.getInstance().loadConfig("db.properties"));
		System.out.println(PropertiesUtils.getInstance().loadConfig("db.properties"));
	}
}
