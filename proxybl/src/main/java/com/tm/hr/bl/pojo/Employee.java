package com.tm.hr.bl.pojo;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.io.*;
public class Employee implements EmployeeInterface
{
String employeeID;
String name;
DesignationInterface designation;
Date dateOfBirth;
char gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
public Employee()
{
this.employeeID="";
this.name="";
this.designation=null;
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
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
}
public DesignationInterface getDesignation()
{
return this.designation;
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
if(!(other instanceof EmployeeInterface)) return false;
EmployeeInterface edto=(Employee)other;
return this.employeeID.equalsIgnoreCase(edto.getEmployeeID());
}
public int compareTo(EmployeeInterface other)
{
return this.employeeID.compareTo(other.getEmployeeID());
}
public int hashCode()
{
return this.employeeID.toUpperCase().hashCode();
}
}
