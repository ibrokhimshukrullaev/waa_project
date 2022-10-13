package edu.miu.backend.repository;

import edu.miu.backend.entity.JobAdvertisement;
import edu.miu.backend.entity.JobApplication;
import edu.miu.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
    Optional<JobApplication> findByJobAdvertisementAndStudent(JobAdvertisement jobAdvertisement, Student student);

    Optional<List<JobApplication>> findByJobAdvertisement(JobAdvertisement jobAdvertisement);

    Optional<List<JobApplication>> findByStudent(Student student);

}
