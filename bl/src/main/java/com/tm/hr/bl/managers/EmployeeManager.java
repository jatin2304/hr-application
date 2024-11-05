package com.tm.hr.bl.managers;
import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import com.tm.enums.*;
import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.util.*;
import java.text.*;
import java.math.*;
public class EmployeeManager implements EmployeeManagerInterface 
{ 
//******************************************************************************
private Map<String, EmployeeInterface> employeeIDWiseEmployeesMap;
private Map<String, EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String, EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
//******************************************************************************
public static EmployeeManagerInterface employeeManager=null;
//******************************************************************************
private EmployeeManager() throws BLException //DC
{
this.populateDataStructures();
}
//******************************************************************************
public void populateDataStructures() throws BLException
{
this.employeeIDWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
try
{
EmployeeDAOInterface edao=new EmployeeDAO();
Set<EmployeeDTOInterface> dlEmployees=edao.getAll(); 
EmployeeInterface blEmployee=null;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation=null;
Set<EmployeeInterface> ets=null; //employeeTreeSet
for(EmployeeDTOInterface dlEmployee: dlEmployees)
{
blEmployee=new Employee();
blEmployee.setEmployeeID(dlEmployee.getEmployeeID());
blEmployee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
blEmployee.setGender((dlEmployee.getGender()=='M' || dlEmployee.getGender()=='m')?GENDER.MALE:GENDER.FEMALE);
blEmployee.setIsIndian(dlEmployee.getIsIndian());
blEmployee.setBasicSalary(dlEmployee.getBasicSalary());
blEmployee.setPANNumber(dlEmployee.getPANNumber());
blEmployee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIDWiseEmployeesMap.put(blEmployee.getEmployeeID().toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(blEmployee.getPANNumber().toUpperCase(),blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(blEmployee.getAadharCardNumber().toUpperCase(),blEmployee);
//adding in designationCodeWiseEmployeesMap
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(blEmployee);
}
this.employeesSet.add(blEmployee);
}
}catch(DAOException daoe)
{
BLException blexception=new BLException();
blexception.setGenericException(daoe.getMessage());
throw blexception;
}
}
//******************************************************************************
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}

//******************************************************************************
//actual methods
//******************************************************************************

public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeID=employee.getEmployeeID();
if(employeeID!=null)
{
employeeID=employeeID.trim();
if(employeeID.length()>0) blException.addPropertyException("ID","Employee id should be empty"); 
}
String name=employee.getName();
if(name==null) blException.addPropertyException("Name","Invalid name");
else
{
name=name.trim();
if(name.length()==0) blException.addPropertyException("Name","Invalid name");
}
DesignationInterface designation=employee.getDesignation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null) blException.addPropertyException("Designation","Invalid designation");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false) blException.addPropertyException("Designation","Invalid designation code");
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addPropertyException("Date of birth","Invalid date of birth");
char gender=employee.getGender();
//we can also check for empty value in case of gender
if(!(gender=='m'||gender=='M'||gender=='f'||gender=='F')) blException.addPropertyException("Gender","Invalid gender");
boolean isIndian=employee.getIsIndian();
if(!(isIndian==true||isIndian==false)) blException.addPropertyException("isIndian","Invalid Nationality");
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addPropertyException("Salary","Invalid Salary");
else
{
if(basicSalary.signum()==-1) blException.addPropertyException("Salary","Invalid salary");
}
String panNumber=employee.getPANNumber();
if(panNumber==null) blException.addPropertyException("PAN number","Invalid PAN number");
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addPropertyException("PAN number","Invalid PAN number");
else if(panNumberWiseEmployeesMap.containsKey(panNumber)) blException.addPropertyException("PAN number","PAN number already exists:"+panNumber);
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null) blException.addPropertyException("Aadhar card number","Invalid Aadhar card number");
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addPropertyException("Aadhar card number","Invalid aadhar card number");
else if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber)) blException.addPropertyException("Aadhar card number","Aadhar card number already exists:"+aadharCardNumber);
}
if(blException.hasExceptions()) throw blException;
try
{
//for addition in file
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setName(name);
edto.setDesignationCode(designation.getCode());
edto.setDateOfBirth(dateOfBirth);
edto.setGender((gender=='M' || gender=='m')?GENDER.MALE:GENDER.FEMALE);
edto.setIsIndian(isIndian);
edto.setBasicSalary(basicSalary);
edto.setPANNumber(panNumber);
edto.setAadharCardNumber(aadharCardNumber);
new EmployeeDAO().add(edto);
employee.setEmployeeID(edto.getEmployeeID());
//for addition in DS
EmployeeInterface blEmployee=new Employee();
employeeID=employee.getEmployeeID();
blEmployee.setEmployeeID(employeeID);
blEmployee.setName(name);
//we will not put clone of Designation object, we type-casted designationManager as it does not recognize getDSByDesignationCode()
blEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
blEmployee.setDateOfBirth((Date)dateOfBirth.clone()); //in DS clone of datOfBirth is kept
blEmployee.setGender((gender=='M' || gender=='m')?GENDER.MALE:GENDER.FEMALE);
blEmployee.setIsIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);
//adding in DS
this.employeeIDWiseEmployeesMap.put(employeeID.toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(), blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(), blEmployee);
//adding in designationCodeWiseEmployeesMap
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(blEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(blEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(blEmployee);
}
this.employeesSet.add(blEmployee);
}catch(DAOException daoe)
{
blException.setGenericException(daoe.getMessage());
throw blException;
}
}
//******************************************************************************

