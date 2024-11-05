import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationGetByTitle
{
public static void main(String args[])
{
String title=args[0];
try
{
DesignationDAOInterface ddao=new DesignationDAO();
DesignationDTOInterface ddto=ddao.getByTitle(title);
System.out.printf("Code: %d, Title: %s\n",ddto.getCode(),ddto.getTitle());


}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}
