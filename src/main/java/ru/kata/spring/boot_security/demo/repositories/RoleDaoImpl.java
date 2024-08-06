package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> findRoleByUserId(User user) {
        String hqlQuery = "select r FROM User u JOIN u.roles r where u.id = :userId";
        Long userId = user.getId();
        List<Role> roles = entityManager
                .createQuery(hqlQuery, Role.class)
                .setParameter("userId", userId)
                .getResultList();
        Set<Role> roleSet = new HashSet<>();
        roleSet.addAll(roles);
        return roleSet;
    }

    @Override
    public Role findRoleByName(String name) {
        Role role = entityManager.createQuery("select r from Role r where r.name = :roleName", Role.class)
                .setParameter("roleName", name)
                .getSingleResult();
        return role;
    }

    @Override
    public Role findById(Long id) {
        Role role = entityManager.createQuery("select r from Role r where r.id = :id", Role.class)
                .setParameter("id", id)
                .getSingleResult();
        return role;
    }
}
