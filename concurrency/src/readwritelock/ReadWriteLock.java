package readwritelock;

public class ReadWriteLock {
	
	private int readers=0;
	private boolean writer=false;
	private long id=-1;
	
	public synchronized void readLock(){
		while(writer){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		readers++;
		
	}
	
	public synchronized void writeLock(){
		while(writer || readers>0){
			try{
				wait();
			}catch(InterruptedException e){
				//Do something
			}
		}
		id=Thread.currentThread().getId();
		writer=true;
	}
	
	public synchronized void unlock(){
		if(Thread.currentThread().getId() == id){
			writer=false;
			id=-1;
		} else {
			readers--;
		}
		notifyAll();
	}

}
