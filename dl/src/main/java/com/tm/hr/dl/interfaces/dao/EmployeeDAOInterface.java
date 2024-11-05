package com.tm.hr.dl.interfaces.dao;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.util.*;
public interface EmployeeDAOInterface 
{ 
public void add(EmployeeDTOInterface edto) throws DAOException;
public void update(EmployeeDTOInterface edto) throws DAOException;
public void delete(String employeeID) throws DAOException;
public EmployeeDTOInterface getByEmployeeID(String employeeID) throws DAOException;
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException;
public Set<EmployeeDTOInterface> getByName(String name) throws DAOException;
public Set<EmployeeDTOInterface> getAll() throws DAOException;
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException;
public boolean employeeIDExists(String employeeID) throws DAOException;
public boolean nameExists(String name) throws DAOException;
public boolean panNumberExists(String panNumber) throws DAOException;
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException;
public boolean isDesignationAlloted(int designationCode) throws DAOException;
public int getCount() throws DAOException;
public int getCountByDesignationCode(int designationCode) throws DAOException;
}