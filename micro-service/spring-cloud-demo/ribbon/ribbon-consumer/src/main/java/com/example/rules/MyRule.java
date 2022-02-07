package com.example.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/2 下午1:22
 */
public class MyRule extends AbstractLoadBalancerRule implements IRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 使用uri作为标识
        String uri = request.getRequestURI() + "?" + request.getQueryString();
        // 所有的节点列表
        List<Server> servers = getLoadBalancer().getAllServers();
        return route(uri.hashCode(), servers);
    }

    /**
     * 自定义的负载均衡策略, 根据hashId挑选一个服务, 使用简易的一致性哈希实现
     *
     * @author Jiahao Wang
     * @date 2021/12/2 下午1:27
     * @param hashId hashId
     * @param servers 现有的服务列表
     * @return com.netflix.loadbalancer.Server
     */
    public Server route(int hashId, List<Server> servers) {
        if (CollectionUtils.isEmpty(servers)) {
            return null;
        }
        TreeMap<Long, Server> address = new TreeMap<>();

        servers.forEach(server -> {
            // 虚化若干个服务节点，到环上, 相当于放大节点倍数
            for (int i = 0; i < 8; i++) {
                long hash = hash(server.getId() + i);
                address.put(hash, server);
            }
        });

        long hash = hash(String.valueOf(hashId));

        // 找到键 >= hash的 部分视图
        SortedMap<Long, Server> tailMap = address.tailMap(hash);
        if (CollectionUtils.isEmpty(tailMap)) {
            // 当request URL的hash值大于任意一个服务器对应的hashKey，
            // 取address中的第一个节点
            return address.firstEntry().getValue();
        }
        // 取第一个值即可, 也就是一致性哈希中
        return tailMap.get(tailMap.firstKey());
    }

    private long hash(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);

        md5.update(bytes);
        byte[] digest = md5.digest();

        long hashCode = ((long) (digest[2] & 0xFF << 16))
                | ((long) (digest[1] & 0xFF << 8))
                | ((long) (digest[0] & 0xFF));

        return hashCode & 0xffffffffL;
    }

}
