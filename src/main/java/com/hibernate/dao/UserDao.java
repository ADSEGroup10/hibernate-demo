package com.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hibernate.model.User;
import com.hibernate.util.HibernateUtil;

/**
 * Data Access Object untuk entity {@link User}.
 *
 * Lapisan ini satu-satunya bagian aplikasi yang berbicara langsung
 * dengan Hibernate, sehingga Servlet tidak perlu tahu detail database.
 */
public class UserDao {

    private final SessionFactory sessionFactory;

    public UserDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Memvalidasi kredensial login.
     *
     * Membaca data tidak membutuhkan transaksi (Hibernate memakai
     * auto-commit untuk operasi baca), jadi di sini cukup Session.
     *
     * @return true jika username ditemukan dan password cocok.
     */
    public boolean validate(String userName, String password) {
        if (userName == null || userName.isBlank() || password == null) {
            return false;
        }

        // try-with-resources menjamin Session selalu ditutup.
        try (Session session = sessionFactory.openSession()) {

            User user = session
                    .createQuery("FROM User WHERE username = :userName", User.class)
                    .setParameter("userName", userName.trim())
                    .setMaxResults(1)
                    .uniqueResult();

            if (user == null) {
                return false;
            }

            // Perbandingan plain-text: HANYA untuk demonstrasi akademik.
            // Di produksi gunakan hashing (bcrypt/Argon2).
            return password.equals(user.getPassword());
        }
    }

    /**
     * Cek apakah username sudah dipakai. Dipakai oleh seeder agar
     * data demo tidak pernah terduplikasi.
     */
    public boolean existsByUsername(String userName) {
        if (userName == null || userName.isBlank()) {
            return false;
        }

        try (Session session = sessionFactory.openSession()) {
            Long count = session
                    .createQuery("SELECT COUNT(u.id) FROM User u WHERE u.username = :userName", Long.class)
                    .setParameter("userName", userName.trim())
                    .uniqueResult();

            return count != null && count > 0;
        }
    }

    /**
     * Menyimpan {@link User} baru. Operasi tulis WAJIB memakai
     * transaksi supaya commit/rollback eksplisit dan aman.
     */
    public void saveUser(User user) {
        if (user == null) {
            return;
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
