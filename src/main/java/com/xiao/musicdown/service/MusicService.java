package com.xiao.musicdown.service;

import org.openqa.selenium.WebDriver;

import com.xiao.musicdown.model.SongGatherResult;

public interface MusicService {

	public boolean search(WebDriver driver, String searchInputId, String songName);
	
	public boolean resultMatch(WebDriver driver);
	
	public SongGatherResult gatherLogs(WebDriver driver, String songName);
	
}
