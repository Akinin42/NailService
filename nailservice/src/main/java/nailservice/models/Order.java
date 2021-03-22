package nailservice.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private Customer customer;
    private NailService nailService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public NailService getNailService() {
        return nailService;
    }

    public void setNailService(NailService nailService) {
        this.nailService = nailService;
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

}