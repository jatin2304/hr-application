package com.tm.netclient;
import com.tm.netcommon.*;
import com.tm.netcommon.exceptions.*;
import java.io.*;
import java.net.*;
public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte bytes[]=baos.toByteArray();
byte header[]=new byte[1024];
int i,j,x,requestLength;
i=1023;
j=0;
requestLength=bytes.length;
x=requestLength;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());// ("localhost",5500) this hardcoding of port and host is changed
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
int chunkSize=1024;
//sending header
os.write(header,0,chunkSize);
os.flush();
//receiving ack
int bytesReadCount;
byte ack[]=new byte[1];
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
//sending data
int bytesToSend=requestLength;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(bytes,j,chunkSize);
os.flush();
j+=chunkSize;
}
//receiving ack of data
ack[0]=0;
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
//receing header of response
i=0;
j=0;
int bytesToReceive=1024;
while(j<bytesToReceive)
{
bytesReadCount=is.read(header);
if(bytesReadCount==-1) continue;
j+=bytesReadCount;
}
int responseLength=0;
j=1;
i=1023;
//parsing header
while(i>=0)
{
responseLength+=header[i]*j;
j=j*10;
i--;
}
//sending ack of resonse header
ack[0]=1;
os.write(ack,0,1);
os.flush();
//receiving response
byte response[]=new byte[responseLength];
bytesToReceive=responseLength;
i=0;
j=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(response);
if(bytesReadCount==-1) continue;
j+=bytesReadCount;
}
//sending ack of response
ack[0]=1;
os.write(ack,0,1);
os.flush();
socket.close();
ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
Response responseObject=(Response)ois.readObject();
return responseObject;
}catch(Exception e)
{
throw new NetworkException(e.getMessage());
}
}
}

