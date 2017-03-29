package syncstrintmap;

import java.util.HashMap;
import java.util.Map;

public class SyncStrIntMap {

	private Map<String, Integer> m;
	
	public SyncStrIntMap(Map<String, Integer> m){
		this.m=new HashMap<String,Integer>(m);
	}
	
	public synchronized Integer get(String s){
		while(!m.containsKey(s))
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return m.get(s);
	}
	
	public synchronized void put(String s, Integer i){
		while(m.containsKey(s))
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		m.put(s, i);
		notifyAll();
	}
	
	public synchronized Integer remove(String s){
		while(!m.containsKey(s))
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Integer i = m.remove(s);
		notifyAll();
		return i;
	}
	
	public synchronized boolean containsKey(String s){
		return m.containsKey(s);
	}
	
	public synchronized int size(){
		return m.size();
	}
}
