package ua.itea;

import java.util.*;

public class CPUQueue {
    public LinkedList<Process> processes;

    public CPUQueue() {
	processes = new LinkedList<Process>();
    }

    public void push(Process process) {
	processes.addLast(process);
    }

    public Process pop() {
	return processes.removeFirst();
    }

    public int getSize() {
	return processes.size();
    }
}
