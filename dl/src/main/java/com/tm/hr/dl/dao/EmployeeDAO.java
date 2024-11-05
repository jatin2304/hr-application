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
DesignationDAOInterface ddao=new DesignationDAO();
boolean exists=ddao.codeExists(designationCode);
if(exists==false) throw new DAOException("Invalid designation code: "+ designationCode);
Date dateOfBirth=edto.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of Birth is null");
char gender=edto.getGender();
if(gender==' ') throw new DAOException("Invalid gender. choose(M/F)");
boolean isIndian=edto.getIsIndian();
BigDecimal basicSalary=edto.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Invalid basic salary");
String panNumber=edto.getPANNumber();
if(panNumber==null) throw new DAOException("Invalid PAN number");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN number");
String aadharCardNumber=edto.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar card number ");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number");
try
{
File file=new File(DATA_FILE);
RandomAccessFile raf=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0, recordCount=0;
String lastGeneratedCodeString, recordCountString;
if(raf.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<12) lastGeneratedCodeString+=" ";
recordCountString="0";
while(recordCountString.length()<12) recordCountString+=" ";
raf.writeBytes(lastGeneratedCodeString+"\n");
raf.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
recordCount=Integer.parseInt(raf.readLine().trim());
}
String fPANNumber;
String fAadharCardNumber;
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber) && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
raf.close();
throw new DAOException("PAN number and Aadhar card number already exist");
}
if(fPANNumber.equalsIgnoreCase(panNumber))
{
raf.close();
throw new DAOException("PAN number already exists");
}
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
raf.close();
throw new DAOException("Aadhar card number already exists");
}
}
int tmpid=10000000+lastGeneratedCode+1;
employeeID="A"+tmpid;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=sdf.format(dateOfBirth);
String basicSalaryString=basicSalary.toPlainString();
edto.setEmployeeID(employeeID);
//System.out.println(edto.getEmployeeID());
raf.writeBytes(employeeID+"\n");
raf.writeBytes(name+"\n");
raf.writeBytes(designationCode+"\n");
raf.writeBytes(dateOfBirthString+"\n");
raf.writeBytes(gender+"\n");
raf.writeBytes(isIndian+"\n");
raf.writeBytes(basicSalaryString+"\n");
raf.writeBytes(panNumber+"\n");
raf.writeBytes(aadharCardNumber+"\n");
raf.seek(0);
lastGeneratedCodeString=String.valueOf(lastGeneratedCode+1);
while(lastGeneratedCodeString.length()<12) lastGeneratedCodeString+=" ";
//alternate way: recordCountString=String.format("%-12d",recordCount)
recordCountString=String.valueOf(recordCount+1);
while(recordCountString.length()<12) recordCountString+=" ";
raf.writeBytes(lastGeneratedCodeString+"\n");
raf.writeBytes(recordCountString+"\n");
raf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
String name=edto.getName();
if(name==null) throw new DAOException("Invalid name");
name=name.trim();
if(name.length()==0) throw new DAOException("Invalid name");
int designationCode=edto.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
DesignationDAOInterface ddao=new DesignationDAO();
boolean exists=ddao.codeExists(designationCode);
if(exists==false) throw new DAOException("Invalid designation code: "+ designationCode);
Date dateOfBirth=edto.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of Birth is null");
char gender=edto.getGender();
if(gender==' ') throw new DAOException("Invalid gender. choose(M/F)");
boolean isIndian=edto.getIsIndian();
BigDecimal basicSalary=edto.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Invalid basic salary");
String panNumber=edto.getPANNumber();
if(panNumber==null) throw new DAOException("Invalid PAN number");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Invalid PAN number");
String aadharCardNumber=edto.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Invalid Aadhar card number ");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Invalid Aadhar card number");
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) throw new DAOException("Invalid Employee details.");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
throw new DAOException("Invalid Employee details.");
}
raf.readLine();
raf.readLine();
String fEmployeeID=null;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
boolean found=false;
long foundAt=0;
boolean panNumberFound=false, aadharCardNumberFound=false;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
while(raf.getFilePointer()<raf.length())
{
if(found==false) foundAt=raf.getFilePointer();
fEmployeeID=raf.readLine();
if(fEmployeeID.equalsIgnoreCase(employeeID)) found=true;
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(employeeID.equalsIgnoreCase(fEmployeeID)==false && fPANNumber.equalsIgnoreCase(panNumber)==true)
{
panNumberFound=true;
}
if(employeeID.equalsIgnoreCase(fEmployeeID)==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)==true)
{
aadharCardNumberFound=true;
}
}
if(found==false)
{
raf.close();
throw new DAOException("Invalid Employee id: "+employeeID);
}
if(panNumberFound==true && aadharCardNumberFound==true)
{
raf.close();
throw new DAOException("PAN number and Aadhar card number already exists");
}
if(panNumberFound)
{
raf.close();
throw new DAOException("PAN number already exists");
}
if(aadharCardNumberFound==true)
{
raf.close();
throw new DAOException("Aadhar card number already exists");
}
//employeeID to be updated exists
int i=0;
File tmpfile=new File("tmp.data");
if(tmpfile.exists()) tmpfile.delete();
RandomAccessFile tmpraf=new RandomAccessFile(tmpfile,"rw");
raf.seek(foundAt);
tmpraf.seek(0);
fEmployeeID=raf.readLine();
for(i=1;i<=8;i++) raf.readLine();
while(raf.getFilePointer()<raf.length()) tmpraf.writeBytes(raf.readLine()+"\n");
raf.seek(foundAt);
raf.writeBytes(fEmployeeID+"\n");
raf.writeBytes(name+"\n");
raf.writeBytes(designationCode+"\n");
raf.writeBytes(sdf.format(dateOfBirth)+"\n");
raf.writeBytes(gender+"\n");
raf.writeBytes(isIndian+"\n");
raf.writeBytes(basicSalary.toPlainString()+"\n");
raf.writeBytes(panNumber+"\n");
raf.writeBytes(aadharCardNumber+"\n");
tmpraf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length()) raf.writeBytes(tmpraf.readLine()+"\n");
raf.setLength(raf.getFilePointer());
tmpraf.setLength(0);
raf.close();
tmpraf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public void delete(String employeeID) throws DAOException
{
if(new EmployeeDAO().employeeIDExists(employeeID)==false) throw new DAOException("Invalid Employee id");
if(employeeID==null) throw new DAOException("Invalid Employee id");
employeeID=employeeID.trim();
if(employeeID.length()==0) throw new DAOException("Invalid Employee id");
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) throw new DAOException("Invalid Employee details.");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
throw new DAOException("Invalid Employee details.");
}
int lastGeneratedCode=0, recordCount=0;
String lastGeneratedCodeString, recordCountString;
lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
recordCount=Integer.parseInt(raf.readLine().trim());
String fEmployeeID=null;
boolean found=false;
long foundAt=0;
int i=0;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
while(raf.getFilePointer()<raf.length())
{
foundAt=raf.getFilePointer();
fEmployeeID=raf.readLine();
if(fEmployeeID.equalsIgnoreCase(employeeID))
{
found=true;
break;
}
for(i=1;i<=8;i++) raf.readLine();
}
if(found==false)
{
raf.close();
throw new DAOException("Invalid Employee id: "+employeeID);
}
//employeeID to be updated exists
File tmpfile=new File("tmp.data");
if(tmpfile.exists()) tmpfile.delete();
RandomAccessFile tmpraf=new RandomAccessFile(tmpfile,"rw");
raf.seek(foundAt);
tmpraf.seek(0);
for(i=1;i<=9;i++) raf.readLine();
while(raf.getFilePointer()<raf.length()) tmpraf.writeBytes(raf.readLine()+"\n");
raf.seek(foundAt);
tmpraf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length()) raf.writeBytes(tmpraf.readLine()+"\n");
raf.setLength(tmpraf.length()+foundAt);//raf.setLength(raf.getFilePointer())
tmpraf.setLength(0);
raf.seek(0);
//decreasing recordCount by 1
raf.readLine();
recordCountString=String.valueOf(recordCount-1);
while(recordCountString.length()<12) recordCountString+=" ";
raf.writeBytes(recordCountString+"\n");
raf.close();
tmpraf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public EmployeeDTOInterface getByEmployeeID(String employeeID) throws DAOException
{
if(employeeID==null) throw new DAOException("Invalid Employee ID");
employeeID=employeeID.trim();
if(employeeID.length()==0) throw new DAOException("Invalid Employee ID");
try
{
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("No employee added");
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) throw new DAOException("No employee added");
raf.readLine();
raf.readLine();
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
while(raf.getFilePointer()<raf.length())
{
fEmployeeID=raf.readLine();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fEmployeeID.equalsIgnoreCase(employeeID))
{
raf.close();
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
return edto;
}
}
raf.close();
throw new DAOException("Invalid Employee ID");
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("No employee added");
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) throw new DAOException("No employee added");
raf.readLine();
raf.readLine();
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
while(raf.getFilePointer()<raf.length())
{
fEmployeeID=raf.readLine();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
raf.close();
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
return edto;
}
}
raf.close();
throw new DAOException("Invalid PAN number");
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("No employee added");
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) throw new DAOException("No employee added");
raf.readLine();
raf.readLine();
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
while(raf.getFilePointer()<raf.length())
{
fEmployeeID=raf.readLine();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
raf.close();
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
return edto;
}
}
raf.close();
throw new DAOException("Invalid Aadhar card number");
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file==null) return employees;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) return employees;
raf.readLine();
raf.readLine();
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
EmployeeDTOInterface edto;
while(raf.getFilePointer()<raf.length())
{
//we could also directly assign the content read from file
fEmployeeID=raf.readLine();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
raf.close();
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fName.equalsIgnoreCase(name))
{
edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
employees.add(edto);
}
}
raf.close();
return employees;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public Set<EmployeeDTOInterface> getAll() throws DAOException
{
try
{
File file=new File(DATA_FILE);
if(file==null) return null;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
EmployeeDTOInterface edto;
Set<EmployeeDTOInterface> employees=new TreeSet<>();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
raf.readLine();
raf.readLine();
char fGender;
while(raf.getFilePointer()<raf.length())
{
edto=new EmployeeDTO();
edto.setEmployeeID(raf.readLine());
edto.setName(raf.readLine());
edto.setDesignationCode(Integer.parseInt(raf.readLine().trim()));
try
{
edto.setDateOfBirth(sdf.parse(raf.readLine()));
}catch(ParseException pe)
{
raf.close();
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(Boolean.parseBoolean(raf.readLine()));
edto.setBasicSalary(new BigDecimal(raf.readLine()));
edto.setPANNumber(raf.readLine());
edto.setAadharCardNumber(raf.readLine());
employees.add(edto);
}
/*
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
raf.readLine();
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
fEmployeeID=raf.readLine().trim();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
raf.close();
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
edto.setGender(fGender);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
employees.add(edto);
}
*/
raf.close();
return employees;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code");
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(DATA_FILE);
if(file==null) return employees;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) return employees;
raf.readLine();
raf.readLine();
String fEmployeeID;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary=null;
String fPANNumber;
String fAadharCardNumber;
EmployeeDTOInterface edto;
while(raf.getFilePointer()<raf.length())
{
//we could also directly assign the content read from file
fEmployeeID=raf.readLine();
fName=raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
try
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=sdf.parse(raf.readLine());
}catch(ParseException pe)
{
raf.close();
throw new DAOException(pe.getMessage());
}
fGender=raf.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(raf.readLine());
fBasicSalary=new BigDecimal(raf.readLine());
fPANNumber=raf.readLine();
fAadharCardNumber=raf.readLine();
if(fDesignationCode==designationCode)
{
edto=new EmployeeDTO();
edto.setEmployeeID(fEmployeeID);
edto.setName(fName);
edto.setDesignationCode(fDesignationCode);
edto.setDateOfBirth(fDateOfBirth);
if(fGender=='m' || fGender=='M') edto.setGender(GENDER.MALE);
if(fGender=='f' || fGender=='F') edto.setGender(GENDER.FEMALE);
edto.setIsIndian(fIsIndian);
edto.setBasicSalary(fBasicSalary);
edto.setPANNumber(fPANNumber);
edto.setAadharCardNumber(fAadharCardNumber);
employees.add(edto);
}
}
raf.close();
return employees;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public boolean employeeIDExists(String employeeID) throws DAOException
{
if(employeeID==null) return false;
employeeID=employeeID.trim();
if(employeeID.length()==0) return false;
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
raf.readLine();

String fEmployeeID;
while(raf.getFilePointer()<raf.length())
{
fEmployeeID=raf.readLine();
if(fEmployeeID.equalsIgnoreCase(employeeID)) 
{
raf.close();
return true;
}
for(int i=1;i<=8;i++) raf.readLine();
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
raf.readLine();
String fName;
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
fName=raf.readLine();
if(fName.equalsIgnoreCase(name)) 
{
raf.close();
return true;
}
for(int i=1;i<=7;i++) raf.readLine();
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
raf.readLine();
String fPANNumber;
while(raf.getFilePointer()<raf.length())
{
for(int i=1;i<=7;i++) raf.readLine();
fPANNumber=raf.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber)) 
{
raf.close();
return true;
}
raf.readLine();
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
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
File file=new File(DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
raf.readLine();
String fAadharCardNumber;
while(raf.getFilePointer()<raf.length())
{
for(int i=1;i<=8;i++) raf.readLine();
fAadharCardNumber=raf.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) 
{
raf.close();
return true;
}
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code: "+designationCode);
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) return false;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
int fDesignationCode;
raf.readLine();
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
if(fDesignationCode==designationCode) 
{
raf.close();
return true;	
}
for(int i=1;i<=6;i++) raf.readLine();
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public int getCount() throws DAOException
{
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) return 0;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0)
{
raf.close();
return 0;
}
raf.readLine();
int recordCount=Integer.parseInt(raf.readLine().trim());
raf.close();
return recordCount;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public int getCountByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false) throw new DAOException("Invalid designation code.");
try
{
File file=new File(DATA_FILE);
if(file.exists()==false) return 0;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
return 0;
}
int count=0;
int fDesignationCode;
raf.readLine();
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
raf.readLine();
fDesignationCode=Integer.parseInt(raf.readLine());
if(fDesignationCode==designationCode) count++;	
for(int i=1;i<=6;i++) raf.readLine();
}
raf.close();
return count;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************
}//EmployeeDAO class ends