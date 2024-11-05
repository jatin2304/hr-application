import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import java.io.*;
import java.util.*;
public class DesignationGetAll
{
public static void main(String args[])
{
try
{
DesignationDAOInterface ddao=new DesignationDAO();
Set<DesignationDTOInterface> designations=ddao.getAll();
System.out.println("1");

designations.forEach((s)-> {System.out.printf("Code: %d, Title: %s\n",s.getCode(), s.getTitle());});
}catch(DAOException daoe)
{
System.out.println(daoe.getMessage());
}
}
}