package repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//I cannot find HibernateUtil in org.hibernate
//so google it to find one
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static String status = "-";

    static
    {
        try
        {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {   status = "erro na inicializacao Hibernate: " + e.getMessage();  }
    }

    public static Session getSession()
            throws HibernateException {  return sessionFactory.openSession();  }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static Session getCurrentSession() throws HibernateException { return sessionFactory.getCurrentSession(); }
}