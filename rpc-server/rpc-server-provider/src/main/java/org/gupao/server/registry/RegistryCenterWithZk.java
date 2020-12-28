package org.gupao.server.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * zookeeper注册中心
 */
public class RegistryCenterWithZk implements IRegistryCenter {
    //初始化curator客户端
    private CuratorFramework curatorFramework;
    private final String ZK_CLUSTER_ADDR = "47.103.202.101:2181,47.103.202.101:2182,47.103.202.101:2183";

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
    public void registry(String serviceName, String serviceAddr) {
        String servicePath = "/" + serviceName;
        try {
            //判断节点是否创建
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath);
            }
            //添加临时节点，服务断开则节点被删除    curatorFramework.start() 建立了zk连接
            String serviceAddrPath = servicePath + "/" + serviceAddr;
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(serviceAddrPath);
            System.out.println("【"+serviceName+"】服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
