package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String state;
    private String city;
    private String zip;

    @OneToMany(mappedBy ="address")
    @JsonManagedReference
    private List<User> user;

    private boolean deleted = Boolean.FALSE;
}
