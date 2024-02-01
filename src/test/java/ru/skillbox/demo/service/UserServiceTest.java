package ru.skillbox.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import ru.skillbox.demo.entity.Subscription;
import ru.skillbox.demo.entity.UserId;
import ru.skillbox.demo.entity.Users;
import ru.skillbox.demo.repository.SubscriptionRepository;
import ru.skillbox.demo.repository.UserRepository;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    void createUserSuccess() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        Users user = new Users("test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users savedUser = new Users(1L, "test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository, subscriptionRepository);

        String result = userService.createUser(user);

        assertEquals("Пользователь epok добавлен в базу с id = 1", result);
    }

    @Test
    void createUserError() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        Users user = new Users("test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users savedUser = new Users(1L, "test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Mockito.when(userRepository.save(user)).thenThrow(PersistenceException.class);
        UserService userService = new UserService(userRepository, subscriptionRepository);

        Executable executable = () -> userService.createUser(user);

        assertThrows(PersistenceException.class, executable);
    }

    @Test
    void getUser() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        Users user = new Users("test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users savedUser = new Users(1L, "test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));
        UserService userService = new UserService(userRepository, subscriptionRepository);

        String result = String.valueOf(userService.getUser(1L).getEmail());

        assertEquals("test@test.ru", result);
    }

    @Test
    void updateUser() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Users user = new Users("test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users savedUser = new Users(1L, "test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users updateUser = new Users(1L, "test1@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Mockito.when(userRepository.save(user)).thenReturn(updateUser);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        UserService userService = Mockito.mock(UserService.class);

        Boolean result = userService.updateUser(updateUser, 1L);

        assertEquals(false, result);
    }

    @Test
    void getUsers() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);
        Users user = new Users("test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        Users savedUser = new Users(1L, "test@test.ru", "epok", "password", "Anton", "Bazhin", LocalDate.of(2012, 12,12), "Perm");
        ArrayList<Users> list = new ArrayList();
        list.add(savedUser);
        Mockito.when(userRepository.findAll()).thenReturn(list);
        UserService userService = new UserService(userRepository, subscriptionRepository);

        String result = userService.getUsers().get(0).getLogin();

        assertEquals("epok", result);
    }

    @Test
    void subscriptionUsersOnYourself() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);

        UserId uid = new UserId(1L, 1L);
        Subscription sub = new Subscription(uid);
        Mockito.when(subscriptionRepository.save(sub)).thenReturn(sub);
        UserService userService = new UserService(userRepository, subscriptionRepository);

        String result = userService.subscriptionUsers(1L, 1L);

        assertEquals("Нельзя подписаться на самого себя", result);
    }

    @Test
    void subscriptionUsersNonExistUser() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        SubscriptionRepository subscriptionRepository = Mockito.mock(SubscriptionRepository.class);

        UserId uid = new UserId(1L, 5L);
        Subscription sub = new Subscription(uid);
        Mockito.when(subscriptionRepository.save(sub)).thenReturn(sub);
        UserService userService = new UserService(userRepository, subscriptionRepository);

        String result = userService.subscriptionUsers(1L, 5L);

        assertEquals("Одного или двух пользователей не существует", result);
    }
}