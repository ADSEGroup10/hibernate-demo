package com.hibernate.test;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.hibernate.util.HibernateUtil;

public class HibernateConnectionTest {

    public static void main(String[] args) {

        SessionFactory factory = null;

        try {
            System.out.println("Initializing Hibernate...");

            factory = HibernateUtil.getSessionFactory();

            try (Session session = factory.openSession()) {

                session.doWork(connection -> {

                    try (Statement statement =
                                 connection.createStatement();

                         ResultSet result =
                                 statement.executeQuery(
                                     "SELECT DATABASE(), VERSION()"
                                 )) {

                        if (result.next()) {
                            System.out.println(
                                "Connected Database: " +
                                result.getString(1)
                            );

                            System.out.println(
                                "MySQL Version: " +
                                result.getString(2)
                            );
                        }
                    }
                });
            }

            System.out.println(
                "HIBERNATE CONNECTION SUCCESS!"
            );

        } catch (Exception e) {

            System.err.println(
                "HIBERNATE CONNECTION FAILED!"
            );

            e.printStackTrace();

        } finally {

            if (factory != null) {
                factory.close();
            }
        }
    }
}
