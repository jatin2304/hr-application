import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationAdd 
{
public static void main(String args[])
{
try
{
String title=args[0];
DesignationDTOInterface ddto=new DesignationDTO();
DesignationDAOInterface ddao=new DesignationDAO();
ddto.setTitle(title);
ddao.add(ddto);
System.out.println("Designation "+title+ " added");
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}