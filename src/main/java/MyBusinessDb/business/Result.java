package MyBusinessDb.business;

public class Result {

	private int    count = -1;
	private int    code = -1;
	private String status = "";
	
	public void setError(int code)
	{
		this.code = code;
		this.status = "error";
	}
	public void setSuccess()
	{
		this.code = 0;
		this.status = "success";
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
