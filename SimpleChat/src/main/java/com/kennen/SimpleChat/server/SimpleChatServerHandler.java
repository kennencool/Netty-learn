package com.kennen.SimpleChat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: hejiyuan
 * @Date: 2021/3/24 8:36
 * @Description:
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 一个线程安全的集合，这里有所有的channel，当channel关闭时会自动从集合中移除
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[SERVER]:" + incoming.remoteAddress() + " 加入\n");
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[SERVER]:" + incoming.remoteAddress() + " 离开\n");
        // 这里channel会自动从集合中移除，无需remove方法
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for(Channel channel : channels){
            if(channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            }else{
                channel.writeAndFlush("[you]" + s + "\n");
            }
        }
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "在线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "离线\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:" + incoming.remoteAddress() + "异常\n");
        cause.printStackTrace();;
        ctx.close();
    }
    
}
