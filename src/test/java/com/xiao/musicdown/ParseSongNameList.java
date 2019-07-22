package com.xiao.musicdown;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseSongNameList {

	public static void main(String[] args) {
		try {
			URL url = ClassLoader.getSystemResource("html.txt");
			String html = FileUtils.readFileToString(new File(url.getPath()), "UTF-8");
			Document document = Jsoup.parse(html);
			Elements elements = document.select(".m-table tbody tr");
			Iterator<Element> iterElem = elements.iterator();
			while (iterElem.hasNext()) {
				Element element = iterElem.next();
				Element txtElement = element.selectFirst("td:eq(1) span.txt a b");
				System.out.println(txtElement.attr("title"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
