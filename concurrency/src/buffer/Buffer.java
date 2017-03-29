package buffer;

import java.util.ArrayList;

public class Buffer {
	
	private class MyInt {
	
		public int value;
		public int readerQueued;
		public boolean valid;
		
		public MyInt(){
			value=0;
			readerQueued=0;
			valid = false;
		}

	}
	
	private static final int CAPACITY = 10;
	private ArrayList<MyInt> buff;
	
	public Buffer () {
		buff = new ArrayList<MyInt>(CAPACITY);
		
		for (int i = 0; i<CAPACITY; i++){
			buff.add(i, new MyInt());
		}
		
	}
	
///////////////////////////////////////////////////////
	public int read(int pos){
		MyInt elem = buff.get(pos);
		int value;
		boolean updateCounter = false;
		
		synchronized(elem){
			
			while(!elem.valid){
				
				if(!updateCounter){
					elem.readerQueued++;
					updateCounter=true;
				}
				
				try {
					elem.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(updateCounter)
				elem.readerQueued--;
			
			value = elem.value;
		}
		
		return value;
	}
	
///////////////////////////////////////////////////////
	public int get(int pos){
		MyInt elem = buff.get(pos);
		int value;
		
		synchronized(elem){
			
			while(!elem.valid || elem.readerQueued > 0){
				try {
					elem.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			value = elem.value;
			elem.valid=false;

		}
		
		return value;
	}
	
	public void write(int value, int pos){
		MyInt elem = buff.get(pos);
		
		synchronized(elem){
			elem.value=value;
			elem.valid=true;
			elem.notifyAll();
		}
	}
///////////////////////////////////////////////////////
	

}
