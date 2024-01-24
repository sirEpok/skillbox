package ru.skillbox.demo.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.demo.entity.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
    List<Users> findByLastName(String lastName);
}
