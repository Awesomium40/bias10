package com.jww.bias10.test.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseRepositoryTest
{
	protected static String driver="com.mysql.cj.jdbc.Driver";
	protected static String url = "jdbc:mysql://192.168.1.19/bias10";
	protected static String uid = "jay";
	protected static String pwd = "qeswaK4trUku";
	protected static Connection con;
	
	@BeforeClass
	public static void setUpClass() throws ClassNotFoundException, SQLException
	{
		Class.forName(driver);
		
		con = DriverManager.getConnection(url, uid, pwd);
		con.setAutoCommit(false);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
	}
	
	@AfterClass
	public static void tearDownClass() throws SQLException
	{
		con.rollback();
		if (con != null && !con.isClosed())
			con.close();
	}
}
