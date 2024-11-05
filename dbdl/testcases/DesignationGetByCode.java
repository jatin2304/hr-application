import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
public class DesignationGetByCode
{
public static void main(String args[])
{
int code=Integer.parseInt(args[0]);
try
{
DesignationDAOInterface ddao=new DesignationDAO();
DesignationDTOInterface ddto=ddao.getByCode(code);
System.out.printf("Code: %d, Title: %s\n",ddto.getCode(),ddto.getTitle());


}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}
