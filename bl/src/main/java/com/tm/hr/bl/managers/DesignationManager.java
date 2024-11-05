package com.tm.hr.bl.managers;
import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import com.tm.hr.dl.exceptions.*;
import com.tm.hr.dl.interfaces.dto.*;
import com.tm.hr.dl.interfaces.dao.*;
import com.tm.hr.dl.dto.*;
import com.tm.hr.dl.dao.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
//data structures creation 
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String, DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationsSet;
private DesignationManager() throws BLException // made private so that from outside its object can't be created
{
populateDataStructures();
}
private static DesignationManager designationManager=null;
public void populateDataStructures() throws BLException
{
codeWiseDesignationMap=new HashMap<>();
titleWiseDesignationMap=new HashMap<>();
designationsSet=new TreeSet<>();
try
{
DesignationDAOInterface ddao=new DesignationDAO();
Set<DesignationDTOInterface> dlDesignationsSet=ddao.getAll(); // all designations in file are fetched and stored in Set 
DesignationInterface blDesignation; 
for(DesignationDTOInterface ddto: dlDesignationsSet)
{
blDesignation=new Designation();
blDesignation.setCode(ddto.getCode());
blDesignation.setTitle(ddto.getTitle());
this.codeWiseDesignationMap.put(new Integer(blDesignation.getCode()), blDesignation); //boxing
this.titleWiseDesignationMap.put(blDesignation.getTitle().toUpperCase(), blDesignation);
this.designationsSet.add(blDesignation);
}
}catch(DAOException daoe)
{
BLException blexception=new BLException();
blexception.setGenericException(daoe.getMessage());
throw blexception;
}
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
//used for Singleton approach so that only one object is created of DesignationManager class and only once DS are created
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
//******************************************************************************
//actual methods
//******************************************************************************

public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blexception=new BLException();
if(designation==null)
{
blexception.setGenericException("Designation required.");
throw blexception;
}
int code=designation.getCode();
if(code!=0) blexception.addPropertyException("Code", "Code should be zero");
String title=designation.getTitle();
if(title==null) 
{
blexception.addPropertyException("Title", "Title is null");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blexception.addPropertyException("Title", "Invalid title");
}
if(title.length()>0 && titleWiseDesignationMap.containsKey(title.toUpperCase())) blexception.addPropertyException("Title", "Title: "+title+" already exists.");
if(blexception.hasExceptions()) throw blexception;
try
{
DesignationDTOInterface ddto=new DesignationDTO();
ddto.setTitle(title);
DesignationDAOInterface ddao=new DesignationDAO();
ddao.add(ddto); // adding in file 
int dsCode=ddto.getCode();
designation.setCode(dsCode); // code that is alloted to the title in designation object is set in it
String dsTitle=ddto.getTitle();
DesignationInterface blDesignation=new Designation(); // clone object of Designation is created to be added in the data structures
blDesignation.setCode(dsCode);
blDesignation.setTitle(dsTitle);
codeWiseDesignationMap.put(dsCode, blDesignation);
titleWiseDesignationMap.put(dsTitle.toUpperCase(), blDesignation);
designationsSet.add(blDesignation);
}catch(DAOException daoe)
{
blexception.setGenericException(daoe.getMessage());
throw blexception;
}
}

//******************************************************************************

public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blexception=new BLException();
if(designation==null)
{
blexception.setGenericException("Designation required.");
throw blexception;
}
int code=designation.getCode();
if(code<=0) blexception.addPropertyException("Code", "Invalid code: "+code);
if(code>0 && codeWiseDesignationMap.containsKey(code)==false)
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
String title=designation.getTitle();
if(title==null) 
{
blexception.addPropertyException("Title", "Title is null");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blexception.addPropertyException("Title", "Invalid title");
}
if(title.length()>0) // for checking title is not existing against any other code
{
DesignationInterface d=titleWiseDesignationMap.get(title.toUpperCase());
if(d!=null && code!=d.getCode()) blexception.addPropertyException("Title", "Title: "+title+" already exists");
}
if(blexception.hasExceptions()) throw blexception;

