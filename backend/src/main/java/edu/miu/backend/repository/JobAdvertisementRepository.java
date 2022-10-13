package edu.miu.backend.repository;

import edu.miu.backend.entity.JobAdvertisement;
import edu.miu.backend.entity.Student;
import edu.miu.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Integer> {

    List<JobAdvertisement> findByCreatedBy(User user);

    @Query("select j from JobAdvertisement j where j.createdBy <> ?1")
    List<JobAdvertisement> findByCreatedByNot(Student student);

    @Query("select j from JobAdvertisement j where j.createdBy = ?1")
    List<JobAdvertisement> findByCreatedBy(Student student);

    @Query(value = "select ad, count(a.id) as co from JobApplication a left join a.jobAdvertisement as ad group by ad order by co desc")
    List<JobAdvertisement> findTopAll();
}
