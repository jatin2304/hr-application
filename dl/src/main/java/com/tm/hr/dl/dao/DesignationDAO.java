package com.tm.hr.dl.dao;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;
public class DesignationDAO implements DesignationDAOInterface
{
private static final String DATA_FILE="designation.data";

//******************************************************************************

public void add(DesignationDTOInterface ddto) throws DAOException
{
if(ddto==null) throw new DAOException("Designation cannot be null");
String title=ddto.getTitle();
if(title==null) throw new DAOException("Designation cannot be null");
title=title.trim();
if(title.length()==0) throw new DAOException("Designation cannot be null");
int lastGeneratedCode=0, recordCount=0;
try
{
File file=new File(DATA_FILE);
RandomAccessFile raf= new RandomAccessFile(file,"rw");
String lastGeneratedCodeString, recordCountString;
if(raf.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString="0";
while(recordCountString.length()<10) recordCountString+=" ";
raf.writeBytes(lastGeneratedCodeString+"\n");
raf.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
recordCount=Integer.parseInt(raf.readLine().trim());
}
int fCode;
String fTitle;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine());
fTitle=raf.readLine();
if(title.equalsIgnoreCase(fTitle))
{
raf.close();
throw new DAOException("designation title already exists");
}
}
lastGeneratedCode++;
recordCount++;
int code=lastGeneratedCode;
raf.writeBytes(code+"\n");
raf.writeBytes(title+"\n");
ddto.setCode(code);
raf.seek(0);
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10) recordCountString+=" ";
raf.writeBytes(lastGeneratedCodeString+"\n");
raf.writeBytes(recordCountString+"\n");
raf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public void update(DesignationDTOInterface ddto) throws DAOException
{
if(ddto==null) throw new DAOException("Designation is null");
int code=ddto.getCode();
String title=ddto.getTitle();
if(code<=0) throw new DAOException("Invalid code: "+code);
if(title==null) throw new DAOException("Invalid title: "+title);
title=title.trim();
if(title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("Invalid title");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
throw new DAOException("Invalid title: "+title);
}
int lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
if(lastGeneratedCode<code)
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
raf.readLine();
int fCode;
String fTitle;
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(fCode==code)
{
found=true;
}
if(fCode!=code && fTitle.equalsIgnoreCase(title))
{
raf.close();
throw new DAOException(title+" title already exists against code: "+ fCode);
}
}
if(found==false)
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
String lastGeneratedCodeString, recordCountString;
File tmpFile=new File("tmp.data");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpraf=new RandomAccessFile(tmpFile,"rw");
raf.seek(0);
tmpraf.seek(0);
tmpraf.writeBytes(raf.readLine()+"\n");
tmpraf.writeBytes(raf.readLine()+"\n");
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(fCode!=code)
{
tmpraf.writeBytes(String.valueOf(fCode)+"\n");
tmpraf.writeBytes(fTitle+"\n");
}
else
{
tmpraf.writeBytes(String.valueOf(code)+"\n");
tmpraf.writeBytes(title+"\n");
}
}
tmpraf.seek(0);
raf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length())
{
raf.writeBytes(tmpraf.readLine()+"\n");
}
raf.setLength(tmpraf.length());
tmpraf.setLength(0);
raf.close();
tmpraf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("Invalid code");
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0) 
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
int lastGeneratedCode=Integer.parseInt(raf.readLine().trim());
if(lastGeneratedCode<code)
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
int recordCount=Integer.parseInt(raf.readLine().trim());
int fCode=0;
String fTitle="";
boolean found=false;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
//verifying whether code to be deleted is alloted to any employee, if yes then exception will be raised
if(new EmployeeDAO().isDesignationAlloted(code))
{
raf.close();
throw new DAOException("Code " +fCode+" exists against title: "+fTitle+". Unable to delete.");
}
String lastGeneratedCodeString, recordCountString;
File tmpFile=new File("tmp.data");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpraf=new RandomAccessFile(tmpFile,"rw");
raf.seek(0);
tmpraf.seek(0);
tmpraf.writeBytes(raf.readLine()+"\n");
tmpraf.writeBytes(raf.readLine()+"\n");
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(fCode!=code)
{
tmpraf.writeBytes(String.valueOf(fCode)+"\n");
tmpraf.writeBytes(fTitle+"\n");
}
}
tmpraf.seek(0);
raf.seek(0);
while(tmpraf.getFilePointer()<tmpraf.length())
{
raf.writeBytes(tmpraf.readLine()+"\n");
}
raf.setLength(tmpraf.length());
tmpraf.setLength(0);
raf.seek(0);
raf.readLine();
recordCountString=String.valueOf(recordCount-1);
while(recordCountString.length()<10) recordCountString+=" ";
raf.writeBytes(recordCountString+"\n");
raf.close();
tmpraf.close();
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
File file=new File(DATA_FILE);
if(file==null) return designations;
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
return designations;
}
raf.readLine();
raf.readLine();
int fCode;
String fTitle;
DesignationDTOInterface ddto;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
ddto=new DesignationDTO();
ddto.setCode(fCode);
ddto.setTitle(fTitle);
designations.add(ddto);
}
raf.close();
return designations;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("Invalid code: "+code);
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
throw new DAOException("Invalid code: "+code);
}
raf.readLine();
raf.readLine();
int fCode;
String fTitle;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(code==fCode)
{
DesignationDTOInterface ddto=new DesignationDTO();
ddto.setCode(fCode);
ddto.setTitle(fTitle);
raf.close();
return ddto;
}
}
raf.close();
throw new DAOException("Invalid code: "+code);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null) throw new DAOException("Invalid title: "+title);
title=title.trim();
if(title.length()==0) throw new DAOException("Invalid title: "+title);
try
{
File file=new File(DATA_FILE);
if(file==null) throw new DAOException("Invalid title: "+title);
RandomAccessFile raf=new RandomAccessFile(file,"rw");
if(raf.length()==0)
{
raf.close();
throw new DAOException("Invalid title: "+title);
}
raf.readLine();
raf.readLine();
int fCode;
String fTitle;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(title.equalsIgnoreCase(fTitle))
{
DesignationDTOInterface ddto=new DesignationDTO();
ddto.setCode(fCode);
ddto.setTitle(fTitle);
raf.close();
return ddto;
}
}
raf.close();
throw new DAOException("Invalid title: "+title);
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file=new File(DATA_FILE);
if(file==null) return false;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
int recordCount=Integer.parseInt(raf.readLine().trim());
if(recordCount==0)
{
raf.close();
return false;
}
int fCode;
String fTitle;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
if(code==fCode)
{
raf.close();
return true;
}
fTitle=raf.readLine();
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public boolean titleExists(String title) throws DAOException
{
if(title==null) return false;
title=title.trim();
if(title.length()==0) return false;
try
{
File file=new File(DATA_FILE);
if(file==null) return false;
RandomAccessFile raf=new RandomAccessFile(file, "rw");
if(raf.length()==0) 
{
raf.close();
return false;
}
raf.readLine();
int recordCount=Integer.parseInt(raf.readLine().trim());
if(recordCount==0)
{
raf.close();
return false;
}
int fCode;
String fTitle;
while(raf.getFilePointer()<raf.length())
{
fCode=Integer.parseInt(raf.readLine().trim());
fTitle=raf.readLine();
if(title.equalsIgnoreCase(fTitle))
{
raf.close();
return true;
}
}
raf.close();
return false;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

public int getCount() throws DAOException
{
try
{
File file=new File(DATA_FILE);
if(file==null) return 0;
RandomAccessFile raf=new RandomAccessFile(DATA_FILE,"rw");
if(raf.length()==0) 
{
raf.close();
return 0;
}
raf.readLine();
int recordCount=Integer.parseInt(raf.readLine().trim());
return recordCount;
}catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());
}
}

//******************************************************************************

}