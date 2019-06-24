package com.coco.tango.surfing.chat.cache.local;

import io.netty.channel.Channel;
import org.apache.logging.log4j.core.config.Scheduled;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket链接实例本地存储
 * Created by MySelf on 2018/11/26.
 */
public class WsCacheMap {

    /**
     * 存储用户标识与链接实例
     */
    private final static Map<String, Channel> maps = new ConcurrentHashMap<String, Channel>();

    /**
     * 存储链接地址与用户标识
     */
    private final static Map<String, String> addMaps = new ConcurrentHashMap<>();

    /**
     * 存储链接
     *
     * @param userCode {@link String} 用户标签
     * @param channel  {@link Channel} 链接实例
     */
    public static void saveWs(String userCode, Channel channel) {
        maps.put(userCode, channel);
    }

    /**
     * 存储登录信息
     *
     * @param address  登录地址
     * @param userCode 用户标签
     */
    public static void saveAd(String address, String userCode) {
        addMaps.put(address, userCode);
    }

    /**
     * 获取链接数据
     *
     * @param userCode {@link String} 用户标识
     * @return {@link Channel} 链接实例
     */
    public static Channel getByUserCode(String userCode) {
        return maps.get(userCode);
    }

    /**
     * 获取对应userCode标签
     *
     * @param address {@link String} 链接地址
     * @return {@link String}
     */
    public static String getByAddress(String address) {
        return addMaps.get(address);
    }

    /**
     * 删除链接数据
     *
     * @param userCode {@link String} 用户标识
     */
    public static void deleteWs(String userCode) {
        try {
            maps.remove(userCode);
        } catch (NullPointerException e) {
//            throw new NotFindLoginChannlException(NotInChatConstant.Not_Login);
        }
    }

    /**
     * 删除链接地址
     *
     * @param address
     */
    public static void deleteAd(String address) {
        addMaps.remove(address);
    }

    /**
     * 获取链接数
     *
     * @return {@link Integer} 链接数
     */
    public static Integer getSize() {
        return maps.size();
    }

    /**
     * 判断是否存在链接账号
     *
     * @param userCode {@link String} 用户标识
     * @return {@link Boolean} 是否存在
     */
    public static boolean hasUserCode(String userCode) {
        return maps.containsKey(userCode);
    }

    /**
     * 获取在线用户标签列表
     *
     * @return {@link Set} 标识列表
     */
    public static Set<String> getUserCodeList() {
        Set keys = maps.keySet();
        return keys;
    }

    public static String getByJedis(String userCode) {
        return null;
    }
}
