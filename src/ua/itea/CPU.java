package ua.itea;

public class CPU implements Runnable {
    private int time;
    private volatile boolean isFree;
    private Process process;
    private volatile int handled;

    public CPU(int time) {
	this.time = time;
	isFree = true;
	
    }

    public Thread handle(Process process) {
	this.process = process;
	Thread cpu = new Thread(this);
	cpu.start();
	return cpu;
    }

    public void run() {
	isFree = false;
//	System.out.println("start");
	try {
	    Thread.sleep(time);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	process.waiting(false);
//	System.out.println("finish");
	handled++;
	isFree = true;
    }

    public boolean isFree() {
	return isFree;
    }
    
    public int handled() {
	return handled;
    }

}
