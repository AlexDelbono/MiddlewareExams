package periodicrunner;

public class PeriodicRunner {
	
	private Thread t;
	private boolean stop;
	private int milli;
	
	public PeriodicRunner(Runnable r, int ms){
		this.milli=ms;
		
		t= new Thread() {
			public void run(){
				boolean exit = false;
				while(!exit){
					try {
						Thread.sleep(milli);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r.run();
					synchronized(PeriodicRunner.this){
						exit=stop;
					}
				}
			}
		};
		
		stop=false;
	}
	
	public synchronized void start(){
		t.start();
	}
	
	public synchronized void stop(){
		stop=true;
	}

}
