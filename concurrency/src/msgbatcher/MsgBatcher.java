package msgbatcher;

public class MsgBatcher {
	
	private class MyThread extends Thread{
		
		public Message m;
		public boolean go=false;
		public boolean stop=false;
		
		@Override
		public synchronized void run(){
			while(!stop){
				while(!go){
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				m.send();
				go=false;
			}
		}
	}
	
	
	private MyThread sender;
	private Message[] q;
	private int tail;
	private final int CAPACITY;
	
	public MsgBatcher(int capacity){		
		sender = new MyThread();
		sender.start();
		
		CAPACITY = capacity;
		q= new Message[CAPACITY];
		
		tail=0;
	}
	
	public synchronized void enqueue(Message m){
		while(tail>=CAPACITY){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		q[tail]=m;
		tail++;
	}
	
	public synchronized void sendAll(){
		for(int i=0; i<tail; i++){
			synchronized(sender){
				sender.go=true;
				sender.m=q[i];
				sender.notify();
			}
		}
		tail = 0;
		notifyAll();
	}

}
