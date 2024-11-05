import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeDoesExists
{
public static void main (String args[])
{
try
{
//String employeeID=args[0];
//String name=args[0];
String panNumber=args[0];
//String aadharCardNumber=args[0];
EmployeeDAOInterface edao=new EmployeeDAO();
boolean exists=false;
//exists=edao.employeeIDExists(employeeID);
//exists=edao.nameExists(name);
exists=edao.panNumberExists(panNumber);
//exists=edao.aadharCardNumberExists(aadharCardNumber);
System.out.println("PAN number exists: "+exists);
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}