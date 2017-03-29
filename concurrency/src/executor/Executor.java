package executor;

public class Executor {
	private Thread t;
	private boolean stop=false;
	
	public Executor(Runnable r){
		t= new Thread(){public void run(){
			boolean flag=false;
			
			while(!flag){
				r.run();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized(Executor.this){
					flag=stop;
				}
			}
		}};
		
		t.start();
	}
	
	public synchronized void stop(){
		stop=true;
	}
}
