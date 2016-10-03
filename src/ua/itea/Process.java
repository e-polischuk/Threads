package ua.itea;

public class Process extends Thread {
    
    public void run() {
	waiting(true);
    }

    public synchronized void waiting(boolean isRun) {
	if (isRun)
	    try {
		wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    };
	notify();
    }
}
