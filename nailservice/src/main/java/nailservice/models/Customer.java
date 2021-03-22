package nailservice.models;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer customerId;
    private String name;
    private String phone;

    public Integer getId() {
        return customerId;
    }

    public void setId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}