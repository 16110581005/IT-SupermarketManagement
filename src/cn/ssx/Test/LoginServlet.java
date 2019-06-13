package cn.ssx.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ssx.Class.Administrator;
import cn.ssx.Class.Product;
import cn.ssx.DAO.Administrator_interface;
import cn.ssx.DAO.Product_interface;
import cn.ssx.Impl.Administrator_DAO_Impl;
import cn.ssx.Impl.Product_DAO_Impl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
         
        //����������������  
        Enumeration params = request.getParameterNames();  
        System.out.println(params.toString());
		response.setContentType("text/html; charset=utf-8");
	    request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		String Administrator_name = request.getParameter("username");
		String Administrator_password = request.getParameter("password");
		Administrator_interface dao = new Administrator_DAO_Impl();
		Administrator admin = null;
		if(test2(request,response)==false){
			out.write("<script language='javascript'>alert('�Ƿ����룬����������')</script>"); 
			response.setHeader("Refresh", "0; URL = http://localhost:8080/Second/Login.jsp" );
		}
		else {
			try {
				admin = dao.Findname(Administrator_name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if(admin==null) {
				 
				 PrintWriter pw=response.getWriter();
				 response.setContentType("text/html; charset=utf-8");
				 request.setCharacterEncoding("utf-8");
				 pw.write("<script language='javascript'>alert('���û������ڣ�����Ϊ����ת��ע�����')</script>"); 
				 response.setHeader("Refresh", "0; http://localhost:8080/Second/Register.jsp" );
			 }
			 else if(admin.getAdministrator_password().equals(Administrator_password )) {
				 
				 PrintWriter pw=response.getWriter();
				 response.setContentType("text/html; charset=utf-8");
				 request.setCharacterEncoding("utf-8");
				 pw.write("<script language='javascript'>alert('��½�ɹ�������Ϊ����ת���������')</script>"); 
				 test1(request,response);
			 }
			 else {
				 PrintWriter pw=response.getWriter();
				 response.setContentType("text/html; charset=utf-8");
				    request.setCharacterEncoding("utf-8");
				 pw.write("<script language='javascript'>alert('���������������')</script>"); 
				 response.setHeader("Refresh", "0; URL = http://localhost:8080/Second/Login.jsp" );
			 }
		}	 
}
	protected void test1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
	    request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		Product_interface dao = new Product_DAO_Impl();
		HttpSession session = request.getSession();
		try {
			List <Product> list  = dao.Product_FindWarning();
			response.setContentType("text/html; charset=utf-8");
			session.setAttribute("WarningProductlist", list);
			response.setHeader("Refresh", "0; http://localhost:8080/Second/Index.jsp" );
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	protected boolean test2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   HttpServletRequest req = (HttpServletRequest) request;  
	       HttpServletResponse res = (HttpServletResponse) response; 
	       Enumeration params = req.getParameterNames();  
		   String sql = "";  
	        while (params.hasMoreElements()) {  
	            //�õ�������  
	            String name = params.nextElement().toString();  
	            //System.out.println("name===========================" + name + "--");  
	            //�õ�������Ӧֵ  
	            String[] value = req.getParameterValues(name);  
	            for (int i = 0; i < value.length; i++) {  
	                sql = sql + value[i];  
	            }  
	        }  
			/*
			 * System.out.println(sql); System.out.println("��ƥ���ַ�����"+sql);
			 */
	        if (sqlValidate(sql)) {  
	            return false;  
	        }   
	        return true;   
	}
	protected static boolean sqlValidate(String str) {  
        str = str.toLowerCase();//ͳһתΪСд
        //String badStr = "and|exec";
        String badStr = "'from|and|join|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|char|declare|sitename|net user|xp_cmdshell|or|like|malaigebazi|caonima|shabi|<html>|<head>|<title>|<h1>|<pre>|<u>|<b>|<i>|<tt>|<cite>|<em>|<strong>|<font>|<BASEFONT>|<big>|<SMALL>|<STRIKE>|<CODE>|<KBD>|<SAMP>|<VAR>|<BLOCKQUOTE>|<DFN>|<ADDRESS>|<sup>|<SUB>|<xmp>|<plaintext>|<listing>\r\n" + 
        		"";  
        /*String badStr = "'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|" +  
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";    */    //���˵���sql�ؼ��֣������ֶ����  
        String[] badStrs = badStr.split("\\|");  
        for (int i = 0; i < badStrs.length; i++) {
        	badStrs[i]=badStrs[i].toLowerCase();
            if (str.indexOf(badStrs[i]) !=-1) { 
                System.out.println("ƥ�䵽��"+badStrs[i]);
                return true;  
            }  
        }  
        return false;  
    }  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	 

}
