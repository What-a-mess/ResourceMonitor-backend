# ResourceMontior-backend

一个可以部署在Linux上的简单性能监视器-后端部分

为什么总是有一些奇奇怪怪的课程作业😣

## 功能

1. 监视CPU占用率，提供整体占用率接口以及每核心占用率接口；
2. 监视内存使用情况，提供可用内存接口、已占用内存接口以及内存整体容量接口；
3. 监视磁盘使用情况，提供获取磁盘读写速度的接口（Windows和macOS似乎都把IO速度合并到读速度里面了，但是这个是Linux的资源监视器所以无所谓！）；
4. 查看文件系统使用情况，可以看到各个文件系统的容量以及空闲量；
5. （TODO）查看网络接口使用情况
6. ~~（TODO）查看IPv4和IPv6的连接（应该会做吧……）~~
7. ~~（TOThink）我也不知道看oshi文档的时候会突发奇想再加什么新的功能~~

## Service层设计思路

Service层用了ScheduledExecutorService来定时更新每个对应资源的状态。

这样做可以让请求不阻塞，并且可以处理多个用户时间小于oshi获取占用率所需的延迟

~~至于为什么要使用这个方法而不是SpringBoot的@Sceduled注解，这是因为用户应该可以选择监视的间隔（比如5s更新一次），
如果代码里面选择最小间隔（1s）进行更新，用户每5s请求一次会导致5s中有4s的信息没有被计算在平均资源占用率里面，
所以会需要一个可以动态调整的定时任务。~~

这样实现也不对，一个用户调整会影响其他用户的时间粒度，应该要记录过去一段时间的资源占用然后平均，但现有的方法在单用户的情况下能行，就这样吧！