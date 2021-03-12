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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cost;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + serviceId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NailService other = (NailService) obj;
        if (cost != other.cost)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (serviceId != other.serviceId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NailService [serviceId=" + serviceId + ", name=" + name + ", cost=" + cost + "]";
    }
}