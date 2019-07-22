package com.xiao.musicdown.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xiao.musicdown.service.MusicDownListener;
import com.xiao.musicdown.simulator.SimulatorAsync;

/**
 * @ClassName MusicDownRun
 * @Description 启动下载线程
 * @author XiaoRong
 * @Date 2019年7月20日 下午3:21:45
 * @version 1.0.0
 */
public class MusicDownRun {
	
	private final static Logger logger = LoggerFactory.getLogger(MusicDownRun.class);

	public static int downCount = 0;
	
	public static void startDown(String songValue, String savePath, MusicDownListener listener) {
		try {
			SimulatorAsync asyncSimulator = new SimulatorAsync(songValue, savePath, listener);
			new Thread(asyncSimulator).start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
