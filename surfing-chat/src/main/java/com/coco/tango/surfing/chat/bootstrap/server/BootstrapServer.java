package com.coco.tango.surfing.chat.bootstrap.server;

import com.coco.tango.surfing.chat.util.RemotingUtil;
import io.netty.channel.epoll.Epoll;

/**
 * Netty 服务控制器
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface BootstrapServer {


    /**
     * 关闭
     */
    void shutdown();

    /**
     * 启动
     */
    void start();


    /**
     * 根据 不同环境 选用不同的通道¬
     *
     * @return
     */
    default boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }


}
