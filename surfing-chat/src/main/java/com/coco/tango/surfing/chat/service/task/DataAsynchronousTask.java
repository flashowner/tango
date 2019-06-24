//package com.coco.tango.surfing.chat.service.channel.task;
//
//import com.coco.tango.surfing.chat.constant.LogConstant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.FutureTask;
//
///**
// * 数据异步转移方法
// * Created by MySelf on 2018/12/3.
// */
//@Service
//@Slf4j
//public class DataAsynchronousTask {
//
//    /**
//     * 用户读数据接口伪实现
//     */
//    @Autowired
//    private InChatToDataBaseService inChatToDataBaseService;
//
//    /**
//     * 将Netty数据消息借助这个方法已新线程发送给用户实现读方法
//     *
//     * @param maps {@link Map} 传递消息
//     */
//    public void writeData(Map<String, Object> maps) {
//        Boolean result = false;
//        ExecutorService service = Executors.newCachedThreadPool();
//        final FutureTask<Boolean> future = new FutureTask<Boolean>(new DataCallable(inChatToDataBaseService, maps));
//        service.execute(future);
//        try {
//            result = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            log.error(LogConstant.DATAASYNCHRONOUSTASK_01);
//        } catch (ExecutionException e) {
//            log.error(LogConstant.DATAASYNCHRONOUSTASK_02);
//        }
//        if (!result) {
//            log.error(LogConstant.DATAASYNCHRONOUSTASK_03);
//        }
//    }
//
//}
