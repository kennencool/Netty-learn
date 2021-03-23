package com.kennen.HttpServer.handler;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: hejiyuan
 * @Date: 2021/3/23 17:49
 * @Description:
 */
public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest);
}
