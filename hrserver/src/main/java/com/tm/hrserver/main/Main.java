package com.tm.hrserver.main;
import com.tm.hrserver.*;
import com.tm.netcommon.exceptions.*;
import com.tm.netserver.*;
public class Main
{
public static void main(String args[])
{
try
{
RequestHandler requestHandler=new RequestHandler();
NetworkServer networkServer=new NetworkServer(requestHandler);
networkServer.start();
}catch(NetworkException ne)
{
System.out.println(ne);
}
}
}
