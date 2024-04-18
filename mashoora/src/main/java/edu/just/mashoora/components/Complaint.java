package edu.just.mashoora.components;

import edu.just.mashoora.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String subject;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @Column
    private boolean isReviewed;

    @ManyToMany(mappedBy = "complaints")
    private Set<User> users = new HashSet<>();

}
