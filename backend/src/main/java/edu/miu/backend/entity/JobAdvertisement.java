package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE job_advertisement SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class JobAdvertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;

    private String description;

    private String benefits;

    private String state;

    private String city;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private Student createdBy;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;

    private String file;

    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
