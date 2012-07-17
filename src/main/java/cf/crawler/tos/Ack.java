package cf.crawler.tos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ack implements Serializable {
	
	public static final int SUCCESS = 0;
	public static final int FAILED = 1;
	
	private int code;
	
	public Ack(){};
	
	public Ack(int code){
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
