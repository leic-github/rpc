package org.gupao.client.discovery;

import java.util.List;

/**
 * 负载均很算法
 */
public interface LoadBalance {

    String select(List<String> serviceAddrList);
}
