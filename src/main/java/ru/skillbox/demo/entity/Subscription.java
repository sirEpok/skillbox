package ru.skillbox.demo.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subscription", schema = "users_scheme")
public class Subscription {
    @EmbeddedId
    private UserId userId;

    public Subscription() {}
    public Subscription(UserId userId) {
        this.userId = userId;
    }
}