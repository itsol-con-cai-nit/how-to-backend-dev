package repository;

import entity.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class StudentDao {

    Logger logger = Logger.getLogger(StudentDao.class);

    public List<Student> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student").list();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public Student findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("select s from Student s where s.id = :p_student_id");
            query.setParameter("p_student_id", id);
            return query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public boolean insert(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean removeStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Student student = session.load(Student.class, id);
            session.delete(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error(e);
        } finally {
            session.close();
        }
        return false;
    }

}
