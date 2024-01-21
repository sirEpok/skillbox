package ru.skillbox.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.entity.Subscription;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.entity.UserId;
import ru.skillbox.demo.repository.SubscriptionRepository;
import ru.skillbox.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public UserService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public String createUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("Пользователь %s добавлен в базу с id = %s", savedUser.getLogin(), savedUser.getId());
    }
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public String updateUser(User user, long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User updateUser = new User(
                id,
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday());
        User savedUser = userRepository.save(updateUser);
        return String.format("Пользователь %s успешно сохранен", savedUser.getLastName());
    }
    public String deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return String.format("Пользователь с id = %s успешно удален", id);
    }
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
    public String subscriptionUsers(Long sid, Long tid) {
        if (sid == tid) {
            return "Нельзя подписаться на самого себя";
        } else if (userRepository.existsById(sid) && userRepository.existsById(tid)) {
            UserId uid = new UserId(sid, tid);
            Subscription sub = new Subscription(uid);
            subscriptionRepository.save(sub);
            return "Ваша подписка зарегистрирована";
        } else {
            return "Одного или двух пользователей не существует";
        }
    }
    public String deleteSubscriptionUsers(Long sid, Long tid) {
        UserId uid = new UserId(sid, tid);
        if (subscriptionRepository.existsByUserId(uid)) {
            Subscription sub = new Subscription(uid);
            subscriptionRepository.delete(sub);
            return "Отписка";
        } else {
            return "Такой связи не существует";
        }
    }
}