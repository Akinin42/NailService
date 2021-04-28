package nailservice.entity;

import java.util.Objects;

public class Customer {
    private final Integer customerId;
    private final String name;
    private final String phone;

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.phone = builder.phone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, phone);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        return (Objects.equals(customerId, other.customerId) && Objects.equals(phone, other.phone));
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", name=" + name + ", phone=" + phone + "]";
    }

    public static class Builder {
        private Integer customerId;
        private String name;
        private String phone;

        private Builder() {
        }

        public Builder withId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}
