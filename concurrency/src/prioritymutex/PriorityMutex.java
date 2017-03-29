package prioritymutex;

import java.util.PriorityQueue;

public class PriorityMutex {

	private PriorityQueue<Integer> queue;
	private boolean locked;
	
	
	public PriorityMutex(){
		queue= new PriorityQueue<Integer>();
		locked = false;
	}
	
	public void lock(int priority){
		Integer h = new Integer(priority);
		
		synchronized(this){
			if(!locked){
				locked=true;
				return;
			}
			
			queue.add(h);
		}
		
		synchronized(h){
			try {
				h.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public synchronized void unlock(){
		locked=false;
		wakeTheNext();
	}
	
	public synchronized boolean isLocked(){
		if(locked) 
			return true;
		locked=true;
		return false;
	}
	
	private void wakeTheNext(){
		Integer h = queue.poll();
		
		if(h != null){
			h.notify();
		}
	}
	
}
