import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationDelete
{
public static void main(String args[])
{
int code=Integer.parseInt(args[0]);
try
{
DesignationDAOInterface ddao=new DesignationDAO();
ddao.delete(code);
System.out.println("Title againstCode: "+code+" deleted.");
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}
