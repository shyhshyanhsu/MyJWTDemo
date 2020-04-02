package repository;

import model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("repo")
public class RepoClass {

    /// ----------------------
    /// RepoClass.java
    /// ----------------------

    //circular dependencies - even you are able to resolve
    //it is very bad design to expose Service to Repository
    //@Autowired
    //Service serviceClass;

    public static User saveUser(User u) {
        //I cannot find HibernateUtil in org.hibernate
        //so google it to find one
        Session sess = HibernateUtil.getSession();

        sess.saveOrUpdate(u);

        return u;
    }


    public RepoClass() {
    }

    public static Boolean canAccessUser(String accountId) {
    //public static Boolean canAccessUsers(String accountId) {
        Session sess = HibernateUtil.getSession();

        //hibernate.Session does not have getUserAccess
        //we can implement our own logic by checking if accountId exists in USER table
        //if so returns true otherwise false

        //UserAccess ua = sess.getUserAccess(accountId, SecurityContext.getCurrentUserId());

        //if (ua != null && ua.permissions.indexOf("READ") > -1) {
        //    return true;
        //}

        //if accountId exists in USER table returns true otherwise false
        boolean exist = true;
        if (exist) {
            return false;
        }

        return false;
    }

    //new impl being used by Service
    public static Boolean hasUserAccess(String accountId) {
        return canAccessUser(accountId);
    }

    public List<User> getUsers(String accountId) {

        Session sess = HibernateUtil.getSession();

        //return sess.query("SELECT User u where u.accountId = " + accountId, User);
        return sess.createFilter(User.class, "SELECT User u where u.accountId = " + accountId).list();
    }


    //public List<Users> saveUsers(List<Users> users) {
    public List<User> saveUsers(List<User> users) {

        Session sess = HibernateUtil.getSession();

        for (User u : users) {
            //it is very bad design to expose Service to Repository
            //Re-design
            //if (serviceClass.canAccessUsers(u.accountId)) {
            if (canAccessUser(u.getAccountId())) {
                sess.saveOrUpdate(u);
            }
        }

        return users;
    }
}
