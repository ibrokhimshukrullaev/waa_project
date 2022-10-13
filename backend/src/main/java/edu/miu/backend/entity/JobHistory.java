package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@SQLDelete(sql = "UPDATE job_history SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class JobHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private String reasonToLeave;

    @ManyToMany
    List<Tag> tags;

    @ManyToOne
    @JsonManagedReference
    private Student student;

    private Boolean deleted = Boolean.FALSE;
}
