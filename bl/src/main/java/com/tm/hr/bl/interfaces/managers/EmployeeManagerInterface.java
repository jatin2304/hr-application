package com.tm.hr.bl.interfaces.managers;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface 
{ 
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void deleteEmployee(String employeeID) throws BLException;
public EmployeeInterface getEmployeeByEmployeeID(String employeeID) throws BLException;
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException;
public Set<EmployeeInterface> getEmployeeByName(String name) throws BLException;
public Set<EmployeeInterface> getAllEmployees();
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException;
public boolean employeeIDExists(String employeeID);
public boolean nameExists(String name) throws BLException;
public boolean panNumberExists(String panNumber);
public boolean aadharCardNumberExists(String aadharCardNumber);
public boolean isDesignationAlloted(int designationCode);
public int getEmployeeCount();
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException;
}