import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationTitleExists
{
public static void main(String args[])
{
String title=args[0];
try
{
DesignationDAOInterface ddao=new DesignationDAO();
boolean flag=ddao.titleExists(title);
if(flag==true) System.out.println("Title: "+title+" exists.");
else System.out.println("Title: "+title+" does not exists.");
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}
