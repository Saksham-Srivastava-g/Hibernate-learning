package com.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.Entity.PersistenceUnitInfoImpl;
import com.Entity.Student;

public class Main2 {
    public static void main(String[] args) {
        Map<String, String> map = loadPropertiesAsMap("config.properties");
        if (map == null) return;

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new PersistenceUnitInfoImpl(), map);
        EntityManager em = emf.createEntityManager();
        delete(em);
        read(em);

        em.close();
        emf.close();
    }
   public static void insert(EntityManager em) {
    EntityTransaction ts = em.getTransaction();
    ts.begin();
    
    for (int i = 1; i < 11; i++) {
       Student st = new Student(12+i,"kanpur","superman");
       em.persist(st);
    }
    ts.commit();
    System.out.println("INSERTED");
}


public static void read(EntityManager em){
        String jql ="select e from Student e";
        Query nativeQuery = em.createQuery(jql,Student.class);
        List<Student> resultList = nativeQuery.getResultList();
        for (Student student : resultList) {
            System.out.println(student);
        }
        }

public static void update(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        String sql="update Student set eaddress=:eaddress where eid>=:eid";
        Query nativeQuery = em.createQuery(sql);
        nativeQuery.setParameter("eaddress", "rahul yadava");
        nativeQuery.setParameter("eid", 24);
        nativeQuery.executeUpdate();
        trs.commit();
        System.out.println("Data updated");
}
 public static void delete(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        String jql="delete from Student where eid>=:eid";
        Query nativeQuery = em.createQuery(jql);
        nativeQuery.setParameter("eid" ,26);
        nativeQuery.executeUpdate();
        trs.commit();
        System.out.println("Data updated");
}














    private static Map<String, String> loadPropertiesAsMap(String fileName) {
        Properties properties = new Properties();
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.out.println("file not found");
                return null;
            }
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Map<String, String> map = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.getProperty(key));
        }
        return map;
    }

}
