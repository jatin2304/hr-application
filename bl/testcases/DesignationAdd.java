import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.managers.*;
import java.util.*;
public class DesignationAdd
{
public static void main(String args[])
{
String title=args[0];
try
{
DesignationInterface designation=new Designation();
designation.setTitle(title);
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designation title "+title+" added with code: "+designation.getCode());
}catch(BLException ble)
{
if(ble.hasExceptions()) 
{
if(ble.hasGenericException()) System.out.println(ble.getGenericException());
List<String> properties=ble.getProperties();
for(String property: properties) System.out.println(ble.getPropertyException(property));
}
}
}
}