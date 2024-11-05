package com.tm.hr.dl.dao;
import java.sql.*;
import com.tm.hr.dl.exceptions.*;
public class ConnectionDAO
{
private ConnectionDAO()
{
}
public static Connection getConnection() throws DAOException
{
Connection c=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return c;
}
}