import model.MultithreadedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManyService {
    @Autowired
    private MultithreadedQueue multithreadedQueue;

    public void doSomething() {
        multithreadedQueue.bigMethod();
    }
}
