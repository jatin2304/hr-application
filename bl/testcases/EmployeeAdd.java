import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.managers.*;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeAdd
{
public static void main(String args[])
{
try
{
String name=args[0];
int designationCode=Integer.parseInt(args[1]);
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation=designationManager.getDesignationByCode(designationCode);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(args[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender=args[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(args[4]);
BigDecimal basicSalary=new BigDecimal(args[5]);
String panNumber=args[6];
String aadharCardNumber=args[7];
EmployeeInterface employee=new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender((gender=='m'||gender=='M')?GENDER.MALE : GENDER.FEMALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee added with id:"+employee.getEmployeeID());
}catch(BLException ble)
{
if(ble.hasGenericException()) System.out.println(ble.getGenericException());
List<String> properties=ble.getProperties();
for(String property:properties) System.out.println(ble.getPropertyException(property));
}
}
}
