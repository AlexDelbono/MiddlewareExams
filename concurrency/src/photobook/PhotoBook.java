package photobook;

public class PhotoBook {
	private final String NAME;
	private final int CAPACITY;
	private Photo[] photo;
	private String[] caption;
	private int last;
	
	public PhotoBook(String name, int capacity){
		NAME=name;
		CAPACITY=capacity;
		
		photo = new Photo[CAPACITY];
		caption = new String[CAPACITY];
		last=0;
	}
	
	public synchronized int add(Photo p){
		while(last >= CAPACITY){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		photo[last]=p;
		last++;
		return last-1;
	}
	
	public synchronized void setCaption(int slot, String caption){
		this.caption[slot]=caption;
	}
	
	public synchronized void upload(SharingSvc svc){
		Thread t = new Thread() {public void run(){svc.upload(NAME, photo, caption);}};
		
		t.start();
		
		photo = new Photo[CAPACITY];
		caption = new String[CAPACITY];
		last=0;
		
		notifyAll();
	}
	
	
}
