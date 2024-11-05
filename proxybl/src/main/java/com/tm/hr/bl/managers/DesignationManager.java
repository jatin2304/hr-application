package com.tm.hr.bl.managers;
import com.tm.hr.bl.exceptions.*;
import com.tm.hr.bl.interfaces.pojo.*;
import com.tm.hr.bl.interfaces.managers.*;
import com.tm.hr.bl.pojo.*;
import java.util.*;
import com.tm.netclient.*;
import com.tm.netcommon.*;
import com.tm.netcommon.exceptions.*; 
public class DesignationManager implements DesignationManagerInterface
{
//no need of data structures creation 
private DesignationManager() throws BLException // made private so that from outside its object can't be created
{
}
private static DesignationManager designationManager=null;
public void populateDataStructures() throws BLException
{
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
if(blexception.hasExceptions()) throw blexception;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.ADD_DESIGNATION));
request.setArgument(designation);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
DesignationInterface d=(DesignationInterface)response.getResult();//at server side in response designation object should be passed so that code can be set
designation.setCode(d.getCode());//setting the code that came in response in the requested designation object
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
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
if(blexception.hasExceptions()) throw blexception;
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.UPDATE_DESIGNATION));
request.setArgument(designation);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
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
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.REMOVE_DESIGNATION));
request.setArgument(new Integer(code));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
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
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE));
request.setArgument(new Integer(code));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
DesignationInterface designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
throw blexception;
}
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
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE));
request.setArgument(title);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
DesignationInterface designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
throw blexception;
}
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
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));
request.setArgument(new Integer(code));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
Boolean exists=(Boolean)response.getResult();
return exists;
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
throw blexception;
}
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
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));
request.setArgument(title);
NetworkClient client=new NetworkClient();
Response response=client.send(request);
if(response.hasException())
{
blexception=(BLException)response.getException();
throw blexception;
}
Boolean exists=(Boolean)response.getResult();
return exists;
}catch(NetworkException ne)
{
blexception.setGenericException(ne.getMessage());
throw blexception;
}
}

//******************************************************************************

public int getDesignationCount()
{
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
Integer count=(Integer)response.getResult();
return count.intValue(); //no need of intValue() as implicit unboxing will be done
}catch(NetworkException ne) 
{
throw new RuntimeException(ne.getMessage()); //as we cannot throw BLException
}
}

//******************************************************************************

public Set<DesignationInterface> getAllDesignations()
{
try
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.Designation.getAction(Managers.Designation.GET_DESIGNATIONS));
NetworkClient client=new NetworkClient();
Response response=client.send(request);
Set<DesignationInterface> designations=(Set<DesignationInterface>)response.getResult();
return designations;
}catch(NetworkException ne)
{
throw new RuntimeException(ne.getMessage()); //as we cannot throw BLException
}
}
}