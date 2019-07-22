package com.xiao.musicdown.simulator;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiao.musicdown.constants.Constants;
import com.xiao.musicdown.model.SongGatherResult;
import com.xiao.musicdown.service.MusicDownListener;
import com.xiao.musicdown.service.MusicService;
import com.xiao.musicdown.service.impl.Music163Service;
import com.xiao.musicdown.utils.CommonUtils;

public class SimulatorAsync implements Runnable {
	
	private final static Logger logger = LoggerFactory.getLogger(SimulatorAsync.class);
	
	private String songValue;
	
	private String savePath;
	
	private MusicDownListener listener;
	
	public SimulatorAsync(String songValue, String savePath, MusicDownListener listener) {
		this.songValue = songValue;
		this.savePath = savePath;
		this.listener = listener;
	}

	@Override
	public void run() {
		Simulator simulator = new Simulator();
		WebDriver driver = simulator.getDriver();
		//打开网易云音乐的搜索地址
		simulator.openUrl(Constants.Music163.SEARCH_URL, Constants.Music163.SONG_IFRAME_ID);
		//获取要下载歌曲列表
		List<String> songList = Arrays.asList(songValue.split("\n"));
		MusicService musicService = new Music163Service();
		//开始循环歌曲列表并下载
		for (String songName : songList) {
			boolean searchResult = musicService.search(driver, Constants.Music163.SEARCH_INPUT_ID, songName);
			if (searchResult && musicService.resultMatch(driver)) {
				SongGatherResult result = musicService.gatherLogs(driver, songName);
				result.setSavePath(savePath);
				if (result.isSuccess() && CommonUtils.saveFile(result.getUrl(), result.getSavePath())) {
					logger.info("文件【{}】下载成功，保存路径:{}", songName, result.getSavePath());
				} else {
					logger.info("文件【{}】下载失败", songName);
				}
			}
		}
		if (listener != null) {
			listener.endAction(null);
			simulator.quit();
		}
	}

}
