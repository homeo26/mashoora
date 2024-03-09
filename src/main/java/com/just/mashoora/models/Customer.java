package com.just.mashoora.models;

import com.just.mashoora.components.Complaint;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Complaint> complaints;


}
