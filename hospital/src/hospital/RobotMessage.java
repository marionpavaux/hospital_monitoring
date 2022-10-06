package hospital;

import java.util.HashMap;

public class RobotMessage  {
	
	private int sender;
	private String subject;
	private String performative;
	private HashMap <String, Object> content;
	
	public RobotMessage(int sender, String subject, String performative, HashMap <String, Object> content){	//first constructor
		this.sender = sender;
		this.subject = subject;
		this.performative = performative;
		if (content == null)
			this.content = new HashMap<String, Object>();
		else
			this.content = content; 
	}
	public RobotMessage(int sender, String subject, String performative){		//Second constructor
		this(sender,subject,performative,null);
	}
	public void addContent(String key, Object value) {	//Adds an element to the hashmap of content of the message
		content.put(key, value);
	}
	
	//Getters & Setters
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPerformative() {
		return performative;
	}
	public void setPerformative(String performative) {
		this.performative = performative;
	}
	public HashMap<String, Object> getContent() {
		return content;
	}
	public void setContent(HashMap<String, Object> content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "AgentMessage [subject=" + subject + ", performative=" + performative + ", content=" + content
				+ ", sender=" + sender + "]";
	}
	

}