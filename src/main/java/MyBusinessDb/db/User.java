package MyBusinessDb.db;

import java.util.Calendar;

public class User {
	private String  user_num;
	private int     remain_count;
	private String  pw;
	private String  modify_date; // like 2019-05-18

	public String getModify_date() {
		return modify_date;
	}

	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}

	public User()
	{
		user_num = "";
		remain_count = 0;
		pw = "";
	}
 
	public User(String user_num, int remain_count, String pw) {
		super();
		this.user_num = user_num;
		this.remain_count = remain_count;
		this.pw = pw;
	}

	public String getUser_num() {
		return user_num;
	}

	public void setUser_num(String user_num)
	{
		this.user_num = user_num;
	}


	public int getRemain_count() {
		return remain_count;
	}


	public void setRemain_count(int remain_count) {
		this.remain_count = remain_count;
	}


	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public boolean isRootUser()
	{
		if (!user_num.isEmpty() && !Character.isDigit(user_num.charAt(0)))
		{
            return true;
        }
		else
		{
			return false;
		}
	}

	@Override
	public String toString() {
		return "user [num=" + user_num + ", remain_count=" + remain_count + "]";
	}
}