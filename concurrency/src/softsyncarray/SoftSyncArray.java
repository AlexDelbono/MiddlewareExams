package softsyncarray;


public class SoftSyncArray {
	
	private Object[] array;
	private long[] exp;
	private GarbageCollector g;
	
	public SoftSyncArray(int dim){
		array=new Object[dim];
		exp = new long[dim];
		
		for(int i = 0; i<dim; i++){
			array[i]= null;
			exp[i]=0;
		}
		
		g = new GarbageCollector();
		g.start();
	}
	
	public synchronized void put(Object value, int pos, int lease){
		array[pos]=value;
		exp[pos]=System.currentTimeMillis()+1000*lease;
	}
	
	public synchronized Object get(int pos){
		while(System.currentTimeMillis() > exp[pos]){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array[pos];
	}
	
	private class GarbageCollector extends Thread{
		public void run(){
			while(true){
				synchronized(SoftSyncArray.this){
					for(int i=0; i<exp.length; i++){
						if(System.currentTimeMillis()>exp[i]){
							array[i]=null;
						}
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
