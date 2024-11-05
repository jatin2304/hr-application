import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.managers.*;
import com.tm.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeRemove
{
public static void main(String args[])
{
try
{
String employeeID=args[0];
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.deleteEmployee(employeeID);
System.out.println("Employee removed with id:"+employeeID);
}catch(BLException ble)
{
if(ble.hasGenericException()) System.out.println(ble.getGenericException());
List<String> properties=ble.getProperties();
for(String property:properties) System.out.println(ble.getPropertyException(property));
}
}
}
