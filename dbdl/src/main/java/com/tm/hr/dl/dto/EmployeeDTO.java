package com.tm.hr.dl.dto;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.io.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
String employeeID;
String name;
int designationCode;
Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
public EmployeeDTO()
{
this.employeeID="";
this.name="";
this.designationCode=0;
this.dateOfBirth=null;
this.gender=' ';
boolean isIndian=false;
this.basicSalary=null;
this.panNumber="";
this.aadharCardNumber="";
}
public void setEmployeeID(String employeeID)
{
this.employeeID=employeeID;
}
public String getEmployeeID()
{
return this.employeeID;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setDateOfBirth(Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender='M';
else this.gender='F';
}
public char getGender()
{
return this.gender;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian=isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setBasicSalary(BigDecimal basicSalary)
{
this.basicSalary=basicSalary;
}
public BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setPANNumber(String panNumber)
{
this.panNumber=panNumber;
}
public String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(String aadharCardNumber)
{
this.aadharCardNumber=aadharCardNumber;
}
public String getAadharCardNumber()
{
return this.aadharCardNumber;
}
public boolean equals(Object other)
{
if(!(other instanceof EmployeeDTOInterface)) return false;
EmployeeDTOInterface edto=(EmployeeDTO)other;
return this.employeeID.equalsIgnoreCase(edto.getEmployeeID());
}
public int compareTo(EmployeeDTOInterface other)
{
return this.employeeID.compareTo(other.getEmployeeID());
}
public int hashCode()
{
return this.employeeID.toUpperCase().hashCode();
}
}
