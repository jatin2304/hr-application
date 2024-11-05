import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.managers.*;
import java.util.*;
public class DesignationCodeExists
{
public static void main(String args[])
{
int code=Integer.parseInt(args[0]);
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
System.out.println("code "+code+" exists: "+designationManager.designationCodeExists(code));
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