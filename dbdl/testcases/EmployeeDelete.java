import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.util.*;
public class EmployeeDelete
{
public static void main (String args[])
{
try
{
String employeeID=args[0];
EmployeeDAOInterface edao=new EmployeeDAO();
edao.delete(employeeID);
System.out.printf("Employee with ID "+employeeID+" removed successfully"); 
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}