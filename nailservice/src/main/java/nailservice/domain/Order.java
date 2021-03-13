package nailservice.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date date;
    private Time time;
    private Customer customer;
    private NailService nailService;

    public Order() {
    }

    public Order(Date date, Time time, Customer customer, NailService nailService) {
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.nailService = nailService;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nailService == null) ? 0 : nailService.hashCode());
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
        Order other = (Order) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nailService == null) {
            if (other.nailService != null)
                return false;
        } else if (!nailService.equals(other.nailService))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", date=" + date + ", time=" + time + ", customer=" + customer.getName()
                + ", nailService=" + nailService.getName() + "]";
    }

}