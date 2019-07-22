package com.xiao.musicdown.run;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiao.musicdown.constants.Constants;
import com.xiao.musicdown.model.SongGatherResult;
import com.xiao.musicdown.service.MusicService;
import com.xiao.musicdown.service.impl.Music163Service;
import com.xiao.musicdown.simulator.Simulator;
import com.xiao.musicdown.utils.CommonUtils;

/**
 * @ClassName Music163DownRun
 * @Description 网易云音乐抓包下载
 * @author XiaoRong
 * @Date 2019年7月19日 上午10:26:44
 * @version 1.0.0
 */
public class Music163DownRun {

	private final static Logger logger = LoggerFactory.getLogger(Music163DownRun.class);

	public static void main(String[] args) {
		//加载配置文件到常量池
		Constants.refresh();
		//创建模拟器
		Simulator simulator = new Simulator();
		//创建网易云音乐下载器服务
		MusicService music163 = new Music163Service();
		//打开网易云音乐的搜索地址
		simulator.openUrl(Constants.Music163.SEARCH_URL, Constants.Music163.SONG_IFRAME_ID);
		//获取浏览器对象
		WebDriver driver = simulator.getDriver();
		//获取要下载歌曲列表
		List<String> songList = readSongList();
		//开始循环歌曲列表并下载
		for (String songName : songList) {
			boolean searchResult = music163.search(driver, Constants.Music163.SEARCH_INPUT_ID, songName);
			if (searchResult && music163.resultMatch(driver)) {
				SongGatherResult result = music163.gatherLogs(driver, songName);
				if (result.isSuccess() && CommonUtils.saveFile(result.getUrl(), result.getSavePath())) {
					logger.info("文件【{}】下载成功，保存路径:{}", songName, result.getSavePath());
				} else {
					logger.info("文件【{}】下载失败", songName);
				}
			}
		}
	}
	
	private static List<String> readSongList() {
		List<String> songList = new ArrayList<String>();
		try {
			String url = System.getProperty("user.dir").concat("\\").concat("song.txt");
			logger.info("获取要下载的歌曲列表，扫描文件:{}", url);
			File file = new File(url);
			songList = FileUtils.readLines(file, "UTF-8");
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return songList;
	}
	
}