public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null) 
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeID=employee.getEmployeeID();
if(employeeID==null) blException.addPropertyException("ID","Invalid employee id");
else
{
employeeID=employeeID.trim();
if(employeeID.length()==0) blException.addPropertyException("ID","Invalid id");
if(employeeIDWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false)
{
blException.addPropertyException("ID","Invalid id, id does not exists");
throw blException;
}
}
String name=employee.getName();
if(name==null) blException.addPropertyException("Name","Invalid name");
else
{
name=name.trim();
if(name.length()==0) blException.addPropertyException("Name","Invalid name");
}
DesignationInterface designation=employee.getDesignation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null) blException.addPropertyException("Designation","Invalid designation");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false) blException.addPropertyException("Designation","Invalid designation code");
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addPropertyException("Date of birth","Invalid date");
char gender=employee.getGender();
if(!(gender=='m'||gender=='M'||gender=='f'||gender=='F')) blException.addPropertyException("Gender","Invalid gender");
boolean isIndian=employee.getIsIndian();
if(!(isIndian==true||isIndian==false)) blException.addPropertyException("isIndian","Invalid Nationality");
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addPropertyException("Salary","Invalid Salary");
if(basicSalary.signum()==-1) blException.addPropertyException("Salary","Invalid salary");
EmployeeInterface tmpEmployee=null;
String panNumber=employee.getPANNumber();
if(panNumber==null) blException.addPropertyException("PAN number","Invalid PAN number");
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addPropertyException("PAN number","Invalid PAN number");
else
{
//validating PAN number that it does not exists already
tmpEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(tmpEmployee!=null && (employeeID.equalsIgnoreCase(tmpEmployee.getEmployeeID())==false)) blException.addPropertyException("PAN number","PAN number already exists against id:"+tmpEmployee.getEmployeeID()); 
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null) blException.addPropertyException("Aadhar card number","Invalid Aadhar card number");
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addPropertyException("Aadhar card number","Invalid aadhar card number");
else
{
//validating Aadhar card number that it does not exists already
tmpEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(tmpEmployee!=null && (employeeID.equalsIgnoreCase(tmpEmployee.getEmployeeID())==false)) blException.addPropertyException("Aadhar card number","Aadhar card number already exists against id:"+tmpEmployee.getEmployeeID());
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setEmployeeID(employeeID.toUpperCase());
edto.setName(name);
edto.setDesignationCode(designation.getCode());
edto.setDateOfBirth(dateOfBirth);
edto.setGender((gender=='M' || gender=='m')?GENDER.MALE:GENDER.FEMALE);
edto.setIsIndian(isIndian);
edto.setBasicSalary(basicSalary);
edto.setPANNumber(panNumber);
edto.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface edao=new EmployeeDAO();
edao.update(edto);
//removing from DS
//extracting employee to be updated to delete from DS
EmployeeInterface blEmployee=employeeIDWiseEmployeesMap.get(employeeID.toUpperCase());
employeeIDWiseEmployeesMap.remove(employeeID.toUpperCase());
panNumberWiseEmployeesMap.remove(blEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(blEmployee.getAadharCardNumber().toUpperCase());
//removing from designationCodeWiseEmployeesMap
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(blEmployee.getDesignation().getCode());
ets.remove(blEmployee);
employeesSet.remove(blEmployee);
//setting details to be updated in object that will be addes in DS
blEmployee.setEmployeeID(employeeID);
blEmployee.setName(name);
//we will not put clone of Designation object, we type-casted designationManager as it does not recognize getDSByDesignationCode()
blEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
blEmployee.setDateOfBirth((Date)dateOfBirth.clone());
blEmployee.setGender((gender=='M' || gender=='m')?GENDER.MALE:GENDER.FEMALE);
blEmployee.setIsIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);
//adding in DS
this.employeeIDWiseEmployeesMap.put(employeeID.toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(), blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(), blEmployee);
//adding in designationCodeWiseEmployeesMap
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(blEmployee);
}
this.employeesSet.add(blEmployee);
}catch(DAOException daoe)
{
blException.setGenericException(daoe.getMessage());
throw blException;
}
}
//******************************************************************************

