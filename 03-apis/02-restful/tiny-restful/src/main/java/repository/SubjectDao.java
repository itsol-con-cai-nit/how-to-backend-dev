package repository;

import entity.Subject;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class SubjectDao {

    Logger logger = Logger.getLogger(SubjectDao.class);

    public List<Subject> getAllSubject() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Subject").list();
        } catch (HibernateException e) {
            logger.error(e);
        }
        return null;
    }

}
