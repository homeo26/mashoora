//package edu.just.mashoora.components;
//
//import edu.just.mashoora.models.Role;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Complaint {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank
//    @Size(max = 10000) // Assuming len(word) = 5 characters on average, We could expect around 2,000 words.
//    private String description;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_username", referencedColumnName = "username")
//    private Customer customer;
//
//
//    @ManyToOne
//    @JoinColumn(name = "lawyer_username", referencedColumnName = "username")
//    private Lawyer lawyer;
//
//
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
//}
