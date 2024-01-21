package ru.skillbox.demo.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Subscription {
    @EmbeddedId
    private UserId userId;

    public Subscription() {}
    public Subscription(UserId userId) {
        this.userId = userId;
    }
}