package com.coco.tango.surfing.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Netty 服务 参数 配置
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {


    /**
     * 监听端口
     */
    private int port = 8888;

    /**
     * 接收主线程数
     */
    private int bossThread = 1;

    /**
     * 工作处理线程数
     */
    private int workerThread = 4;

    /**
     * 保持长连接
     */
    private boolean keepalive = true;


    /**
     * 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
     *
     * @return {@link Integer} 服务端接受连接的队列长度
     */
    private int backlog = 1024;

    /**
     * TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。
     * 该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，
     * 如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。
     *
     * @return {@link Boolean} Nagle算法是否启用
     */
    private boolean nodeLay = true;

    /**
     * 地址复用，默认值False。有四种情况可以使用：
     * (1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。
     * (2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。
     * (3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。
     * (4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。
     *
     * @return {@link Boolean} 地址复用
     */
    private boolean reuseaddr = true;

    /**
     * 数据发送缓冲区大小
     */
    private int sndBuf = 10485760;

    /**
     * 数据接收缓冲区大小 10M
     */
    private int revBuf = 10485760;

    /**
     * 读超时时间
     */
    private int heart = 180;

    /**
     * 消息 重发周期
     */
    private int period = 10;

    /**
     * 服务名称
     */
    private String serverName = "iot-netty-chat";

    /**
     * 消息 重发延迟
     */
    private int initalDelay = 10;

    /**
     * HTTP 请求 聚合 内容 最大值
     */
    private int maxContext = 65536;

    /**
     * websocket地址
     */
    private String webSocketPath = "/ws";


}

    
    
  