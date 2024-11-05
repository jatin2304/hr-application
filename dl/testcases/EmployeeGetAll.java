import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.math.*;
import java.text.*;
import java.util.*;
public class EmployeeGetAll
{
public static void main (String args[])
{
try
{
EmployeeDAOInterface edao=new EmployeeDAO();
Set<EmployeeDTOInterface> employees=edao.getAll();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
//if(employees==null) System.out.println("No employee");
employees.forEach((edto)-> 
{ 
System.out.println("Employee Details:");
System.out.println("id: "+edto.getEmployeeID());
System.out.println("Name: "+edto.getName());
System.out.println("Designation code: "+edto.getDesignationCode());
System.out.println("Date of birth: "+sdf.format(edto.getDateOfBirth()));
System.out.println("Gender: "+edto.getGender());
System.out.println("Indian: "+String.valueOf(edto.getIsIndian()));
System.out.println("Salary: "+edto.getBasicSalary().toPlainString());
System.out.println("PAN number: "+edto.getPANNumber());
System.out.println("Aadhar card number: "+edto.getAadharCardNumber());
System.out.println();
});
//for(EmployeeDTOInterface edto:employees) System.out.printf("Employee details:\nID: %s\nName: %s\nDesignation code: %d\nDate of birth: %s\nGender: %c\nIndian: %s\nSalary: %s\nPAN number: %s\nAadhar card number: %s\n",edto.getEmployeeID(),edto.getName(),edto.getDesignationCode(),sdf.format(edto.getDateOfBirth()),edto.getGender(),String.valueOf(edto.getIsIndian()),edto.getBasicSalary().toPlainString(),edto.getPANNumber(),edto.getAadharCardNumber());
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}

}
}