import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.managers.*;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeGetAll
{
public static void main(String args[])
{
try
{
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees=employeeManager.getAllEmployees();
DesignationInterface d=null;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeInterface employee:employees)
{
System.out.println("Employee id:"+employee.getEmployeeID());
System.out.println("Name:"+employee.getName());
d=employee.getDesignation();
System.out.println("Designation code:"+d.getCode());
System.out.println("Date of birth:"+sdf.format(employee.getDateOfBirth()));
System.out.println("Gender:"+employee.getGender());
System.out.println("Indian:"+employee.getIsIndian());
System.out.println("Salary:"+employee.getBasicSalary().toPlainString());
System.out.println("PAN number:"+employee.getPANNumber());
System.out.println("Aadhar card number:"+employee.getAadharCardNumber());
}
}catch(BLException ble)
{
if(ble.hasGenericException()) System.out.println(ble.getGenericException());
List<String> properties=ble.getProperties();
for(String property:properties) System.out.println(ble.getPropertyException(property));
}
}
}
