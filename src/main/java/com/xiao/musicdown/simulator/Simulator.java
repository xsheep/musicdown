package com.xiao.musicdown.simulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName Simulator
 * @Description 模拟器
 * @author XiaoRong
 * @Date 2019年7月3日 上午11:19:46
 * @version 1.0.0
 */
public class Simulator {
	
	private final static Logger logger = LoggerFactory.getLogger(Simulator.class);

	private WebDriver driver;
	
	private static class HolderSingletonHolder {
        static Simulator instance = new Simulator();
    }

    public static Simulator getInstance() {
        return HolderSingletonHolder.instance;
    }
	
	public Simulator() {
		init();
	}
	
	public void init() {
		ChromeDriverService service = new ChromeDriverService.Builder().build();
		try {
			service.start();
			driver = new RemoteWebDriver(service.getUrl(), chromeOption());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void quit() {
		driver.quit();
	}
	
	/**
	 * @Description 浏览器设置
	 * @author XiaoRong
	 * @Date 2019年7月3日 上午11:01:39
	 * @return
	 */
	private DesiredCapabilities chromeOption() {
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		//启用Performance，Log日志采集
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		
		//创建HashMap类的一个对象
		Map<String, Object> prefs = new HashMap<String, Object>();
		//设置提醒的设置，2表示block
		prefs.put("profile.default_content_setting_values.notifications", 2);
		cap.setCapability("prefs", prefs);
		return cap;
	}
	
	/**
	 * @Description 打开指定的URL
	 * @author XiaoRong
	 * @Date 2019年7月3日 上午11:19:39
	 * @param url
	 */
	public void openUrl(String url, String frameId) {
		try {
			driver.get(url);
			driver.manage().window().maximize();
			if (StringUtils.isNotBlank(frameId)) {
				driver.switchTo().frame(frameId);
			}
			//driver.manage().timeouts().implicitlyWait(Constants.PAGE_TIMEOUT, TimeUnit.SECONDS);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
