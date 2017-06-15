# douyuDanmu
使用netty抓取斗鱼弹幕

# 使用方法
`mvn assembly:assembly`
会在target下生成`douyu-0.0.1-jar-with-dependencies.jar`
`java -jar douyu-0.0.1-jar-with-dependencies.jar -rid <roomid>`
就可连接房间弹幕

# 程序流程
* 程序入口 Main.java。参考netty的exmpale的factorial中客户端的写法
* 编码与解码类
	* DyFrameDecoder 按斗鱼的协议把字节流分片组合为字符消息
	* FrameToMessageDecoder 把字符消息转化为key-value的消息
	* Encoder 把字符消息按斗鱼的协议编码为字节流
* 处理器类 FrameHandler ，在这里相当于业务层的处理位置。只是简单按消息类型输出
* 消息实体，参考https://github.com/yanglw/DouYuDanmaku ,增加消息类别，做了简化处理


