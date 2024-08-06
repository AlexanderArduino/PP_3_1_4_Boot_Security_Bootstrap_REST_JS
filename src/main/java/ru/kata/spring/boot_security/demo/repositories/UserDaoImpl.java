package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

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
        String hqlQuery = "update User set name = :newName, surname = :newSurname, email = :newEmail, username = :newUsername, password = :newPassword, roles = :newRoles";
        entityManager.createQuery(hqlQuery)
                .setParameter("newName", user.getName())
                .setParameter("newSurname", user.getSurname())
                .setParameter("newEmail", user.getEmail())
                .setParameter("newUsername", user.getUsername())
                .setParameter("newPassword", bCryptPasswordEncoder.encode(user.getPassword()))
                .setParameter("newRoles", roleDao.findRoleByUserId(user))
                .executeUpdate();
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
        return null;
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
