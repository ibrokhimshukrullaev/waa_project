package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Where(clause = "deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    // @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoggedIn;

    private Boolean active = Boolean.TRUE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Address address;

    @OneToMany(mappedBy = "createdBy")
    @JsonBackReference
    private List<JobAdvertisement> jobAdvertisements;

    private Boolean deleted = Boolean.FALSE;
}
