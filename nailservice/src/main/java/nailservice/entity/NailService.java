package nailservice.entity;

import java.util.Objects;

public class NailService {
    
    private final Integer id;
    private final String name;
    private final Integer cost;
    private final Integer duration;
    
    private NailService(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.cost = builder.cost;
        this.duration = builder.duration;
    }
    
    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getDuration() {
        return duration;
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
    
    public static class  Builder{
        
        private Integer id;
        private String name;
        private Integer cost;
        private Integer duration;
        
        private Builder() {            
        }
        
        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }
        
        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder withCost(Integer cost) {
            this.cost = cost;
            return this;
        }
        
        public Builder withDuration(Integer duration) {
            this.duration = duration;
            return this;
        }
        
        public NailService build() {
            return new NailService(this);
        }
    }
}