package wit.feng.douyu;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wit.feng.douyu.codec.DyFrameDecoder;
import wit.feng.douyu.codec.Encoder;
import wit.feng.douyu.codec.FrameToMessageDecoder;
import wit.feng.douyu.handler.FrameHandler;

public class Main {
	
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption("h", "help", false, "Print this usage information");  
		options.addOption("rid", "roomid", true, "roomid");  
		CommandLineParser parser = new BasicParser( );  
		CommandLine commandLine = parser.parse( options, args );  
		if(commandLine.hasOption("rid"))
		{
			String roomid =  commandLine.getOptionValue("rid","297535");
			int rid = Integer.parseInt(roomid);
			work(rid);
		}
		else
		{
			HelpFormatter f = new HelpFormatter();
			f.printHelp("douyu -rid <roomid>", options );
		}
	}
	public static void work(final int roomid)
	{
//		"danmu.douyu.com",12604
		String HOST="danmu.douyu.com";
		int PORT = 12604;
		   // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
//                     p.addLast(new LoggingHandler(LogLevel.INFO));
                     p.addLast(new DyFrameDecoder());
                     p.addLast(new FrameToMessageDecoder());
                     p.addLast(new Encoder());
                     p.addLast(new FrameHandler(roomid));
                 }
             });
            System.out.println("start connect room "+roomid);
            // Start the client.
            ChannelFuture f;
			f = b.connect(HOST, PORT).sync();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
	}
}
