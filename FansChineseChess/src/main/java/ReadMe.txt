/**
 * 项目名称: FansChineseChess
 * 版本号：1.0
 * 名字：雷文
 * 博客: http://FansUnion.cn
 * CSDN:http://blog.csdn.net/FansUnion
 * 邮箱: leiwen@FansUnion.cn
 * QQ：240-370-818
 * 版权所有: 2011-2013,leiwen
 */
 
 上次更新：2010-11-12（代码开发结束）
 本次更新：2013-9-9（增加文档和注释）
 
 主要类的介绍
 LoginDialog:登录对话框，需要输入用户名和服务器地址(localhost)
 ChessServer:服务器
 ChessClient:客户端界面，登录成功后，跳转到该界面
 
 cn.fansunion.core
 ChessBoard:棋盘
 ChessPiece:棋子
 ChessPoint:棋子点
 GameRule:游戏规则
 MoveRecord:一条移动记录的所有信息
 MoveStep:一条移动记录的起始和结束的位置信息
 

 
 cn.fansunion.ui
 Demo:演示历史游戏界面
 MakeChessManual:棋子移动记录面板
 
 cn.fansunion.util
 ChessUtils:工具类
 Constants:常量
 DataPacket:2个客户端通信用的数据包
 HandleASession:为2个玩家定义一个线程类来处理新的会话
 Message:聊天信息
 ThreadPool:线程池
 
 
 
 2010年，我从CSDN下载中心下载了1个中国象棋程序，功能比较简单。
 我在此基础上，开发了当前版本(1.0)的程序。相对于下载的版本，
 增加了联网对战，悔棋等功能，增加必要的注释，重命名类、函数、变量的名字，提高了可读性。
 
 
 游戏运行方式：
 1.运行ChessServer服务器。
 2.运行LoginDialog,输入玩家1的用户名和服务器地址（本机是localhost） 比如 leiwen,localhost
 3.运行LoginDialog,输入玩家2的用户名和服务器地址（本机是localhost） 比如 fansunion,localhost
   2个玩家的用户名不能相同。
   
 4.2个玩家登录之后，自动绑定到1个会话中。
   1个为红方，1个为蓝方。
     蓝方先“准备游戏”，红方然后“开始游戏”。
     
 
 特别说明：这个1.0版本的功能比较弱，用户体验也很差，bug也不少。
 主要用来学习用，如果有兴趣，可以自行完善。
 
 2.0版本比1.0版本要好很多，无论是功能还是用户体验，近期更新，敬请期待。
 
 
 