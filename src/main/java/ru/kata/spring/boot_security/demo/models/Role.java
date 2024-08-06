package ru.kata.spring.boot_security.demo.models;


import org.hibernate.annotations.GeneratorType;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private  String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
