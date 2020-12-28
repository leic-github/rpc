package org.gupao.client.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class ServiceDiscoveryWithZk implements ServiceDiscovery {
    private CuratorFramework curatorFramework;
    private final String ZK_CLUSTER_ADDR = "47.103.202.101:2181,47.103.202.101:2182,47.103.202.101:2183";
    private List<String> paths;

    private LoadBalance loadBalance = new RandomLoadBalance();

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZK_CLUSTER_ADDR)
                .sessionTimeoutMs(5000)
                .namespace("registry")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();
    }

    @Override
    public String discovery(String serviceName) {
//        查找这个服务的子节点
        try {
            String serviceNamePath = "/" + serviceName;
            paths = curatorFramework.getChildren().forPath(serviceNamePath);
            listenerNodeChange(serviceNamePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        负载均衡
        return loadBalance.select(paths);
    }

    /**
     * 监听节点变化
     *
     * @param serviceNamePath
     */
    private void listenerNodeChange(String serviceNamePath) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, serviceNamePath, false);
        pathChildrenCache.getListenable().addListener((curatorFramework1, event) -> {
            System.out.println("节点发生变化:" + event);
            paths = curatorFramework1.getChildren().forPath(serviceNamePath);
        });
        pathChildrenCache.start();
    }

}
