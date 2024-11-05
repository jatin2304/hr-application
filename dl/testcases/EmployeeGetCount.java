import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetCount
{
public static void main (String args[])
{
try
{
EmployeeDAOInterface edao=new EmployeeDAO();
int count=edao.getCount();
System.out.println(count);
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}

}
}