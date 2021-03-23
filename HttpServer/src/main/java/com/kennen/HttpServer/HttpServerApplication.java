package com.kennen.HttpServer;


import com.kennen.HttpServer.server.HttpServer;

/**
 * @Author: hejiyuan
 * @Date: 2021/3/23 17:44
 * @Description: 自制 HttpServer 启动类
 */
public class HttpServerApplication {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
