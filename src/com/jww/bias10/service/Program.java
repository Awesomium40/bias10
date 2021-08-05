package com.jww.bias10.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jww.bias10.model.Interview;
import com.jww.bias10.repo.InterviewRepository;

public class Program
{
	protected static String driver="com.mysql.jdbc.Driver";
	protected static String ur2 = "jdbc:mysql://192.168.1.19/bias10";
	protected static String url =" jdbc:mysql://192.168.1.19/utopia";
	protected static String uid = "jay";
	protected static String pwd = "qeswaK4trUku";
	protected static Connection con;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		
		con = DriverManager.getConnection(ur2, uid, pwd);
		con.setAutoCommit(false);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		
		InterviewRepository repo = new InterviewRepository(con);
		
		Interview iv = new Interview();
		iv.setName("TEST_INTERVIEW");
		
		//repo.insert(iv);
		//con.rollback();
		
		PreparedStatement ps = con.prepareStatement("INSERT INTO interview (interview_name) VALUES (?)");
		ps.setObject(1, "TEST_INTERVIEW");
		ps.executeUpdate();
		con.rollback();
	}

}
