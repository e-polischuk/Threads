package homework;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer extends Thread {
    private Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private int capacity = 5; 
    private Lock queueLock = new ReentrantLock();
    private Condition isFull = queueLock.newCondition();
    private Condition isEmpty = queueLock.newCondition();
    
    private int amountProcess = 10;
    private volatile int process;
    private int consumID;
    
    public static void main(String[] args) {
	ProducerConsumer pc = new ProducerConsumer();
	
	Thread producer = pc.newProducer();
	Thread consumer1 = pc.newConsumer();
	Thread consumer2 = pc.newConsumer();
	
	producer.start();
	consumer1.start();
	consumer2.start();
	
	try {
	    producer.join();
	    consumer1.join();
	    consumer2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
	
	

    }
    
    public void produce(int id) {
	queueLock.lock();
	try {
	    while(queue.size() >= capacity) isFull.await();
	    queue.offer((int) (Math.random() * 5 + 5));
	    System.out.println("\nProducer put Process_" + id + " to queue: " + queue);
	    isEmpty.signalAll();
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    queueLock.unlock();
	}
    }
    
    public void consume(int consumerID) {
	queueLock.lock();
	try {
	    while(queue.isEmpty()) isEmpty.await();
	    printFact(queue.poll(), consumerID);
	    isFull.signalAll();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    queueLock.unlock();
	}
    }
    
    public Thread newProducer() {
	Thread producer = new Thread(new Runnable() {
	    public void run() {
		int processes = amountProcess;
		while(processes-- > 0) {
		    produce(amountProcess - processes);
		}
	    }
	});
	
	return producer;
    }
    
    public Thread newConsumer() {
	Thread consumer = new Thread(new Runnable() {
	    int id = ++consumID;
	    public void run() {
		while(process < amountProcess - 1) {
		    consume(id);
		}  
	    }
	});
	
	return consumer;
    }
    
    public void printFact(int number, int id) {
	queueLock.lock();
	try {
	    int factor = 1;
	    int i = 1;
	    while(++i <= number) {
		factor *= i;
		Thread.sleep(100);
	    }
	    System.out.print("\nConsumer_" + id + " handled Process_" + ++process + ": ");
	    System.out.println(number + "! = " + factor);
	} catch (InterruptedException e) {
		e.printStackTrace();
	} finally {
	    queueLock.unlock();
	}
	
    }

}
