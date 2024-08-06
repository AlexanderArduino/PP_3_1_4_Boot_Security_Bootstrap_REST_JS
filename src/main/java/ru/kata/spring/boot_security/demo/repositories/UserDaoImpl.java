package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private RoleDAO roleDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setRoleDao(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        System.out.println("DAO update start");
        String hqlQuery = "update User u set u.name = :newName, u.surname = :newSurname, " +
                "u.email = :newEmail, u.username = :newUsername, u.password = :newPassword where u.id = :newId";
        entityManager.createQuery(hqlQuery)
                .setParameter("newName", user.getName())
                .setParameter("newSurname", user.getSurname())
                .setParameter("newEmail", user.getEmail())
                .setParameter("newUsername", user.getUsername())
                .setParameter("newPassword", user.getPassword())
                .setParameter("newId", user.getId())
                .executeUpdate();

        User existingUser = entityManager.find(User.class, user.getId());
            existingUser.setRoles(user.getRoles());
            entityManager.merge(existingUser);

        System.out.println("DAO update end");
    }

    @Override
    public User findById(Long id) {
        User user = entityManager.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
        return user;
    }

    @Override
    public User finByUsername(String username) {
        User user = entityManager.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        return user;
    }

    @Override
    public void deleteUser(String username) {
        entityManager.createQuery("delete User u where u.username = :username", User.class)
                .setParameter("username", username)
                .executeUpdate();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }
}
