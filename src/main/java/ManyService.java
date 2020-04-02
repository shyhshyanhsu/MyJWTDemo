import model.MultithreadedQueue;
import org.springframework.beans.factory.annotation.Autowired;

public class ManyService {
    @Autowired
    private MultithreadedQueue multithreadedQueue;

    public void doSomething() {
        multithreadedQueue.bigMethod();
    }
}
