package com.coco.tango.surfing.chat.bootstrap.server;

import com.coco.tango.surfing.chat.config.NettyConfig;
import com.coco.tango.surfing.chat.constant.BootstrapConstant;
import com.coco.tango.surfing.chat.bootstrap.server.handler.DefaultHandler;
import com.coco.tango.surfing.chat.service.HandlerBaseService;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Netty 服务控制器 抽象实现
 *
 * @author ckli01
 * @date 2019-06-12
 */
public abstract class AbstractBootstrapServer implements BootstrapServer {


    /**
     * @param channelPipeline channelPipeline
     * @param nettyConfig     服务配置参数
     */
    protected void initHandler(ChannelPipeline channelPipeline, NettyConfig nettyConfig) {
//        if (serverBean.isSsl()){
//            if (!ObjectUtils.allNotNull(serverBean.getJksCertificatePassword(),serverBean.getJksFile(),serverBean.getJksStorePassword())){
//                throw new NullPointerException(NotInChatConstant.SSL_NOT_FIND);
//            }
//            try {
//                SSLContext context = SslUtil.createSSLContext("JKS",serverBean.getJksFile(),serverBean.getJksStorePassword());
//                SSLEngine engine = context.createSSLEngine();
//                engine.setUseClientMode(false);
//                engine.setNeedClientAuth(false);
//                channelPipeline.addLast(BootstrapConstant.SSL,new SslHandler(engine));
//                System.out.println("open ssl  success");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        intProtocolHandler(channelPipeline, nettyConfig);
        channelPipeline.addLast(new IdleStateHandler(nettyConfig.getHeart(), 0, 0));
        channelPipeline.addLast(new DefaultHandler(SpringContextUtil.getBean(HandlerBaseService.class)));
    }

    private void intProtocolHandler(ChannelPipeline channelPipeline, NettyConfig nettyConfig) {
        channelPipeline.addLast(BootstrapConstant.HTTPCODE, new HttpServerCodec());
        channelPipeline.addLast(BootstrapConstant.AGGREGATOR, new HttpObjectAggregator(nettyConfig.getMaxContext()));
        channelPipeline.addLast(BootstrapConstant.CHUNKEDWRITE, new ChunkedWriteHandler());
        channelPipeline.addLast(BootstrapConstant.WEBSOCKETHANDLER, new WebSocketServerProtocolHandler(nettyConfig.getWebSocketPath(), null, true, nettyConfig.getRevBuf()));
    }

//    private void initSsl(InitNetty serverBean){
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.submit(() -> {});
//        String algorithm = SystemPropertyUtil.get("ssl.KeyManagerFactory.algorithm");
//        if (algorithm == null) {
//            algorithm = "SunX509";
//        }
//        SSLContext serverContext;
//        try {
//            //
//            KeyStore ks = KeyStore.getInstance("JKS");
//            ks.load( SecureSocketSslContextFactory.class.getResourceAsStream(serverBean.getJksFile()),
//                    serverBean.getJksStorePassword().toCharArray());
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
//            kmf.init(ks,serverBean.getJksCertificatePassword().toCharArray());
//            serverContext = SSLContext.getInstance(PROTOCOL);
//            serverContext.init(kmf.getKeyManagers(), null, null);
//        } catch (Exception e) {
//            throw new Error(
//                    "Failed to initialize the server-side SSLContext", e);
//        }
//        SERVER_CONTEXT = serverContext;
//    }

}



    
    
  