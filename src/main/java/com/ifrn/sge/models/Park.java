package com.ifrn.sge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Entity
public class Park implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @NotBlank
    private String id;

    @OneToOne
    private User client;

    public Park() {
    }

    public Park(String id, User client) {
        this.id = id;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", client=" + client +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
