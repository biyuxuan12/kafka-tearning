# kafka 初体验

* 为了体验kafka，我们将制作一个简单的网站。

* 首先，参照https://blog.gmem.cc/apache-kafka-study-note 笔记，下载并启动一个zookeeper 和一个kafka服务

* kafka服务启动后，启动本项目

`gradle clean build `

* 访问 127.0.0.1:8080

* 此时敲击键盘可弹奏钢琴，前台将弹奏的信息发送至后台，后台再广播给所有同时在访问此网页的客户端

* 在前端打开网页控制台，输入
`websocket.send("replay");`
打开回放功能
* 此时发送给后台的信息会被kafka记录，并回放给发送端，实现录音。
