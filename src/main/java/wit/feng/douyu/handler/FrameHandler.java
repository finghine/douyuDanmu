package wit.feng.douyu.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import wit.feng.douyu.message.ChatMsg;
import wit.feng.douyu.message.DgbMsg;
import wit.feng.douyu.message.DyMessage;
import wit.feng.douyu.message.SpbcMsg;
import wit.feng.douyu.message.UenterMsg;

public class FrameHandler extends SimpleChannelInboundHandler<DyMessage>{

	private ChannelHandlerContext ctx;

	private int roomid;
	
	public FrameHandler(int roomid) {
		this.roomid = roomid;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DyMessage msg) throws Exception {
		
		String type = msg.getType();
		switch(type)
		{
		case "loginres":
			System.out.println("login result");
//			System.out.println(msg.getMsg());
			sendJoinGropMsg();
			break;
		case "qausrespond":
			sendHeart();
			break;
		case "chatmsg":
			System.out.println(new ChatMsg(msg));
			break;
		case "dgb":
			System.out.println(new DgbMsg(msg));
			break;
		case "uenter":
			System.out.println(new UenterMsg(msg));
			break;
		case "spbc":
			System.out.println(new SpbcMsg(msg));
			break;
		default:
			System.out.println("othertype:"+msg.getMsg());
			break;
			// 获取奖励
//			type@=gpbc/cnt@=5/sid@=105266620/did@=12631533/rpt@=0/snk@=桃花村的占据主任/dnk@=叻忄猪刄/pnm@=赞/rid@=297535/
			// 粉丝勋章升级
//			type@=blab/uid@=19009572/nn@=天涯3/lbl@=3/bl@=4/ba@=1/bnn@=骚浪蚊/rid@=297535/
			// 
//			type@=synexp/o_exp@=236832430/o_lev@=39/o_minexp@=230080000/o_upexp@=18967570/rid@=297535/
			// unknow
//			rid@=297535/type@=ubsc/bc@=39180/bts@=0/bct@=0/tse@=0/cse@=0/gfid@=0/an@=profess/
		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx  = ctx;
		sendLoginMsg();
	}

	private void sendLoginMsg() {
		String logininfo=String.format("type@=loginreq/roomid@=%d/", roomid);
		ctx.writeAndFlush(logininfo);
	}
	private void sendJoinGropMsg() {
		String joingrop=String.format("type@=joingroup/rid@=%d/gid@=-9999/", roomid);
		ctx.writeAndFlush(joingrop);
	}

	private void sendHeart(){
		String hearttext = "type@=mrkl/";
		ctx.writeAndFlush(hearttext);
	}


}
