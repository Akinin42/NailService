package nailservice.domain;

import java.io.Serializable;

public class NailService implements Serializable {

    private static final long serialVersionUID = 1L;
    private int serviceId;
    private String name;
    private int cost;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}