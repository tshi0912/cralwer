package cf.crawler.vos;

public class ResultVo {
	
	public static final int SUCCESS = 0;
	public static final int FAILED = 1;
	public static final int ERROR = 2;
	
	private int code;
	private Object data;
	
	public ResultVo(){};
	
	public ResultVo(int code){
		setCode(code);
	}
	
	public ResultVo(int code, Object data){
		setCode(code);
		setData(data);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
