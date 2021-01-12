package com.xiao.musicdown.service;

import org.openqa.selenium.WebDriver;

import com.xiao.musicdown.model.SongGatherResult;

public interface MusicService {

	boolean search(WebDriver driver, String searchInputId, String songName);
	
	boolean resultMatch(WebDriver driver);
	
	SongGatherResult gatherLogs(WebDriver driver, String songName);
	
}
