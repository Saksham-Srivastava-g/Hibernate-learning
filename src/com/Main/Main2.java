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
        insert(em);

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
        String sql ="select * from Student";
        Query nativeQuery = em.createNativeQuery(sql);
        List<Object[]> resultList = nativeQuery.getResultList();
        for (Object[] objects : resultList) {
                for (Object o: objects) {
                        System.out.print(o + " ");
                }
         System.out.println();
        }
}
public static void update(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        String sql="update Student set eaddress=? where eid>=?";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter(1, "rahul yadava");
        nativeQuery.setParameter(2, 24);
        nativeQuery.executeUpdate();
        trs.commit();
        System.out.println("Data updated");
}
 public static void delete(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        String sql="delete from Student swhere eid>=?";
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter(1, 26);
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
