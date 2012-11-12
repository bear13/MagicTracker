MagicTracker 0.0

作者:@胡可聪 (新浪微博: http://weibo.com/keanhu )

起源:在研究开源项目源代码时,常用日志工具(如 commons-logging )对代码执行过程进行跟踪.但日志工具无法满足实际需求,于是MagicTracker诞生了.

介绍:整个工程结构非常像 commons-logging ,原理也几乎一模一样.不同的是,commons-logging针对的是日志级别,MagicTracker针对的是信息段落.

功能:控制台输出信息具有层次结构,可读性更佳.通过配置文件,可配置输出那些段落的信息,可配置输出为文本文件,可配置输出为html文件.