package com.tm.netserver;
import com.tm.netcommon.*;
import com.tm.netcommon.exceptions.*;
import java.net.*;
public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null)
{
throw new NetworkException("Request handler is missing");
}
this.requestHandler=requestHandler;
}

public void start() throws NetworkException
{
//this will run on main thread
ServerSocket serverSocket=null;
int port=Configuration.getPort();
try
{
serverSocket=new ServerSocket(port);
}catch(Exception e)
{
throw new NetworkException("Unable to start server on port: "+port);
}
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port: "+port);
socket=serverSocket.accept();
requestProcessor=new RquestProcessoe(socket, requestHandler);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}
