package com.codecon.backend.model;

import lombok.Getter;

@Getter
public enum Role {
    MASTER(2),
    ADMIN(1),
    USER(0);

    private final int accessLevel;

    Role(int accessLevel) {
        this.accessLevel = accessLevel;
    }

}
