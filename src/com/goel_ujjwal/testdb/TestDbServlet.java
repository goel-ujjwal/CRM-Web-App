package com.goel_ujjwal.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// setup connection variables
		String user = "springstudent";
		String password = "springstudent";

		String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
		String driver = "com.mysql.cj.jdbc.Driver";

		// get connection to the Database
		try {
			PrintWriter out = response.getWriter();
			out.println("Connecting to Db: " + jdbcUrl);

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(jdbcUrl, user, password);

			out.println("Connection to Db successful!");
			conn.close();

		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
