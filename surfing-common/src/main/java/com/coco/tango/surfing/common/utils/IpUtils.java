package com.coco.tango.surfing.common.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 获取Ip地址相关数据
 *
 * @author ckli01
 * @date 2018/8/10
 */
public class IpUtils {


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址
     * <p>
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * 如: 192.168.1.110, 192.168.1.120, 192.168.1.130
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 取真实IP地址
        if (!StringUtils.isEmpty(ip) && ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /***
     * 获取外网IP
     * @return
     */
    public static String internetIp() {
        try {

            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress = null;
            Enumeration<InetAddress> inetAddresses = null;
            while (networks.hasMoreElements()) {
                inetAddresses = networks.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null
                            && inetAddress instanceof Inet4Address
                            && !inetAddress.isSiteLocalAddress()
                            && !inetAddress.isLoopbackAddress()
                            && inetAddress.getHostAddress().indexOf(":") == -1) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

            return null;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 获取内网IP
     *
     * @return
     */
    public static String intranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取服务启动host
     *
     * @return
     */
    public static String getHost() {
        return internetIp() == null ? intranetIp() : internetIp();
    }


}
