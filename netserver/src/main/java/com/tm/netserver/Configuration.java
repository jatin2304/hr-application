package com.tm.netclient;
import java.io.*;
import java.io.*;
import org.xml.sax.*;
import javax.xml.xpath.*;
import com.tm.netcommon.exceptions.*;
class Configuration
{
private static int port=-1;
private static boolean malformed, missing;
static //static block so that it is called as the class is loaded 
{
try
{
File file=new File("server.xml");
if(file.exists())
{
InputSource inputSource=new InputSource("server.xml");
XPath xpath=XPathFactory.newInstance().newXPath();
String port=xpath.evaluate("//server/@port",inputSource);
Configuration.port=Integer.valueOf(port);
Configuration.host=host;
}
else missing=true;
}catch(Exception e)
{
malformed=true;
}
}
public static int getPort() throws NetworkException
{
if(missing) throw new NetworkException("server.xml file is missing , read documentation");
if(malformed) throw new NetworkException("File server.xml not configured accoreding to the documenation");
if(port<0 || port>49151) throw new NetworkException("File server.xml not configured accoreding to the documenation");
return Configuration.port;
}
}	