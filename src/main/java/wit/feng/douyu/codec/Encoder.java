package wit.feng.douyu.codec;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
		byte[] bys = msg.getBytes("UTF-8");
		int framelen = bys.length+9;
		out.writeBytes(htonl(framelen));
		out.writeBytes(htonl(framelen));
		out.writeBytes(htons((short)689));
		out.writeBytes(htons((short)0));
		out.writeBytes(bys);
		out.writeByte(0);
	}
	
	public static byte[] htonl(int i)
	{
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
	}
	public static byte[] htons(short i)
	{
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(i).array();
	}


}
