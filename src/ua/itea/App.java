package ua.itea;

import java.util.ArrayList;

/**
 * Multithreading: Variant_9
 * 
 * @author Polischuk
 *
 */
public class App {
    static int min_time = 5;
    static int max_time = 500;
    static int processAmount = 50;
    static int max_size;
    static volatile int finish;

    public static void main(String[] args) {
	CPUQueue queue = new CPUQueue();

	int handling = generate();
	System.out.println("Handling = " + handling);
	CPU processor1 = new CPU(handling);
	CPU processor2 = new CPU(handling);

	int genereting = generate();
	System.out.println("Generated = " + genereting);
	CPUProcess process = new CPUProcess(processAmount, genereting);
	Process[] p = process.getProcesses();
	
	ArrayList<Thread> cpu = new ArrayList<>();
	
	Thread controller = new Thread(new Runnable() {
	    public void run() {
		int index = 0;
		new Thread(process).start();
		while (index < processAmount) {
		    if (process.getSize() > index) {
			System.out.print("Process_" + (index + 1) + " is ");
			if (processor1.isFree()) {
			    cpu.add(processor1.handle(p[index]));
			    System.out.println("handled by Processor_1...");
			} else if (processor2.isFree()) {
			    cpu.add(processor2.handle(p[index]));
			    System.out.println("handled by Processor_2...");
			} else {
			    queue.push(p[index]);
			    System.out.println("put into queue...");
			} 
			index++;
		    }
		}		
	    }
	});
	
	Thread check = new Thread(new Runnable() {
	    public void run() {
		while (processor1.handled() + processor2.handled() < processAmount) {
		    if (queue.getSize() > max_size)
			max_size = queue.getSize();
		    if (processor1.isFree() && queue.getSize() > 0) {
			cpu.add(processor1.handle(queue.pop()));
			System.out.println("  From Queue is handled by Processor_1.");
		    }
		    if (processor2.isFree() && queue.getSize() > 0) {
			cpu.add(processor2.handle(queue.pop()));
			System.out.println("  From Queue is handled by Processor_2.");
		    }
		}
	    }
	});

	check.start();
	controller.start();
	
	try {
	    controller.join();
	    check.join();
	    for (Thread pr : cpu) pr.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println("VARIANT_9. Max_size of queue was: " + max_size);

    }

    public static int generate() {
	return (int) (Math.random() * (max_time - min_time + 1) + min_time);
    }

}
