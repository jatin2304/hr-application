import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeIsDesignationCodeAlloted
{
public static void main (String args[])
{
try
{
int designationCode=Integer.parseInt(args[0]);
EmployeeDAOInterface edao=new EmployeeDAO();
boolean exists=false;
exists=edao.isDesignationAlloted(designationCode);
System.out.println("Designation code alloted: "+exists);
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}