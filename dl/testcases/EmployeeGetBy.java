import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetBy
{
public static void main (String args[])
{
try
{
String employeeID=args[0];
//String name=args[0];
//String panNumber=args[0];
//String aadharCardNumber=args[0];
EmployeeDAOInterface edao=new EmployeeDAO();
EmployeeDTOInterface edto=edao.getByEmployeeID(employeeID);
//EmployeeDTOInterface edto=edao.getByName(name);
//EmployeeDTOInterface edto=edao.getByPANNumber(panNumber);
//EmployeeDTOInterface edto=edao.getByAadgharCardNumber(aadharCardNumber);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
if(edto==null) System.out.println("Invalid Employee ID");
else System.out.printf("Employee details:\nID: %s\nName: %s\nDesignation code: %d\nDate of birth: %s\nGender: %c\nIndian: %s\nSalary: %s\nPAN number: %s\nAadhar card number: %s\n",edto.getEmployeeID(),edto.getName(),edto.getDesignationCode(),sdf.format(edto.getDateOfBirth()),edto.getGender(),String.valueOf(edto.getIsIndian()),edto.getBasicSalary().toPlainString(),edto.getPANNumber(),edto.getAadharCardNumber());
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}