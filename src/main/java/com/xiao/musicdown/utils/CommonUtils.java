package com.xiao.musicdown.utils;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static boolean saveFile(String srcUrl, String savePath) {
		try {
			File saveFile = new File(savePath);
			if (saveFile.exists()) {
				logger.info("文件：【{}】已存在，无需重复下载", savePath);
				return true;
			}
			URL downUrl = new URL(srcUrl);
			FileUtils.copyURLToFile(downUrl, saveFile);
			return true;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	public static boolean endsWith(String srcValue, List<String> suffixList, StringBuilder suffixStr) {
		if (StringUtils.isNotBlank(srcValue)) {
			for (String suffix : suffixList) {
				if (srcValue.endsWith(suffix)) {
					suffixStr.append(suffix);
					return true;
				}
			}
		}
		return false;
	}
}
