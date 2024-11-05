package com.tm.hr.dl.dao;
import com.tm.enums.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import java.sql.*;
public class EmployeeDAO implements EmployeeDAOInterface 
{
public static final String DATA_FILE="employee.data";

//******************************************************************************

public void add(EmployeeDTOInterface edto) throws DAOException
{
if(edto==null) throw new DAOException("Employee is null");
String employeeID;
String name=edto.getName();
if(name==null) throw new DAOException("Invalid name");
name=name.trim();
if(name.length()==0) throw new DAOException("Invalid name");
int designationCode=edto.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);

Connection c=null;
PreparedStatement p;
ResultSet r;
try
{
c=ConnectionDAO.getConnection();
p=c.prepareStatement("select code from designation where code=?");
p.setInt(1,designationCode);
r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
r.close();
p.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}

/*
DesignationDAOInterface ddao=new DesignationDAO();
boolean exists=ddao.codeExists(designationCode);
if(exists==false) throw new DAOException("Invalid designation code: "+ designationCode);
*/
java.util.Date dateOfBirth=edto.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=edto.getGender();
if(gender==' ')
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid gender. choose(M/F)");
}
boolean isIndian=edto.getIsIndian();
BigDecimal basicSalary=edto.getBasicSalary();
if(basicSalary==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()==-1) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid basic salary");
}
String panNumber=edto.getPANNumber();
if(panNumber==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid PAN number");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid PAN number");
}
String aadharCardNumber=edto.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid Aadhar card number ");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid Aadhar card number");
}
try
{
p=c.prepareStatement("select gender from employee where pan_number=?");
p.setString(1,panNumber);
r=p.executeQuery();
boolean panExists=false,aadharExists=false;
if(r.next()) panExists=true;
r.close();
p.close();
p=c.prepareStatement("select gender from employee where aadhar_card_number=?");
p.setString(1,aadharCardNumber);
r=p.executeQuery();
if(r.next()) aadharExists=true;
if(panExists && aadharExists)
{
r.close();
p.close();
c.close();
throw new DAOException("PAN number("+panNumber+") and Aadhar number("+aadharCardNumber+")already exist.");
}
if(panExists )
{
r.close();
p.close();
c.close();
throw new DAOException("PAN number("+panNumber+") already exists.");
}
if(aadharExists)
{
r.close();
p.close();
c.close();
throw new DAOException("Aadhar number("+aadharCardNumber+")already exists.");
}
r.close();
p.close();
p=c.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
p.setString(1,name);
p.setInt(2,designationCode);
java.sql.Date date=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
p.setDate(3,date);
p.setString(4,String.valueOf(gender));
p.setBoolean(5,isIndian);
p.setBigDecimal(6,basicSalary);
p.setString(7,panNumber);
p.setString(8,aadharCardNumber);
p.executeUpdate();
r=p.getGeneratedKeys();
r.next();
int generatedEmployeeID=r.getInt(1);
r.close();
p.close();
c.close();
edto.setEmployeeID("A"+(1000000+generatedEmployeeID));
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public void update(EmployeeDTOInterface edto) throws DAOException
{
if(edto==null) throw new DAOException("Employee is null");
String employeeID=edto.getEmployeeID();
if(new EmployeeDAO().employeeIDExists(employeeID)==false) throw new DAOException("Invalid Employee id");
if(employeeID==null) throw new DAOException("Invalid Employee id");
employeeID=employeeID.trim();
if(employeeID.length()==0) throw new DAOException("Invalid Employee id");
int actualEmployeeID;
try
{
actualEmployeeID=Integer.parseInt(employeeID.substring(1))-1000000;
}catch(Exception e)
{
throw new DAOException("Invalid employee id");
}
String name=edto.getName();
if(name==null) throw new DAOException("Invalid name");
name=name.trim();
if(name.length()==0) throw new DAOException("Invalid name");
int designationCode=edto.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
Connection c=null;
PreparedStatement p;
ResultSet r;
try
{
c=ConnectionDAO.getConnection();
p=c.prepareStatement("select code from designation where code=?");
p.setInt(1,designationCode);
r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Invalid designation code: "+designationCode);
}
r.close();
p.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}

/*
DesignationDAOInterface ddao=new DesignationDAO();
boolean exists=ddao.codeExists(designationCode);
if(exists==false) throw new DAOException("Invalid designation code: "+ designationCode);
*/
java.util.Date dateOfBirth=edto.getDateOfBirth();
if(dateOfBirth==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender=edto.getGender();
if(gender==' ')
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid gender. choose(M/F)");
}
boolean isIndian=edto.getIsIndian();
BigDecimal basicSalary=edto.getBasicSalary();
if(basicSalary==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum()==-1) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid basic salary");
}
String panNumber=edto.getPANNumber();
if(panNumber==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid PAN number");
}
panNumber=panNumber.trim();
if(panNumber.length()==0) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid PAN number");
}
String aadharCardNumber=edto.getAadharCardNumber();
if(aadharCardNumber==null) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid Aadhar card number ");
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) 
{
try
{
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
throw new DAOException("Invalid Aadhar card number");
}
try
{
p=c.prepareStatement("select gender from employee where pan_number=? and employee_id<>?");
p.setString(1,panNumber);
p.setInt(2,actualEmployeeID);
r=p.executeQuery();
boolean panExists=false,aadharExists=false;
if(r.next()) panExists=true;
r.close();
p.close();
p=c.prepareStatement("select gender from employee where aadhar_card_number=? and employee_id<>?");
p.setString(1,aadharCardNumber);
p.setInt(2,actualEmployeeID);
r=p.executeQuery();
if(r.next()) aadharExists=true;
r.close();
p.close();
if(panExists && aadharExists)
{
r.close();
p.close();
c.close();
throw new DAOException("PAN number("+panNumber+") and Aadhar number("+aadharCardNumber+")already exist.");
}
else if(panExists )
{
r.close();
p.close();
c.close();
throw new DAOException("PAN number("+panNumber+") already exists.");
}
else if(aadharExists)
{
r.close();
p.close();
c.close();
throw new DAOException("Aadhar number("+aadharCardNumber+")already exists.");
}
r.close();
p.close();
p=c.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where employee_id=?");
p.setString(1,name);
p.setInt(2,designationCode);
java.sql.Date date=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
p.setDate(3,date);
p.setString(4,String.valueOf(gender));
p.setBoolean(5,isIndian);
p.setBigDecimal(6,basicSalary);
p.setString(7,panNumber);
p.setString(8,aadharCardNumber);
p.setInt(9,actualEmployeeID);
p.executeUpdate();
p.close();
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public void delete(String employeeID) throws DAOException
{
if(new EmployeeDAO().employeeIDExists(employeeID)==false) throw new DAOException("Invalid Employee id");
if(employeeID==null) throw new DAOException("Invalid Employee id");
employeeID=employeeID.trim();
if(employeeID.length()==0) throw new DAOException("Invalid Employee id");
int actualEmployeeID;
try
{
actualEmployeeID=Integer.parseInt(employeeID.substring(1))-1000000;
}catch(Exception e)
{
throw new DAOException("Invalid employee id.");
}
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p;
ResultSet r;
p=c.prepareStatement("select gender from employee where employee_id=?");
p.setInt(1,actualEmployeeID);
r=p.executeQuery();
if(r.next()==false)
{
r.close();
p.close();
c.close();
throw new DAOException("Employee id: "+employeeID+" does not exist.");
}
r.close();
p.close();
p=c.prepareStatement("delete from employee where employee_id=?");
p.setInt(1,actualEmployeeID);
p.executeUpdate();
p.close();
c.close();
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public EmployeeDTOInterface getByEmployeeID(String employeeID) throws DAOException
{
if(employeeID==null) throw new DAOException("Invalid Employee ID");
employeeID=employeeID.trim();
if(employeeID.length()==0) throw new DAOException("Invalid Employee ID");
int actualEmployeeID;
try
{
actualEmployeeID=Integer.parseInt(employeeID.substring(1))-1000000;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where employee_id=?");
p.setInt(1,actualEmployeeID);
ResultSet r=p.executeQuery();
if(r.next())
{
r.close();
p.close();
c.close();
throw new DAOException("Invalid employee id.");
}
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID(employeeID);
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
r.close();
p.close();
c.close();
return edto;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}


//******************************************************************************

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("Invalid PAN number");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN number");
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where pan_number=?");
p.setString(1, panNumber);
ResultSet r=p.executeQuery();
if(r.next())
{
r.close();
p.close();
c.close();
throw new DAOException("Invalid PAN number("+panNumber+")");
}
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID("A"+(r.getInt("employee_id")+1000000));
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
r.close();
p.close();
c.close();
return edto;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar card number");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number");
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where aadhar_card_number=?");
p.setString(1, aadharCardNumber);
ResultSet r=p.executeQuery();
if(r.next())
{
r.close();
p.close();
c.close();
throw new DAOException("Invalid aadhar card number("+aadharCardNumber+")");
}
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID("A"+(r.getInt("employee_id")+1000000));
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
r.close();
p.close();
c.close();
return edto;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public Set<EmployeeDTOInterface> getByName(String name) throws DAOException
{
if(name==null) throw new DAOException("Invalid Employee name");
name=name.trim();
if(name.length()==0) throw new DAOException("Invalid Employee name");
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where name=?");
p.setString(1,name);
ResultSet r=p.executeQuery();
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto;
while(r.next())
{
edto=new EmployeeDTO();
edto.setEmployeeID("A"+(r.getInt("employee_id")+1000000));
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
employees.add(edto);
}
r.close();
p.close();
c.close();
if(employees.size()==0) throw new DAOException("No record found against name: "+name);
return employees;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public Set<EmployeeDTOInterface> getAll() throws DAOException
{
try
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
Connection c=ConnectionDAO.getConnection();
Statement s=c.createStatement();
ResultSet r=s.executeQuery("select * from employee");
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto;
while(r.next())
{
edto=new EmployeeDTO();
edto.setEmployeeID("A"+(r.getInt("employee_id")+1000000));
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
employees.add(edto);
}
r.close();
s.close();
c.close();
return employees;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
try
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where designation_code=?");
p.setInt(1,designationCode);
ResultSet r=p.executeQuery();
java.util.Date utilDate;
java.sql.Date sqlDate;
EmployeeDTOInterface edto;
while(r.next())
{
edto=new EmployeeDTO();
edto.setEmployeeID("A"+(r.getInt("employee_id")+1000000));
edto.setName(r.getString("name").trim());
edto.setDesignationCode(r.getInt("designation_code"));
sqlDate=r.getDate("date_of_birth");
utilDate=new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
edto.setDateOfBirth(utilDate);
GENDER g=GENDER.MALE;
if((r.getString("gender").charAt(0))=='F') g=GENDER.FEMALE;
edto.setGender(g);
edto.setIsIndian(r.getBoolean("is_indian"));
edto.setBasicSalary(r.getBigDecimal("basic_salary"));
edto.setPANNumber(r.getString("pan_number").trim());
edto.setAadharCardNumber(r.getString("aadhar_card_number").trim());
employees.add(edto);
}
r.close();
p.close();
c.close();
if(employees.size()==0) throw new DAOException("No record found against code: "+designationCode);
return employees;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public boolean employeeIDExists(String employeeID) throws DAOException
{
if(employeeID==null) return false;
employeeID=employeeID.trim();
if(employeeID.length()==0) return false;
int actualEmployeeID;
try
{
actualEmployeeID=Integer.parseInt(employeeID.substring(1))-1000000;
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select gender from employee where employee_id=?");
p.setInt(1,actualEmployeeID);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public boolean nameExists(String name) throws DAOException
{
if(name==null) return false;
name=name.trim();
if(name.length()==0) return false;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where name=?");
p.setString(1,name);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where pan_number=?");
p.setString(1,panNumber);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from emlpoyee where aadhar_card_number=?");
p.setString(1,aadharCardNumber);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select * from employee where designation_code=?");
p.setInt(1,designationCode);
ResultSet r=p.executeQuery();
boolean exists=r.next();
r.close();
p.close();
c.close();
return exists;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public int getCount() throws DAOException
{
try
{
Connection c=ConnectionDAO.getConnection();
Statement s=c.createStatement();
ResultSet r=s.executeQuery("select count(*) from employee");
r.next();
int count=r.getInt("count");
r.close();
s.close();
c.close();
return count;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************

public int getCountByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code.");
try
{
Connection c=ConnectionDAO.getConnection();
PreparedStatement p=c.prepareStatement("select count(*) from employee where designation_code=?");
p.setInt(1,designationCode);
ResultSet r=p.executeQuery();
r.next();
int count=r.getInt("count");
r.close();
p.close();
c.close();
return count;
}catch(SQLException s)
{
throw new DAOException(s.getMessage());
}
}

//******************************************************************************
}//EmployeeDAO class ends