import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.RepoClass;

import java.util.List;

public class Service {

    ///------------------------
    /// ServiceClass.java
    ///------------------------

    @Autowired
    public static RepoClass repo;

    public Service () {
    }

    //public static User addUser(String var1, String var2, String var3, String var4) {
    public static User addUser(String firstName, String lastName, String email, String accountId) {

        User x = new User();
        //x.first = var1;
        //x.last = var2;
        //x.email = var3;
        //x.accountId = var4;
        x.setFirst(firstName);
        x.setLast(lastName);
        x.setEmail(email);
        x.setAccountId(accountId);

        return x;
    }

    public static List<User> saveUsers(List<User> users) {

        for (User u : users) {
            //if (repo.canAccessUser(u.accountId)) {
            if (RepoClass.canAccessUser(u.getAccountId())) {
                repo.saveUser(u);
            }
        }
        //return input
        return users;
    }


    public static Boolean canAccessUsers(String accountId) {
        //return repo.hasUserAccess(accountId);
        return RepoClass.hasUserAccess(accountId);
    }

//move this code to RepoClass
/// ----------------------
/// RepoClass.java
/// ----------------------
//    @Autowired
//    ServiceClass serviceClass;
//
//    public static User saveUser(User u) {
//        Session sess = HibernateUtil.getSession();
//
//        sess.saveOrUpdate(u);
//
//        return u;
//    }
//
//
//    public RepoClass() {
//    }
//
//
//    public static Boolean canAccessUsers(String accountId) {
//        Session sess = HibernateUtil.getSession();
//
//        UserAccess ua = sess.getUserAccess(accountId, SecurityContext.getCurrentUserId());
//
//        if (ua != null && ua.permissions.indexOf("READ") > -1) {
//            return true;
//        }
//
//        return false;
//    }
//
//
//    public List<User> getUsers(String accountId) {
//
//        Session sess = HibernateUtil.getSession();
//
//        return sess.query("SELECT User u where u.accountId = " + accountId, User);
//    }
//
//
//    public List<Users> saveUsers(List<Users> users) {
//
//        Session sess = HibernateUtil.getSession();
//
//        for (User u : users) {
//            if (serviceClass.canAccessUsers(u.accountId)) {
//                sess.saveOrUpdate(u);
//            }
//        }
//
//        return users;
//    }
}
