package org.gupao.client.discovery;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String select(List<String> serviceAddrList) {
        if (serviceAddrList == null || serviceAddrList.size() == 0) {
            return null;
        }
        if (serviceAddrList.size() == 1) {
            return serviceAddrList.get(0);
        }
        return doSelect(serviceAddrList);
    }

    protected abstract String doSelect(List<String> serviceAddrList);
}
