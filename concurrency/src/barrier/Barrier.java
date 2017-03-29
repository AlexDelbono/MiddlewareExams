package barrier;

public class Barrier {
	
	private final int N;
	private int count;
	private boolean abort;
	
	public Barrier(int n){
		N=n;
		count = 0;
		abort = false;
	}
	
	
	public synchronized void await() throws InterruptedException{
		count++;
		
		if(count >= N)
			notifyAll();
		
		while(!abort && count < N) 
			wait();
		
		if(abort)
			throw new InterruptedException();
	}
	
	public synchronized void interrupt(){
		abort=true;
		notifyAll();
	}
	
}
