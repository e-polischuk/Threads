package ua.itea;

public class CPUProcess implements Runnable {
    private int time;
    private volatile Process[] p;
    private volatile int size;

    public CPUProcess(int process, int time) {
	this.time = time;
	p = new Process[process];
    }

    public void run() {
	for (int i = 0; i < p.length; i++) {
	    p[i] = new Process();
	    size++;
//	    System.out.println("Size = " + size);
	    try {
		Thread.sleep(time);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public Process[] getProcesses() {
	return p;
    }

    public int getSize() {
	return size;
    }
}
