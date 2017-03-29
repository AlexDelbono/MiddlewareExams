package mapper;

public class Mapper {
	
	private class MyThread extends Thread {
		
		public boolean go = false;
		public boolean stop = false;
		
		public int ini, fin;
		public int[] data;
		public Processor p;
		
		@Override
		public synchronized void run(){
			while(!stop){
				while(!go){
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for (int i=ini; i<fin; i++){
					data[i] = p.process(data[i]);
				}
				
				go = false;
			}
		}
	}
	
	private final int NTHREADS;
	private MyThread pool[];
	
	public Mapper(int numbOfThreads) {
		NTHREADS = numbOfThreads;
		pool = new MyThread[NTHREADS];
		
		for(int i = 0; i< numbOfThreads; i++){
			pool[i]=new MyThread();
			pool[i].start();
		}
	}
	
	public void map(int[] data, Processor p){
		float step = ((float)data.length) / NTHREADS;
		
		for(int i = 0, count=0; i<NTHREADS; i++){
			synchronized(pool[i]){
				pool[i].p=p;
				pool[i].data=data;
				pool[i].ini=count;
				count= (int)(step+count);
				pool[i].fin = i == NTHREADS-1 ? data.length : count;
				pool[i].go = true;
				pool[i].notify();
			}
		}
		
		for(int i =0; i<NTHREADS; i++){
			synchronized(pool[i]){
				System.out.println("Thread " + i + " finished.");
			}
		}
	}

	public static void main(String[] args) {
		Mapper m = new Mapper(20);
		
		int[] data = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		
		ProcessorImp p = new ProcessorImp();

		m.map(data, p);
		
		for(int i =0; i<data.length; i++){
			System.out.print(data[i] + "   ");
		}
		System.out.print("\n");
	}

}
