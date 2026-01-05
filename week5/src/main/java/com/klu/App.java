package com.klu;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class App {
    public static void main(String[] args) {

        /* STEP 1: INSERT (RUN ONLY ONCE) */
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(new Product("Laptop", "Electronics", 75000, 10));
        session.save(new Product("Mouse", "Electronics", 800, 50));
        session.save(new Product("Keyboard", "Electronics", 2500, 30));
        session.save(new Product("Chair", "Furniture", 4500, 20));
        session.save(new Product("Table", "Furniture", 9000, 5));
        session.save(new Product("Pen", "Stationery", 20, 200));
        session.save(new Product("Notebook", "Stationery", 80, 150));

        tx.commit();
        session.close();

        /* STEP 2: SORTING */
        session = HibernateUtil.getSessionFactory().openSession();

        List<Product> asc = session.createQuery(
                "FROM Product ORDER BY price ASC", Product.class).list();

        List<Product> desc = session.createQuery(
                "FROM Product ORDER BY price DESC", Product.class).list();

        /* STEP 3: PAGINATION */
        Query<Product> page1 = session.createQuery("FROM Product", Product.class);
        page1.setFirstResult(0);
        page1.setMaxResults(3);

        Query<Product> page2 = session.createQuery("FROM Product", Product.class);
        page2.setFirstResult(3);
        page2.setMaxResults(3);

        /* STEP 4: AGGREGATES */
        Long count = session.createQuery(
                "SELECT COUNT(p) FROM Product p", Long.class).uniqueResult();

        Object[] minMax = session.createQuery(
                "SELECT MIN(p.price), MAX(p.price) FROM Product p",
                Object[].class).uniqueResult();

        /* STEP 5: LIKE */
        session.createQuery(
                "FROM Product WHERE name LIKE 'L%'", Product.class)
                .list().forEach(p -> System.out.println(p.getName()));

        session.close();
    }
}
