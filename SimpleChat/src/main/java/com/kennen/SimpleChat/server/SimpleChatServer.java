package com.kennen.SimpleChat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: hejiyuan
 * @Date: 2021/3/24 8:51
 * @Description: 简单聊天服务器——服务端
 */
public class SimpleChatServer {
    private int port;
    
    public SimpleChatServer(int port){
        this.port = port;
    }
    
    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            System.out.println("SimpleChatServer 启动");
            
            // 绑定端口，开始接受连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务器 socket关闭，但在这个例子中不会发生。
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("SimpleChatServer 关闭");
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new SimpleChatServer(port).run();
    }
}
