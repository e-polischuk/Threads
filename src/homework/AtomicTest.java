package homework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
	int task = 10;
	
	AtomicInteger superSum = new AtomicInteger();
	
	int[] nums = new int[30];
	for(int i = 0; i < 30; i++) {
	    nums[i] = (int) (Math.random() * 10) * 5;
	}
	
	Future<Integer>[] future = new Future[task];
	
	ExecutorService es = Executors.newFixedThreadPool(10);
	
	for(int i = 0; i < task; i++) {
	    future[i] = es.submit(new Callable<Integer>() {
		public Integer call() throws Exception {
		    int sum = 0;
		    for(int i : nums) {
			sum += i;
		    }
		    return superSum.addAndGet(sum);
		}
	    });
	}
	
	for(int i = 0; i < task; i++) {
	    System.out.println("Thread_" + i + ":   " + future[i].get());
	}
	
	es.shutdown();
    }

}
