# KnapsackToGo4
强大的跨服数据同步插件第4代。

可在多个服务器之间同步玩家数据，支持多种同步方式。

使用KnapsackToGo4可以轻松的完成多服务器之间的数据同步。

# 支持的同步方式
- 文件
- mysql
- sqlite

#  如何保证数据安全
KnapsackToGo4使用的独占锁的方式，当玩家在一个服务器中的时其他服务器无法读取。

下面是两个mc客户端登录同一个游戏账号进入安装了KnapsackToGo4并且互相同步的服务器的演示。

![asd](图片/如何保证数据安全.gif)

# 为什么写这款插件
KnapsackToGo2插件已经有很多服务器在使用了，并且功能也挺完善。
但是仍然存在一些问题，例如：服务器崩溃后玩家再次进入服务器就需要等待许久。
我自己对KnapsackToGo2也是不满意的，
在开发完KnapsackToGo2之后我又继续开发了许多插件，也学习到了很多编程经验，我感觉我的编程技术有了很大的进步。
在总结了前几代插件的毛病和逻辑上的问题后，我想到了一种新的方案。于是我决定重新编写了一个更好的KnapsackToGo4。
这次全新的KnapsackToGo4是在总结了前面所有失败的经验后完全重新编写的，运行逻辑也是最合理符合直觉的。

ps：
为什么新一代叫KnapsackToGo4而不是KnapsackToGo3呢?
因为KnapsackToGo3在很久之前就写完了，但是因为问题太多所以就弃坑了。



## 相较于KnapsackToGo2的提升
 - 拥有更多同步方式
 - 更强大的扩展性
 - 解决了服务器崩溃死锁的问题
 