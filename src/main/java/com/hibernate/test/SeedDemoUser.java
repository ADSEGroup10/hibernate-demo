package com.hibernate.test;

import com.hibernate.dao.UserDao;
import com.hibernate.model.User;

/**
 * Seeder akun demonstrasi.
 *
 * Sengaja TIDAK dijalankan otomatis saat aplikasi start, supaya
 * data tidak pernah terduplikasi. Jalankan manual satu kali:
 *
 *   mvn clean compile org.codehaus.mojo:exec-maven-plugin:3.5.0:java \
 *       "-Dexec.mainClass=com.hibernate.test.SeedDemoUser"
 *
 * Seeder bersifat idempotent: kalau username sudah ada, tidak
 * ada insert baru.
 */
public class SeedDemoUser {

    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_PASSWORD = "demo123";

    public static void main(String[] args) {

        UserDao userDao = new UserDao();

        if (userDao.existsByUsername(DEMO_USERNAME)) {
            System.out.println("Akun demo '" + DEMO_USERNAME + "' sudah ada. Tidak ada data baru.");
            return;
        }

        User user = new User();
        user.setFirstName("Demo");
        user.setLastName("Mahasiswa");
        user.setUsername(DEMO_USERNAME);
        user.setPassword(DEMO_PASSWORD);

        userDao.saveUser(user);

        System.out.println("Akun demo berhasil dibuat:");
        System.out.println("  username = " + DEMO_USERNAME);
        System.out.println("  password = " + DEMO_PASSWORD + "  (plain-text, hanya untuk demo akademik)");
    }
}
