package msgbatcher;

public class Message {
	
	private String s;

	public Message(String s){
		this.s=s;
	}
	
	public void send(){
		System.out.println(s);
	}
}
