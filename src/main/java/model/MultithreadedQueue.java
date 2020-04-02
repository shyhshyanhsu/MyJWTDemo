package model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    MultithreadedQueue is a shared resource that will be accessed/modified concurrently
    by multiple external components/services.  In this example 'List<User> users' is
    the shared resource that needs to be protected.  getUsers() and addUser(User user)
    are the public methods that are used by external components/services to access/modify
    the shared resource.  getUsers() and addUser(User user) are very small so we can
    synchronize whole method, otherwise we can synchronize only the block of code around
    the shared resource for example we can make up
 */

@Component
public class MultithreadedQueue {
    private List<User> users = new ArrayList<User>();

    public MultithreadedQueue() {
    }

    public synchronized List<User> getUsers() {
        return users;
    }

    public synchronized List<User> addUser(User user) {
        users.add(user);
        return users;
    }

    public void bigMethod() {

        //many lines of code
        synchronized(this)
        {
            //access shared resource
            if (this.users.isEmpty()) {
                //do something
            }
        }
        //many lines of code
    }
}
