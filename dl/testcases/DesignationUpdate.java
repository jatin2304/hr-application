import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationUpdate 
{
public static void main(String args[])
{
try
{
int code=Integer.parseInt(args[0]);
String title=args[1];
DesignationDTOInterface ddto=new DesignationDTO();
DesignationDAOInterface ddao=new DesignationDAO();
ddto.setCode(code);
ddto.setTitle(title);
ddao.update(ddto);
System.out.println("Designation "+title+ " updated for code: "+code);
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}