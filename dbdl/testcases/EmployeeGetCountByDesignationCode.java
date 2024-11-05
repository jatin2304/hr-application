import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetCountByDesignationCode
{
public static void main (String args[])
{
try
{
int designationCode=Integer.parseInt(args[0]);
EmployeeDAOInterface edao=new EmployeeDAO();
int count=edao.getCountByDesignationCode(designationCode);
System.out.println("Number of Employees with Designation code "+designationCode+": "+count);
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}