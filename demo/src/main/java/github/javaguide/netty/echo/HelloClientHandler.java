package github.javaguide.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author: hejiyuan
 * @Date: 2021/3/23 10:42
 * @Description:
 */
@ChannelHandler.Sharable
public class HelloClientHandler extends ChannelInboundHandlerAdapter {

    private final String message;

    public HelloClientHandler(String message) {
        this.message = message;
    }

    /**
     * 客户端和服务端的连接建立之后就会被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //客户端发送数据到服务端
        System.out.println("client sen msg to server " + message);
        ctx.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));

    }

    /**
     * 客户端接收服务端发送数据调用的方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 客户端接收服务端发送过来的数据
        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("client receive msg from server: " + in.toString(CharsetUtil.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
