package wit.feng.douyu.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import wit.feng.douyu.message.DyMessage;

public class FrameToMessageDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		out.add(new DyMessage(msg));
	}
}
