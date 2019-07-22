package com.xiao.musicdown.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hui.common.config.Configuration;

/**
 * @ClassName Constants
 * @Description 常量类
 * @author XiaoRong
 * @Date 2018年11月12日 下午5:21:17
 * @version 1.0.0
 */
public class Constants {
	
	/**
	 * @Field @PAGE_TIMEOUT : 页面超时时间
	 */
	public static Integer PAGE_TIMEOUT;
	
	public static class Music163 {
		
		/**
		 * @Field @MUSIC_163_SEARCH_URL : 网易云音乐的搜索页面
		 */
		public static String SEARCH_URL;
		
		/**
		 * @Field @SEARCH_INPUT_ID : 搜索框ID
		 */
		public static String SEARCH_INPUT_ID;
		
		/**
		 * @Field @SONG_IFRAME_ID : 歌曲内容所属的iframe id
		 */
		public static String SONG_IFRAME_ID;
		
		/**
		 * @Field @SONG_SAVE_PATH : 歌曲保存路径
		 */
		public static String SONG_SAVE_PATH;
		
		/**
		 * @Field @SONG_FORMAT : 音乐文件格式
		 */
		public static List<String> SONG_FORMAT = new ArrayList<String>();
		
		/**
		 * @Field @SEARCH_SONG_TIMEOUT : 搜索歌曲的超时时间，默认10秒
		 */
		public static Integer SEARCH_SONG_TIMEOUT;
		
		public static String getSavePath(String dir, String fileName) {
			return SONG_SAVE_PATH.concat(File.separator).concat(fileName);
		}
	}
	
	public static void refresh() {
		String configPath = "config.properties";
		File file = new File(System.getProperty("user.dir").concat("\\config\\config.properties"));
		if (file.exists()) {
			configPath = "config/config.properties";
		}
		Configuration config = new Configuration(configPath);
		Music163.SEARCH_URL = config.getProperty("Music163SearchUrl", "https://music.163.com/#/search/m/");
		Music163.SEARCH_INPUT_ID = config.getProperty("Music163SearchInputId", "m-search-input");
		Music163.SONG_IFRAME_ID = config.getProperty("Music163SongIframeId", "g_iframe");
		Music163.SONG_SAVE_PATH = config.getProperty("Music163SongSavePath", "F:\\Music\\163");
		Music163.SEARCH_SONG_TIMEOUT = config.getIntProperty("Music163SearchSongTimeout", 10);
		String songFormat = config.getProperty("Music163SongFormat", "m4a,mp3");
		if (StringUtils.isNotBlank(songFormat)) {
			Music163.SONG_FORMAT.addAll(Arrays.asList(songFormat.split(",")));
		}
		
		PAGE_TIMEOUT = config.getIntProperty("pageTimeout", 120);
	}
	
	static {
		refresh();
	}
}
