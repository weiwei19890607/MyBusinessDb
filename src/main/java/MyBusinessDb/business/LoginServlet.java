/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBusinessDb.business;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.HashMap;

import com.google.gson.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import MyBusinessDb.db.MyBatisUtil;
import MyBusinessDb.db.SqlMapper;
import MyBusinessDb.db.User;
import MyBusinessDb.db.UserMapper;
/**
 *
 * @author pi
 */
public class LoginServlet extends HttpServlet {

    public LoginServlet(){
        super();
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //receive username&password
        String userNum;
        String password;
        String code="";
        String message="";

        userNum = request.getParameter("a");
        password = request.getParameter("b");
        
        Result  result = new Result();
        result.setCount(-1);
	    result.setCode(0);
   	    result.setStatus("ok");
   	    
        //login
        SqlSession searchSession = MyBatisUtil.openSession();
  
        UserMapper userMapper = searchSession.getMapper(UserMapper.class);
        
        User u = userMapper.getUser(userNum);
        if(u == null)
        {
        	result.setError(-200);;
        }
        else if(!password.equals(u.getPw()) )
        {
        	result.setError(-100);
        }
        else
        {
        	result.setCount(u.getRemain_count());;
        }

        searchSession.close();
        
        Gson gson = new Gson();
	    String json = gson.toJson(result);
		       
	     response.setContentType("text/plain");
	     response.setCharacterEncoding("UTF-8");
	     PrintWriter out = response.getWriter();
		 out.println(json);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