public void deleteEmployee(String employeeID) throws BLException
{
BLException blException=new BLException();
if(employeeID==null) blException.addPropertyException("ID","Invalid id");
else
{
employeeID=employeeID.trim();
if(employeeID.length()==0) blException.addPropertyException("ID","Invalid id");
else if(employeeIDWiseEmployeesMap.containsKey(employeeID.toUpperCase())==false) blException.addPropertyException("ID","Invalid id, ID does not exists");
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeInterface blEmployee=employeeIDWiseEmployeesMap.get(employeeID.toUpperCase());
//deleting From file
new EmployeeDAO().delete(employeeID);
//removing from DS
employeeIDWiseEmployeesMap.remove(employeeID.toUpperCase());
panNumberWiseEmployeesMap.remove(blEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(blEmployee.getAadharCardNumber().toUpperCase());
//removing from designationCodeWiseEmployeesMap
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(blEmployee.getDesignation().getCode());
ets.remove(blEmployee);
employeesSet.remove(blEmployee);
}catch(DAOException daoe)
{
blException.setGenericException(daoe.getMessage());
throw blException;
}
}

//******************************************************************************

public EmployeeInterface getEmployeeByEmployeeID(String employeeID) throws BLException
{
BLException blException=new BLException();
EmployeeInterface blEmployee=null;
if(employeeID==null) blException.addPropertyException("ID","Invalid id");
else
{
employeeID=employeeID.trim();
if(employeeID.length()==0) blException.addPropertyException("ID","Invalid id");
else 
{
blEmployee=employeeIDWiseEmployeesMap.get(employeeID.toUpperCase());
if(blEmployee==null) blException.addPropertyException("ID","Invalid id,ID does not exists");
}
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
DesignationInterface d=blEmployee.getDesignation();
e.setDesignation(d);
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
return e;
}
//******************************************************************************

public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
EmployeeInterface blEmployee=null;
if(panNumber==null) blException.addPropertyException("PAN number","Invalid PAN number");
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addPropertyException("ID","Invalid PAN number");
else
{
blEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(blEmployee==null) blException.addPropertyException("ID","Invalid PAN number,PAN number does not exists");
}
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
DesignationInterface d=blEmployee.getDesignation();
e.setDesignation(d);
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
return e;
}
//******************************************************************************

public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
EmployeeInterface blEmployee=null;
if(aadharCardNumber==null) blException.addPropertyException("Aadhar card number","Invalid aadhar card number");
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addPropertyException("Aadhar card number","Invalid aadhar card number");
else
{
blEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber);
if(blEmployee==null) blException.addPropertyException("Aadhar card number","Invalid aadhar card number, Aadhar card number does not exists");
}
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
DesignationInterface d=blEmployee.getDesignation();
e.setDesignation(d);
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
return e;
}
//******************************************************************************

