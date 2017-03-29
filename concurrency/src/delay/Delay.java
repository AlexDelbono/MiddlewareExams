package delay;

import java.util.ArrayList;

public class Delay {
	
	private static class MyRunnable{
		Runnable job;
		long time;
		
		public MyRunnable(Runnable job, long time){
			this.job=job;
			this.time=time;
		}
		
		public boolean run(){
			if(System.currentTimeMillis()>=time){
				job.run();
				return true;
			}
			return false;
		}
	}
	
	private static ArrayList<MyRunnable> list = new ArrayList<MyRunnable>();
	private static boolean started=false;
	private static Thread t = new Thread(){
		public void run(){
			while(true){
				synchronized(list){
					for(MyRunnable r : list){
						if(r.run()){
							list.remove(r);
						}
					}
				}
			}
		}
	};
	
	public static void delay(Runnable job, int delay){
		synchronized(list){
			list.add(new MyRunnable(job, System.currentTimeMillis() + delay*1000));
		}
			
		if(!started){
			t.start();
			started=true;
		}
	}
}
