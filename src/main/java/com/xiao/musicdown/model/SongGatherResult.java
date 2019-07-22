package com.xiao.musicdown.model;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.xiao.musicdown.constants.Constants;

/**
 * @ClassName SongGatherResult
 * @Description 歌曲采集结果Bean
 * @author XiaoRong
 * @Date 2019年7月3日 下午2:19:39
 * @version 1.0.0
 */
public class SongGatherResult {

	private String name;
	
	private String url;
	
	private String savePath;
	
	private String suffix;
	
	/**
	 * @Field @isSuccess : 是否采集成功
	 */
	private boolean isSuccess;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSavePath() {
		if (StringUtils.isNotBlank(name)) {
			if (StringUtils.isNotBlank(savePath)) {
				return savePath.concat(File.separator).concat(name).concat(".").concat(suffix);
			} else {
				return Constants.Music163.SONG_SAVE_PATH.concat(File.separator).concat(name).concat(".").concat(suffix);
			}
		}
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
