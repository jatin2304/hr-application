import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetByDesignationCode
{
public static void main (String args[])
{
try
{
int designationCode=Integer.parseInt(args[0]);
EmployeeDAOInterface edao=new EmployeeDAO();
Set<EmployeeDTOInterface> employees=edao.getByDesignationCode(designationCode);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//if(employees==null) System.out.println("No employee");
employees.forEach((edto)-> { System.out.printf("Employee details:\nID: %s\nName: %s\nDesignation code: %d\nDate of birth: %s\nGender: %c\nIndian: %s\nSalary: %s\nPAN number: %s\nAadhar card number: %s\n",edto.getEmployeeID(),edto.getName(),edto.getDesignationCode(),sdf.format(edto.getDateOfBirth()),edto.getGender(),String.valueOf(edto.getIsIndian()),edto.getBasicSalary().toPlainString(),edto.getPANNumber(),edto.getAadharCardNumber());});
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}