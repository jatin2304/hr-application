import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.managers.*;
import java.util.*;
public class DesignationRemove
{
public static void main(String args[])
{
int code=Integer.parseInt(args[0]);
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
System.out.println("Designation with code: "+code+" removed.");
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