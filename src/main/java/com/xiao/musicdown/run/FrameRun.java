package com.xiao.musicdown.run;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xiao.musicdown.constants.Constants;
import com.xiao.musicdown.service.MusicDownListener;
import com.xiao.musicdown.utils.DefaultFontModify;

public class FrameRun {

	private static final String INPUT_TIP = "请输入要下载的歌曲名称，多个以[,]或者回车隔开";
	
	private JFrame frame;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRun window = new FrameRun();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrameRun() {
		initialize();
	}

	private void initialize() {
		try {
			//加载chromedriver，也可将其加入环境变量
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			DefaultFontModify.loadIndyFont();
			frame = new JFrame("网易云音乐歌曲下载");
			frame.getContentPane().setLayout(null);
			
			JLabel label = new JLabel("文件保存路径：");
			label.setBounds(42, 22, 89, 15);
			frame.getContentPane().add(label);
			
			JLabel label_1 = new JLabel("要下载的歌曲名称：");
			label_1.setBounds(18, 66, 108, 15);
			frame.getContentPane().add(label_1);
			
			textField = new JTextField();
			textField.setBounds(141, 15, 212, 30);
			textField.setEditable(false);
			frame.getContentPane().add(textField);
			textField.setColumns(10);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(140, 66, 294, 170);
			
			frame.getContentPane().add(scrollPane);
			
			JTextPane textPane = new JTextPane();
			textPane.setText(INPUT_TIP);
			textPane.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					//格式化输入的列表
					String text = textPane.getText();
					if (StringUtils.startsWith(text, "<")) {
						//html解析
						text = parseHtml(text);
					} else if (StringUtils.isNotBlank(text)) {
						text = text.replaceAll("[,，]", "\n");
					} else {
						text = INPUT_TIP;
					}
					textPane.setText(text);
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					String text = textPane.getText();
					if (StringUtils.equals(text, INPUT_TIP)) {
						textPane.setText("");
					}
				}
			});
			scrollPane.setViewportView(textPane);
			
			JButton button = new JButton("浏览..");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("请选择文件存放目录");
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fileChooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						String filepath = fileChooser.getSelectedFile().getPath();
						textField.setText(filepath);
						Constants.Music163.SONG_SAVE_PATH = filepath;
					}
				}
			});
			button.setBounds(363, 18, 71, 23);
			frame.getContentPane().add(button);
			frame.setResizable(false);
			frame.setBounds(140, 100, 450, 323);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JButton startDownBtn = new JButton("开始下载");
			startDownBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String savePath = textField.getText();
					String songValue = textPane.getText();
					if (StringUtils.isBlank(savePath)) {
						JOptionPane.showMessageDialog(frame, "请选择文件保存目录");
						return;
					}
					if (StringUtils.isBlank(songValue)) {
						JOptionPane.showMessageDialog(frame, "请输入要下载的歌曲(每行输入一首)" );
						return;
					}
					startDownBtn.setText("正在下载..");
					startDownBtn.setEnabled(false);
					MusicDownRun.startDown(songValue, savePath, new MusicDownListener() {
						@Override
						public void endAction(Object object) {
							startDownBtn.setText("开始下载");
							startDownBtn.setEnabled(true);
						}
					});
				}
			});
			startDownBtn.setBounds(170, 262, 93, 23);
			frame.getContentPane().add(startDownBtn);
			frame.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String parseHtml(String html) {
		StringBuilder result = new StringBuilder();
		Document document = Jsoup.parse(html);
		Elements elements = document.select(".m-table tbody tr");
		Iterator<Element> iterElem = elements.iterator();
		while (iterElem.hasNext()) {
			Element element = iterElem.next();
			Element txtElement = element.selectFirst("td:eq(1) span.txt a b");
			result.append(txtElement.attr("title")).append("\n");
		}
		return result.toString();
	}
}
