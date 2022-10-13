package edu.miu.backend.repository;

import edu.miu.backend.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Subscriber deleteBySubscriberId(int id);
    Subscriber findBySubscriberId(int id);
}
