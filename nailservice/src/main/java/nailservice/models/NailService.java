package nailservice.models;

import java.io.Serializable;
import java.util.Objects;

public class NailService implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer cost;
    private Integer duration;

    public Integer getId() {
        return id;
    }

    public void setId(int serviceId) {
        this.id = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NailService)) {
            return false;
        }
        NailService other = (NailService) obj;
        return (Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(cost, other.cost));
    }

    @Override
    public String toString() {
        return "NailService [serviceId=" + id + ", name=" + name + ", cost=" + cost + "]";
    }
}