public Set<EmployeeInterface> getEmployeeByName(String name) throws BLException
{
BLException blException=new BLException();
if(name==null) blException.addPropertyException("Name","Invalid name");
else
{
name=name.trim();
if(name.length()==0) blException.addPropertyException("Name","Invalid name");
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface e=null;
DesignationInterface d=null;
Set<EmployeeInterface> employees=new TreeSet<>();
for(EmployeeInterface blEmployee:employeesSet)
{
d=blEmployee.getDesignation();
if(blEmployee.getName().equalsIgnoreCase(name))
{
e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
e.setDesignation(d);
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
employees.add(e);
}
}
return employees;
}

//******************************************************************************

public Set<EmployeeInterface> getAllEmployees()
{
EmployeeInterface e=null;
DesignationInterface d=null;
Set<EmployeeInterface> employees=new TreeSet<>();
for(EmployeeInterface blEmployee:employeesSet)
{
e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
d=blEmployee.getDesignation();
e.setDesignation(d);
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
employees.add(e);
}
return employees;
}

//******************************************************************************

public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCodeWiseEmployeesMap.containsKey(designationCode)==false)
{
blException.addPropertyException("Code","Invalid code:"+designationCode);
throw blException;
}
EmployeeInterface e=null;
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(designationCode);
Set<EmployeeInterface> employees=new TreeSet<>();
for(EmployeeInterface blEmployee:ets)
{
e=new Employee();
e.setEmployeeID(blEmployee.getEmployeeID());
e.setName(blEmployee.getName());
e.setDesignation(blEmployee.getDesignation());
e.setDateOfBirth((Date)blEmployee.getDateOfBirth().clone());
e.setGender((blEmployee.getGender()=='m'||blEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(blEmployee.getIsIndian());
e.setBasicSalary(blEmployee.getBasicSalary());
e.setPANNumber(blEmployee.getPANNumber());
e.setAadharCardNumber(blEmployee.getAadharCardNumber());
employees.add(e);
}
return employees;
}
//******************************************************************************

public boolean employeeIDExists(String employeeID)
{
return employeeIDWiseEmployeesMap.containsKey(employeeID.toUpperCase());
}

//******************************************************************************

public boolean nameExists(String name) throws BLException
{
BLException blException=new BLException();
if(name==null) blException.addPropertyException("Name","Invalid name");
else
{
name=name.trim();
if(name.length()==0) blException.addPropertyException("Name","Invalid name");
}
if(blException.hasExceptions()) throw blException;
for(EmployeeInterface blEmployee:employeesSet)
{
if(blEmployee.getName().equalsIgnoreCase(name)) return true;
}
return false;
}

//******************************************************************************

public boolean panNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
//******************************************************************************

public boolean aadharCardNumberExists(String aadharCardNumber) 
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
//******************************************************************************

public boolean isDesignationAlloted(int designationCode)
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
//******************************************************************************

public int getEmployeeCount()
{
return employeesSet.size();
}
//******************************************************************************

public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
BLException blException=new BLException();
if(designationCode<=0)
{
blException.addPropertyException("Code","Invalid code: "+designationCode);
throw blException;
}
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets!=null) return ets.size();
return 0;
}
//******************************************************************************
}