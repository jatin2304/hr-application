import com.tm.hr.dl.exceptions.*;
import com.tm.enums.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeUpdate
{
public static void main (String args[])
{
try
{
String employeeID=args[0];
String name=args[1];
int designationCode=Integer.parseInt(args[2]);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(args[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender=args[4].charAt(0);
if(!(gender=='m' || gender=='M' ||gender=='f' || gender=='F'))
{
System.out.println("Invalid gender. choose(M/F)");
return;
}
boolean isIndian=Boolean.parseBoolean(args[5]);
BigDecimal basicSalary=new BigDecimal(args[6]);
String panNumber=args[7];
String aadharCardNumber=args[8];
EmployeeDTOInterface edto=new EmployeeDTO();
edto.setName(name);
edto.setDesignationCode(designationCode);
edto.setDateOfBirth(dateOfBirth);
if(gender=='m' || gender=='M') edto.setGender(GENDER.MALE);
else if(gender=='f' || gender=='F') edto.setGender(GENDER.FEMALE);
edto.setEmployeeID(employeeID);
edto.setIsIndian(isIndian);
edto.setBasicSalary(basicSalary);
edto.setPANNumber(panNumber);
edto.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface edao=new EmployeeDAO();
edao.update(edto);
System.out.printf("Employee: %s with id: %s updated successfully.\n", name,edto.getEmployeeID());
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}