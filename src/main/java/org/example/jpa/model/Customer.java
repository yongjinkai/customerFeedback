package org.example.jpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name="customer",uniqueConstraints = {@UniqueConstraint(name ="email", columnNames = "email")})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    @NotBlank(message = "Name cannot be blank")
    @Size(min=3,message="Name must be at least 3 characters")
    @Size(max=255,message="Name must not exceed 255 characters")
    private String name;

    @Column (nullable = false,unique = true)
    @Email(regexp = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message="Invalid email")
    private String email;

    @Column
    @Pattern(regexp = "^\\d{8}$", message = "Phone number must be 8-digits only.")
    private String phone;

    public Customer() {     // create a new empty instance of Customer
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
