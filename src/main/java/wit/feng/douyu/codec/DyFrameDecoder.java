package wit.feng.douyu.codec;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class DyFrameDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if (in.readableBytes() < 12) {
			return;
		}
		in.markReaderIndex();
		byte[] bys = new byte[4];
		in.readBytes(bys);
		int size = ntohl(bys);
		// 等到读完一个整个体消息
		int readablebytes = in.readableBytes();
		if (readablebytes < size) {
			in.resetReaderIndex();
			return;
		}
		in.readBytes(bys);
		int size2 = ntohl(bys);
		short serverflag = in.readShort();
		short keepfield = in.readShort();
		// TODO check serverflag
		if (size != size2) {
			in.resetReaderIndex();
			throw new Exception("frame size error");
		}

		byte[] decode = new byte[size - 9];
		in.readBytes(decode);
		byte endbys = in.readByte();
		if ((endbys) != 0) {
			in.resetReaderIndex();
			throw new Exception("frame end error");
		}
		out.add(new String(decode, "UTF-8"));
	}

	public static int ntohl(byte[] bys) {
		return ByteBuffer.wrap(bys).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
}
