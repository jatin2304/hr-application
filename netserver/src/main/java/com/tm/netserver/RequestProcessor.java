package com.tm.netserver;
import com.tm.netcommon.*;
import com.tm.netcommon.exceptions.*;
import java.io.*;
import java.net.*;
public class RequestProcessor extends Thread
{
private RequestHandlerInterface requestHandler;
Socket socket;
public RequestProcessor(Socket socket, RequestHandlerInterface requestHandler)
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int bytesToReceive=1024;
byte header[]=new byte[1024];
int bytesReadCount;
int i, j, k;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(header);
if(bytesReadCount==-1) continue;
j+=bytesReadCount;
}
int requestLength=0;
i=1;
j=1023;
while(j>=0)
{
requestLength+=header[j]*i;
i*=10;
j--;
}
byte ack[]=new byte[1];
ack[0];
os.write(ack,0,1);
os.flush();
byte requestBytes=new byte[requestLength];
bytesToReceive=requestLength;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(requestBytes);
if(bytesReadCount==-1) continue;
j+=bytesReadCount;
}
ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
Response response=requestHandler.process(request);
ByteArrayOutputStream oos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte objectBytes[]=baos.toByteArray();
int responseLength=objectBytes.length;
int x=responseLength;
i=1023;
header=new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
os.write(header,0,1024);
os.flush();
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
int bytesToSend=responseLength;
int chunkSize=4096; //or 1024
j=0;
while(j<bytesToSend)
{
if(bytesToSend-j<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
j+=chunkSize;
}
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}

