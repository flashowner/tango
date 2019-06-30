package com.coco.tango.surfing.core.utils;

import java.io.Serializable;
import java.text.MessageFormat;


/**
 * tedis 缓存 key
 *
 * @author ckli01
 * @date 2019-06-26
 */
public class CacheKeyUtil {

    public static String ROOT = "busyAuthCache";

    public static final String BUSY_GET_AUTH_USER_KEY_PREFIX      = ROOT + "." + "APP_GET_AUTH_USER_{0}_{1}";


    public static String getBusyGetAuthRoleUserKey(Serializable refUserCode, Serializable refTenant) {
        return MessageFormat.format(BUSY_GET_AUTH_USER_KEY_PREFIX, refUserCode, refTenant);
    }
}
