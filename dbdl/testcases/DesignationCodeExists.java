import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationCodeExists
{
public static void main(String args[])
{
int code=Integer.parseInt(args[0]);
try
{
DesignationDAOInterface ddao=new DesignationDAO();
boolean flag=ddao.codeExists(code);
if(flag==true) System.out.println("Code: "+code+" exists.");
else System.out.println("Code: "+code+" does not exists.");
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}
