package mutex;

public class Mutex {
	
	private long id=-1;
	private boolean locked=false;
	
	public synchronized void lock(){
		while(locked){
			try{
				this.wait();
			} catch (Exception e){
				//Do stuff
			}
		}
		
		locked=true;
		id = Thread.currentThread().getId();
	}
	
	public synchronized void unlock(){
		if(locked && id == Thread.currentThread().getId()){
			locked = false;
			notify();
		}
	}
	
	public synchronized boolean tryLock(){
		if(locked){
			return false;
		}
		locked=true;
		id=Thread.currentThread().getId();
		return true;
		
	}

}
