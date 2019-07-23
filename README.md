# musicdown
该程序使用Selenium工具模拟，实现功能如下：</br>
1.打开网易云音乐，并搜索指定歌曲进行播放</br>
2.通过抓取Network日志筛选出音乐源文件（m4a,mp3） </br>
3.下载筛选出来的源文件到指定目录</br></br>

程序可直接导入Eclipse运行，如果有Maven环境，直接使用"mvn package"生成可执行jar包，driver目录是chrome驱动程序，与运行的jar放在同级目录下即可
