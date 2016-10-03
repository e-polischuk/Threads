package state;

public class States implements Runnable {

    volatile boolean mark = true;

    public static void main(String[] args) {
	States test = new States();
	Thread tested = new Thread(test, "Tested");
	Thread assist = new Thread(new Runnable() {
	    public void run() {
		test.block();
		try {
		    Thread.sleep(300);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		test.waiting();
				
	    }
	}, "Assist");	
		
	System.out.println(tested.getState());		
	tested.start();
	System.out.println(tested.getState());
	assist.start();
	try {
	    Thread.sleep(50);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println(tested.getState());
	try {
	    Thread.sleep(200);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println(tested.getState());
	try {
	    Thread.sleep(200);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println(tested.getState());
	try {
	    Thread.sleep(100);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println(tested.getState());
    }

    synchronized void block() {
	reverse();
	try {
	    Thread.sleep(100);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    synchronized void waiting() {
	reverse();
	if (!isTrue())
	    try {
		wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	notify();
    }

    synchronized void waitTime() {
	reverse();
	try {
	    wait(100);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public void run() {
	while (mark);
	block();
	waiting();
	waitTime();
	reverse();
    }

    public boolean isTrue() {
	return mark;
    }

    public void reverse() {
	mark = !mark;
    }
    
}