try
{
DesignationInterface blDesignation=codeWiseDesignationMap.get(code); // elemenet to be deleted from set
DesignationDTOInterface ddto=new DesignationDTO();
DesignationDAOInterface ddao=new DesignationDAO();
ddto.setCode(code); // setting code and new tile in ddto to update in the DL
ddto.setTitle(title);
ddao.update(ddto); //update of DL 

//remove from DS
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(blDesignation.getTitle().toUpperCase()); //deleted from title wise map
designationsSet.remove(blDesignation); // removed from set

//add in DS
blDesignation.setTitle(title); //new title set 
codeWiseDesignationMap.put(code, blDesignation); //updated in code wise map
titleWiseDesignationMap.put(title.toUpperCase(), blDesignation); //added in title wise map on the basis of new title
designationsSet.add(blDesignation); //added new Designation class object in set
}catch (DAOException daoe)
{
blexception.setGenericException(daoe.getMessage());
throw blexception;
}
}

//******************************************************************************

public void removeDesignation(int code) throws BLException
{
BLException blexception=new BLException();
if(code<=0) 
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
if(codeWiseDesignationMap.containsKey(code)==false)
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
try
{
DesignationDAOInterface ddao=new DesignationDAO();
DesignationInterface blDesignation=codeWiseDesignationMap.get(code);
ddao.delete(code);
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(blDesignation.getTitle().toUpperCase());
designationsSet.remove(blDesignation);
}catch(DAOException daoe)
{
blexception.setGenericException(daoe.getMessage());
throw blexception;
}
}

//******************************************************************************

public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blexception=new BLException();
if(code<=0) 
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
DesignationInterface blDesignation=codeWiseDesignationMap.get(code);
if(blDesignation==null)
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
DesignationInterface designation=new Designation();
designation.setCode(blDesignation.getCode());
designation.setTitle(blDesignation.getTitle());
return designation;
}

//******************************************************************************
//only for internal use, for returning original designation object
//no modifier is used so method is not accessible outside package
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation=codeWiseDesignationMap.get(code);
return designation;
}

//******************************************************************************

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blexception=new BLException();
if(title==null) 
{
blexception.addPropertyException("Title", "Invalid title: "+title);
throw blexception;
}
title=title.trim();
if(title.length()==0) 
{
blexception.addPropertyException("Title", "Invalid title: "+title);
throw blexception;
}
DesignationInterface blDesignation=titleWiseDesignationMap.get(title);
if(blDesignation==null)
{
blexception.addPropertyException("Title", "Invalid title: "+title);
throw blexception;
}
DesignationInterface designation=new Designation();
designation.setCode(blDesignation.getCode());
designation.setTitle(blDesignation.getTitle());
return designation;
}

//******************************************************************************

public boolean designationCodeExists(int code) throws BLException
{
BLException blexception=new BLException();
if(code<=0) 
{
blexception.addPropertyException("Code", "Invalid code: "+code);
throw blexception;
}
return codeWiseDesignationMap.containsKey(code);
}

//******************************************************************************

public boolean designationTitleExists(String title) throws BLException
{
BLException blexception=new BLException();
if(title==null) 
{
blexception.addPropertyException("title", "Invalid title: "+title);
throw blexception;
}
title=title.trim();
if(title.length()==0) 
{
blexception.addPropertyException("Title", "Invalid title: "+title);
throw blexception;
}
return titleWiseDesignationMap.containsKey(title.toUpperCase());
}

//******************************************************************************

public int getDesignationCount()
{
return codeWiseDesignationMap.size();
}

//******************************************************************************

public Set<DesignationInterface> getAllDesignations()
{
Set<DesignationInterface> designations=new TreeSet<>();
DesignationInterface d=null;
for(DesignationInterface blDesignation: designationsSet)
{
d=new Designation();
d.setCode(blDesignation.getCode());
d.setTitle(blDesignation.getTitle());
designations.add(d);
}
return designations;
}
}