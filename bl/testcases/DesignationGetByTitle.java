import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.managers.*;
import java.util.*;
public class DesignationGetByTitle
{
public static void main(String args[])
{
String title=args[0];
try
{
DesignationInterface designation=null;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designation=designationManager.getDesignationByTitle(title);
System.out.println("Code: "+designation.getCode()+", Title: "+designation.getTitle());
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