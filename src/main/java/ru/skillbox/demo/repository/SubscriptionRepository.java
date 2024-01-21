package ru.skillbox.demo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.Subscription;
import ru.skillbox.demo.entity.UserId;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    boolean existsByUserId(UserId uid);
}
