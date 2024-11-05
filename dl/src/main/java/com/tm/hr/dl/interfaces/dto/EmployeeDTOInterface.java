package com.tm.hr.dl.interfaces.dto;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.io.*;
public interface EmployeeDTOInterface extends Comparable<EmployeeDTOInterface>, Serializable
{
public void setEmployeeID(String EmployeeID);
public String getEmployeeID();
public void setName(String name);
public String getName();
public void setDesignationCode(int designationCode);
public int getDesignationCode();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public void setIsIndian(boolean isIndian);
public boolean getIsIndian();
public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();
public void setPANNumber(String panNumber);
public String getPANNumber();
public void setAadharCardNumber(String aadharCardNumber);
public String getAadharCardNumber();
}





