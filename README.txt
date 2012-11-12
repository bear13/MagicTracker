MagicTracker 0.0

作者:@胡可聪 (新浪微博: http://weibo.com/keanhu )

起源:在研究开源项目源代码时,常用日志工具(如 commons-logging )对代码执行过程进行跟踪.但日志工具无法满足实际需求,于是MagicTracker诞生了.

介绍:整个工程结构非常像 commons-logging ,原理也几乎一模一样.不同的是,commons-logging针对的是日志级别,MagicTracker针对的是信息阶段.

功能:控制台输出信息具有层次结构,可读性更佳.通过配置文件,可配置输出那些阶段的信息,可配置输出为文本文件,可配置输出为html文件.

如何运行例子(org/magictracker/test/TestDemo):

1.TestDemo 例子简介

  t.begin(name,"...") 和 t.stop() 方法,形成一个阶段范围,name为该阶段名字.

  t.r("..."),输出信息,跟commons-logging的info()方法类似.

  t.reportList(),输出TestDemo代码里所配置的所有阶段名字,务必放在TestDemo代码执行的最后位置.

2.tracker-config.xml 为用户配置文件

  <visible-list> 元素,用于配置显示那些阶段,name值为该阶段的名字,由TestDemo.java的 t.begin(name,"...")指定.

  <handlers> 元素,配置信息输出的方式,默认只在控制台输出信息.TxtHandler用于输出为文本文件,HtmlHandler为输出html文件,它们的path值为文件输出的路径,请事先创建所配置的目录,否则程序抛出无法找到路径的异常.不需要的话,请注释掉.

3.build.xml 是Ant任务,只将 MagicTracker 打包成jar,请事先编译源文件.

4.MagicTracker 依赖这些包

commons-beanutils-1.7.0
commons-collections-3.1
commons-digester-1.7
commons-logging-1.0.4

5.运行 TestDemo 即可看到信息输出效果


