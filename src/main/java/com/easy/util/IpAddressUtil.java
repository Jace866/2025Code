package com.easy.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author : hello_@
 * @Date : 2024/3/23
 */
@Slf4j
public class IpAddressUtil {

    public static final String UNKNOWN = "unknown";

    public static final String X_FORWARDED_FOR = "x-forwarded-for";

    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";

    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";

    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    public static final String X_REAL_IP = "X-Real-IP";

    public static final String LOCAL_IP_V4 = "127.0.0.1";

    public static final String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";

    public static final String CDN_SRC_IP = "cdn-src-ip";

    /**
     * 获取 IP 地址
     */
    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst(X_FORWARDED_FOR);
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_X_FORWARDED_FOR);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(X_REAL_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
            if (ip.equals(LOCAL_IP_V4) || ip.equals(LOCAL_IP_V6)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 获取请求IP.
     * @return String ip
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;
        Enumeration<?> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            if (CDN_SRC_IP.equalsIgnoreCase(name)
                    || X_FORWARDED_FOR.equalsIgnoreCase(name)
                    || PROXY_CLIENT_IP.equalsIgnoreCase(name)
                    || WL_PROXY_CLIENT_IP.equalsIgnoreCase(name)
                    || X_REAL_IP.equalsIgnoreCase(name)) {
                ip = request.getHeader(name);
            }
            if (StrUtil.isNotBlank(ip)){
                break;
            }
        }
        if (StrUtil.isBlank(ip)){
            ip = request.getRemoteAddr();
        }

        if (ip.equals(LOCAL_IP_V4) || ip.equals(LOCAL_IP_V6)) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
                ip = inet.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    public static String getRealAddress(String ip) {
        // 内网不查询
        if (internalIp(ip)) {
            return "内网IP·内网IP";
        }
        try {
            String rspStr = RegionUtil.getRegion(ip);
            if (StrUtil.isNotEmpty(rspStr)) {
                String[] obj = rspStr.split("\\|");
                StringBuilder address = new StringBuilder();
                for (String s : obj) {
                    if (!StrUtil.equals("0", s)) {
                        if (address.toString().equals("")) {
                            address.append(s);
                        }else {
                            address.append("·").append(s);
                        }
                    }
                }
                return address.toString();
            }
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", e.toString());
        }
        // ali地域查询
        return PureNetUtils.getAlibaba(ip);
    }

    /* 判断是否是内网IP */
    public static boolean internalIp(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        /*
         * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
         * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
         */
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd)
                || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd)
                || ipAddress.equals("127.0.0.1");
        return isInnerIp;
    }

    /* 获取IP数 */
    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);
        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    /**
     * @Description: 获取request
     */
    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = null;
        if (sra != null) {
            request = sra.getRequest();
        }
        return request;
    }

    public static void main(String[] args) {
        String realAddress = getRealAddress("110.42.6.129");
        System.out.println("realAddress = " + realAddress);
    }

}
