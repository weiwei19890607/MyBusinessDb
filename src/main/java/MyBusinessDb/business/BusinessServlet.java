package MyBusinessDb.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import MyBusinessDb.db.MyBatisUtil;
import MyBusinessDb.db.User;
import MyBusinessDb.db.UserMapper;

/**
 * Servlet implementation class BusinessServlet
 */
@WebServlet("/BusinessServlet")
public class BusinessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private DateFormat m_df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusinessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String userNum;
	     String password;
	     String op;

	     op       = request.getParameter("o");
	     userNum  = request.getParameter("a");
	     password = request.getParameter("b");

	     //login
	     SqlSession searchSession = MyBatisUtil.openSession();
	     
	     UserMapper userMapper = searchSession.getMapper(UserMapper.class);  
	     User u = userMapper.getUser(userNum);
	     
	     Result  result;
    	 /* operation list:
    	    add -- add a new user by root
    	    del -- del a user by root
    	    set -- set the remian count by root
    	    min -- minus the remian count by user self.
    	 */
	     if( u == null || !u.getPw().equals(password))
	     {
	    	 result = new Result();
	    	 result.setError(-200);
	     }
	     else if(op.equals("add"))
	     {
	    	 result = AddUser(u, userMapper, request);
	     }
	     else if(op.equals("del"))
	     {
	    	 result = DelUser(u, userMapper, request);
	     }
	     else if(op.equals("set"))
	     {
	    	 result = SetUserCount(u, userMapper, request);
	     }
	     else if(op.equals("min"))
	     {
	    	 result = MinusUserCount(u, userMapper, request);
	     }
	     else
	     {
	    	 result = new Result();
	    	 result.setError(-100);
	     }
	     searchSession.commit();
	     searchSession.close();
	     
	     Gson gson = new Gson();
		 String json = gson.toJson(result);
		       
	     response.setContentType("text/plain");
	     response.setCharacterEncoding("UTF-8");
	     PrintWriter out = response.getWriter();
		 out.println(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected Result AddUser(User user, UserMapper userMapper, HttpServletRequest request)
	{
		Result  result = new Result();
		if( user.isRootUser())
	    {
			String userNum  = request.getParameter("na");
   		    String password = request.getParameter("nb");
   		 
   		    User tu = userMapper.getUser(userNum);
   		    if(tu == null)
   		    {
   		    	User newUser = new User(userNum, 0, password);
   		    	userMapper.addUser(newUser);
   		    	//get the usr data again!!!
   		    	User u = userMapper.getUser(userNum);
   		    	result.setCount(u.getRemain_count());
   		    	result.setSuccess();
   		    }
   		    else
   		    {
   		    	result.setError(-301);
   		    }
	    }
   	    else
   	    {
   		    result.setError(-300);
   	    }
		return result;
	}
	
	protected Result DelUser(User user, UserMapper userMapper, HttpServletRequest request)
	{
		Result  result = new Result();
		if( user.isRootUser())
	    {
			String userNum = request.getParameter("na");

   		    User tu = userMapper.getUser(userNum);
   		    if(tu != null)
   		    {
   		    	userMapper.deleteUser(userNum);
   		    	result.setSuccess();
   		    }
   		    else
   		    {
   		    	result.setError(-302);
   		    }
	    }
   	    else
   	    {
   		    result.setError(-300);
   	    }
		return result;
	}
	protected Result SetUserCount(User user, UserMapper userMapper, HttpServletRequest request)
	{
		Result  result = new Result();
		if( user.isRootUser())
	    {
			int count = -1;
			String userNum = request.getParameter("na");
			String cntStr = request.getParameter("c");
		    if( cntStr != null)
		    { 
		    	count = Integer.parseInt(cntStr);
		    }

   		    User tu = userMapper.getUser(userNum);
   		    if(tu != null)
   		    {
   		    	userMapper.updateUser(userNum, count, GetCurrentTime());
   		    	result.setSuccess();
   		    }
   		    else
   		    {
   		    	result.setError(-302);
   		    }
	    }
   	    else
   	    {
   		    result.setError(-300);
   	    }
		return result;
	}
	protected Result MinusUserCount(User user, UserMapper userMapper, HttpServletRequest request)
	{
		Result  result = new Result();
		if( user.isRootUser())
	    {
			result.setError(-300);
	    }
   	    else
   	    {
   		    if(checkDate(user.getModify_date()))
   		    {
   		    	userMapper.updateUser(user.getUser_num(), user.getRemain_count() - 1, GetCurrentTime());
   		    	result.setSuccess();
   		    }
   		    else
   		    {
   		    	result.setError(-303);
   		    }
   	    }
		return result;
	}
	
	// for test, always return true.!!
	public boolean checkDate( String modify_date)
	{
		//Date date5 = df.parse("2016-01-24 02:51:18 Sunday"); 
		Calendar now = Calendar.getInstance();
		if(now.get(Calendar.DAY_OF_WEEK) != 7 ) //not sat.
		{
			return true;
		}
		return true;
	}
	
	public String GetCurrentTime()
	{
		Date date = new Date();
		return m_df.format(date);
	}
}
