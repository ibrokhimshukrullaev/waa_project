package edu.miu.backend.repository;

import edu.miu.backend.entity.JobHistory;
import edu.miu.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Integer> {
    List<JobHistory> findByStudent(Student student);
}
