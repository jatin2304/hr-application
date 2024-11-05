package com.tm.hr.dl.dao;
import java.sql.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;
public class DesignationDAO implements DesignationDAOInterface
{
private static final String DATA_FILE="designation.data";

//******************************************************************************

public void add(DesignationDTOInterface ddto) throws DAOException
{
if(ddto==null) throw new DAOException("Designation cannot be null");
String title=ddto.getTitle();
if(title==null) throw new DAOException("Designation cannot be null");
title=title.trim();
if(title.length()==0) throw new DAOException("Designation cannot be null");
int lastGeneratedCode=0, recordCount=0;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select code from designation where title=(?)");
p.setString(1,title);
ResultSet r=p.executeQuery();
if(r.next())
{
r.close();
p.close();
c.close();
throw new DAOException("Designation: "+title+" already exists.");
}
r.close();
p.close();
p=c.prepareStatement("insert into designation (title) values (?)", Statement.RETURN_GENERATED_KEYS);
p.setString(1,title);
p.executeUpdate();
r=p.getGeneratedKeys();
r.next();
int code=r.getInt(1);
r.close();
p.close();
c.close();
ddto.setCode(code);
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public void update(DesignationDTOInterface ddto) throws DAOException
{
if(ddto==null) throw new DAOException("Designation is null");
int code=ddto.getCode();
String title=ddto.getTitle();
if(code<=0) throw new DAOException("Invalid code: "+code);
if(title==null) throw new DAOException("Invalid title: "+title);
title=title.trim();
if(title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p;
ResultSet r;
p=c.prepareStatement("select code from designation where code=?");
p.setInt(1,code);
r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Code: "+code+" does not exist.");
}
p=c.prepareStatement("select * from designation where title=? and code!=?");// <> is also for not equals to
p.setString(1,title);
p.setInt(2,code);
r=p.executeQuery();
if(r.next())
{
r.close();
p.close();
c.close();
throw new DAOException("Designation "+title+" already exists.");
}
r.close();
p.close();
p=c.prepareStatement("update designation set title=? where code=?");
p.setString(1,title);
p.setInt(2,code);
p.executeUpdate();
p.close();
c.close();
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p;
ResultSet r;
p=c.prepareStatement("select * from designation where code=?");
p.setInt(1, code);
r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Code: "+code+" does not exist.");
}
String title=r.getString("title").trim();
r.close();
p.close();
// to check whether the code is alloted to any employee
p=c.prepareStatement("select gender from employee where designation_code=?");
p.setInt(1,code);
r=p.executeQuery();
if(r.next())//code is alloted to any employee
{
r.close();
p.close();
c.close();
throw new DAOException("Unable to delete as code "+code+" with designation '"+title+"' is alloted to an employee(s)");
}
p=c.prepareStatement("delete from designation where code=?");
p.setInt(1,code);
p.executeUpdate();
p.close();
c.close();
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
Connection c=ConnectionDAO.getConnection();
Statement s=c.createStatement();
ResultSet r=s.executeQuery("select * from designation");;
DesignationDTOInterface ddto;
while(r.next())
{
ddto=new DesignationDTO();
ddto.setCode(r.getInt(1));
ddto.setTitle(r.getString(2));
designations.add(ddto);
}
r.close();
s.close();
c.close();
return designations;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from designation where code=?");
p.setInt(1,code);
ResultSet r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Code: "+code+" does not exist.");
}
DesignationDTOInterface ddto=new DesignationDTO();
ddto.setCode(r.getInt("code"));
ddto.setTitle(r.getString("title").trim());
r.close();
p.close();
c.close();
return ddto;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null) throw new DAOException("Invalid title: "+title);
title=title.trim();
if(title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from designation where title=?");
p.setString(1,title);
ResultSet r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Designation: "+title+" does not exist.");
}
DesignationDTOInterface ddto=new DesignationDTO();
ddto.setCode(r.getInt("code"));
ddto.setTitle(r.getString("title").trim());
r.close();
p.close();
c.close();
return ddto;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from designation where code=?");
p.setInt(1,code);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public boolean titleExists(String title) throws DAOException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from designation where title=?");
p.setString(1,title);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}

//******************************************************************************

public int getCount() throws DAOException
{
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select count(*) as count from designation");
ResultSet r=p.executeQuery();
r.next();
int count=r.getInt("count");
r.close();
p.close();
c.close();
return count;
}catch(SQLException e)
{
throw new DAOException(e.getMessage());
}
}
//******************************************************************************

}