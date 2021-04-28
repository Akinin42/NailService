package nailservice.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Order {

    private final Integer id;
    private final LocalDate date;
    private final LocalTime time;
    private final Customer customer;
    private final NailService nailService;

    private Order(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.time = builder.time;
        this.customer = builder.customer;
        this.nailService = builder.nailService;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public NailService getNailService() {
        return nailService;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Order)) {
            return false;
        }
        Order other = (Order) obj;
        return (Objects.equals(id, other.id) && Objects.equals(date, other.date) && Objects.equals(time, other.time));
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", date=" + date + ", time=" + time + ", customer=" + customer.getName()
                + ", nailService=" + nailService.getName() + "]";
    }

    public static class Builder {

        private Integer id;
        private LocalDate date;
        private LocalTime time;
        private Customer customer;
        private NailService nailService;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Builder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder withNailService(NailService nailService) {
            this.nailService = nailService;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
