package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
/**
 * @author Salem Mufarreh
 * This class represents a customer.
 *
 * The class stores the customer's ID, username, password, and email address.
 * **/
@Data
@Table
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;
    public LocalDate bornAt;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<OrderEntity> orders = new HashSet<>();

}
