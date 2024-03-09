package com.just.mashoora.components;

import com.just.mashoora.models.Customer;
import com.just.mashoora.models.Lawyer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String complaintNote;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "lawyer_id")
    private Lawyer lawyer;

}
