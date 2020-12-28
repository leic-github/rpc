package org.gupao.client.discovery;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected String doSelect(List<String> serviceAddrList) {
        Random random = new Random();
        int index = random.nextInt(serviceAddrList.size());
        return serviceAddrList.get(index);
    }
}
