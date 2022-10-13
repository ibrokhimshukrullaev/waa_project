package edu.miu.backend.repository;

import edu.miu.backend.entity.Comment;
import edu.miu.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select sc from Comment sc where sc.student = ?1")
    List<Comment> findByStudent(Student student);
}
