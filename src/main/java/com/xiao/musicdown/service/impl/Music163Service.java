package com.xiao.musicdown.service.impl;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.xiao.musicdown.constants.Constants;
import com.xiao.musicdown.model.SongGatherResult;
import com.xiao.musicdown.service.MusicService;
import com.xiao.musicdown.utils.CommonUtils;
import com.xiao.musicdown.utils.JSONReader;

public class Music163Service implements MusicService {
	
	private final static Logger logger = LoggerFactory.getLogger(Music163Service.class);

	/**
	 * @Description 搜索指定歌曲
	 * @author XiaoRong
	 * @Date 2019年7月3日 上午11:30:35
	 * @param searchInputId：搜索input框元素ID
	 * @param songName：歌曲名称，格式：海阔天空-Beyond
	 * @param frameId：如果存在，则将焦点切换到frame
	 */
	@Override
	public boolean search(WebDriver driver, String searchInputId, String songName) {
		try {
			if (StringUtils.isBlank(songName)) {
				throw new Exception("songName is empty");
			}
			WebElement searchInputElem = driver.findElement(By.id(searchInputId));
			if (searchInputElem != null) {
				searchInputElem.clear();
				logger.info("搜索歌曲：{}", songName);
				searchInputElem.sendKeys(songName);
				searchInputElem.sendKeys(Keys.ENTER);
				return true;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * @Description 搜索结果匹配，默认取第一条
	 * @author XiaoRong
	 * @Date 2019年7月3日 下午1:50:33
	 * @param author
	 */
	@Override
	public boolean resultMatch(WebDriver driver) {
		try {
			driver.manage().timeouts().implicitlyWait(Constants.Music163.SEARCH_SONG_TIMEOUT, TimeUnit.SECONDS);
			WebElement plyElem = driver.findElement(By.cssSelector(".srchsongst .item a.ply")); //播放按钮
			if (plyElem != null) {
				logger.info("匹配搜索结果成功，加入播放列表");
				plyElem.click();
				return true;
			} else {
				logger.info("匹配搜索结果失败");
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public SongGatherResult gatherLogs(WebDriver driver, String songName) {
		SongGatherResult result = new SongGatherResult();
		try {
			//休眠2秒等待网络日志
			Thread.sleep(2000);
			logger.info("开始采集【{}】的网络日志.", songName);
			Logs logs = driver.manage().logs();
			LogEntries entries = logs.get(LogType.PERFORMANCE);
			Iterator<LogEntry> iter = entries.iterator();
			while (iter.hasNext()) {
				JSONObject message = JSONObject.parseObject(iter.next().getMessage());
				String url = JSONReader.getString(message, "message/params/response/url");
				StringBuilder suffix = new StringBuilder();
				//判断url是否为音乐源文件
				if (CommonUtils.endsWith(url, Constants.Music163.SONG_FORMAT, suffix)) {
					result.setSuffix(suffix.toString());
					result.setUrl(url);
					result.setName(songName);
					result.setSuccess(true);
					logger.info("【{}】的音乐源文件采集成功:{}", songName, JSONObject.toJSONString(result));
					break;
				}
			}
			if (!result.isSuccess()) {
				logger.info("【{}】的音乐源文件采集失败了.", songName);
				closeShade(driver);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * @Description 关闭遮挡层
	 * @author XiaoRong
	 * @Date 2019年7月3日 下午5:42:14
	 * @param driver
	 */
	private void closeShade(WebDriver driver) {
		try {
			//版权问题遮挡层
			WebElement copyright = driver.findElement(By.cssSelector("a[data-action=\"close\"]"));
			if (copyright != null) {
				copyright.click();
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
		try {
			//开通会员遮挡层
			WebElement closeMember = driver.findElement(By.cssSelector(".m-vipcashier-title-close"));
			if (closeMember != null) {
				closeMember.click();
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

}
