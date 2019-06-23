package com.coco.tango.surfing.chat.bootstrap.server;

import com.coco.tango.surfing.chat.config.NettyConfig;
import com.coco.tango.surfing.common.utils.IpUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty 服务控制器 实现类
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Component
@Slf4j
public class NettyBootstrapServer extends AbstractBootstrapServer {

    @Autowired
    private NettyConfig nettyConfig;


    private ServerBootstrap bootstrap = new ServerBootstrap();

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    /**
     * 关闭
     */
    @Override
    public void shutdown() {
        if (workGroup != null && bossGroup != null) {
            try {
                bossGroup.shutdownGracefully().sync();// 优雅关闭
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("服务端关闭资源失败【" + IpUtils.getHost() + ":" + nettyConfig.getPort() + "】");
            }
        }
    }


    /**
     * 启动
     */
    @Override
    @PostConstruct
    public void start() {
        initEventPool();
        bootstrap.group(bossGroup, workGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, nettyConfig.isReuseaddr())
                .option(ChannelOption.SO_BACKLOG, nettyConfig.getBacklog())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF,nettyConfig.getRevBuf())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        initHandler(ch.pipeline(), nettyConfig);
                    }
                })
                .childOption(ChannelOption.TCP_NODELAY, nettyConfig.isNodeLay())
                .childOption(ChannelOption.SO_KEEPALIVE, nettyConfig.isKeepalive())
                .childOption(ChannelOption.SO_SNDBUF,nettyConfig.getSndBuf())
        ;
        bootstrap.bind(IpUtils.getHost(), nettyConfig.getPort()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("服务端启动成功【" + IpUtils.getHost() + ":" + nettyConfig.getPort() + "】");
//                AutoConfig.address = IpUtils.getHost() + ":" + nettyConfig.getPort();
//                RedisConfig.getInstance();
            } else {
                log.info("服务端启动失败【" + IpUtils.getHost() + ":" + nettyConfig.getPort() + "】");
            }
        });


    }

    /**
     * 根据不同操作系统配置 连接
     */
    private void initEventPool() {
        bootstrap = new ServerBootstrap();
        if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(nettyConfig.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(nettyConfig.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(nettyConfig.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(nettyConfig.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }
}

    
    
  