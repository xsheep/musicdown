package com.xiao.musicdown.constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	public static Integer PAGE_TIMEOUT = 120;
	
	public static class Music163 {
		
		/**
		 * @Field @MUSIC_163_SEARCH_URL : 网易云音乐的搜索页面
		 */
		public static String SEARCH_URL = "https://music.163.com/#/search/m/";
		
		/**
		 * @Field @SEARCH_INPUT_ID : 搜索框ID
		 */
		public static String SEARCH_INPUT_ID = "m-search-input";
		
		/**
		 * @Field @SONG_IFRAME_ID : 歌曲内容所属的iframe id
		 */
		public static String SONG_IFRAME_ID = "g_iframe";
		
		/**
		 * @Field @SONG_SAVE_PATH : 歌曲默认保存路径
		 */
		public static String SONG_SAVE_PATH = "D:\\MisicDown\\";
		
		/**
		 * @Field @SONG_FORMAT : 音乐文件格式
		 */
		public static List<String> SONG_FORMAT = new ArrayList<String>();
		
		/**
		 * @Field @SEARCH_SONG_TIMEOUT : 搜索歌曲的超时时间，默认10秒
		 */
		public static Integer SEARCH_SONG_TIMEOUT = 10;
		
		public static String getSavePath(String dir, String fileName) {
			return SONG_SAVE_PATH.concat(File.separator).concat(fileName);
		}
		
		static {
			SONG_FORMAT.add("m4a");
			SONG_FORMAT.add("mp3");
		}
	}
	
}
