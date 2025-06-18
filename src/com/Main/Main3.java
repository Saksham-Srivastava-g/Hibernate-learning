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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.Entity.PersistenceUnitInfoImpl;
import com.Entity.Student;

public class Main3 {
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
          CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
          CriteriaQuery<Student> cq = criteriaBuilder.createQuery(Student.class);
          Root<Student> from = cq.from(Student.class);
          CriteriaQuery<Student> select = cq.select(from);
          TypedQuery<Student> query = em.createQuery(cq);
          List<Student> resultList = query.getResultList();
          for (Student student : resultList) {
            System.out.println(student);
          }
        //   CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        //   CriteriaQuery<Student> cq = criteriaBuilder.createQuery(Student.class);
        //   Root<Student> root= cq.from(Student.class);
        //   Predicate p1 = criteriaBuilder.like(root.get("ename"),"b%");
        //   Predicate p2 = criteriaBuilder.greaterThan(root.get("eid"), 19);
        //   cq.select(root).where(criteriaBuilder.and(p1,p2));
        //   TypedQuery<Student> query = em.createQuery(cq);
        //   List<Student> resultList = query.getResultList();
        //   for (Student student : resultList) {
        //     System.out.println(student);
        //   }
        }

/**
 * @param em
 */
public static void update(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<Student> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Student.class);
        Root<Student> root = criteriaUpdate.from(Student.class);
        criteriaUpdate.set("ename","doreamon");
        criteriaUpdate.where(criteriaBuilder.lessThan(root.get("eid"), 25));
        Query query = em.createQuery(criteriaUpdate);
        query.executeUpdate();
        trs.commit();
        System.out.println("Data updated");
}
 public static void delete(EntityManager em){
        EntityTransaction trs = em.getTransaction();
        trs.begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<Student> criteriaDelete = criteriaBuilder.createCriteriaDelete(Student.class);
        Root<Student> root = criteriaDelete.from(Student.class);
        CriteriaDelete<Student> where = criteriaDelete.where(criteriaBuilder.lessThan(root.get("eid"), 17));
        Query query = em.createQuery(criteriaDelete);
        query.executeUpdate();
        trs.commit();
        System.out.println("Data deleted");
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
