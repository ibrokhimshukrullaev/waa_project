package edu.miu.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Data
@SQLDelete(sql = "UPDATE faculty SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Faculty extends User{
    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "faculty")
    @JsonBackReference
    List<Comment> comments;

    private Boolean deleted = Boolean.FALSE;
}
