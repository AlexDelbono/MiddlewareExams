package doublebuffer;

public class DoubleBuffer {
	
	private static class Foo{
		public static void calc(int[] a, int[] b){
			//Do stuff
		}
	}
	
	private int[] alpha;
	private int[] beta;
	
	private int indxa;
	private int indxb;
	
	
	public DoubleBuffer(int a, int b){
		alpha = new int[a];
		beta = new int[b];
		
		indxa=0;
		indxb=0;
	}
	
	public void addAlpha(int a){
		synchronized(alpha){
			while(indxa >= alpha.length){
				try {
					alpha.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			alpha[indxa]=a;
			indxa++;
		}
	}
	
	public void addBeta(int b){
		synchronized(beta){
			while(indxb >= beta.length){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			beta[indxb]=b;
			indxb++;
		}
	}
	
	public void clear(){
		synchronized(alpha){
			indxa=0;
			alpha.notifyAll();
		}
		synchronized(beta){
			indxb=0;
			beta.notifyAll();
		}
	}
	
	public void compute(){
		int[] a,b;
		
		synchronized(alpha){
			a = alpha.clone();
		}
		synchronized(beta){
			b = beta.clone();
		}
						
		Foo.calc(a, b);
	}

}
