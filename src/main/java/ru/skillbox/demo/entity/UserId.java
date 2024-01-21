package ru.skillbox.demo.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserId implements Serializable {
    private Long sourceUserId;
    private Long targetUserId;

    public UserId() {}
    public UserId(Long sourceUserId, Long targetUserId) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
    }

    public Long getSourceUserId() {
        return sourceUserId;
    }
    public void setSourceUserId(Long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
    public Long getTargetUserId() {
        return targetUserId;
    }
    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}