import java.io.*;
import java.net.*;
class RequestProcessor extends Thread
{
private Socket socket;
RequestProcessor(Socket socket)
{
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte tmp[]=new byte[1024];
byte header[]=new byte[1024];
int bytesToReceive=1024;
int j=0;
int bytesReadCount;
int k,i;
i=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
i=0;
int fileLength=0;
j=1;
while(header[i]!=',')
{
fileLength=fileLength+(header[i]*j);
j=j*10;
i++;
}
i++;
String file_name;
StringBuffer sb=new StringBuffer();
while(i<1024)
{
sb.append((char)header[i]);
i++;
}
file_name=sb.toString().trim();
System.out.println("Header received of length : "+fileLength+", and file name : "+file_name);
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();
System.out.println("Acknowledgement sent");
File file=new File("uploads"+File.separator+file_name);
if(file.exists()) file.delete();
FileOutputStream ofs=new FileOutputStream(file);
bytesToReceive=fileLength;
j=0;
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
while(j<bytesToReceive)
{
bytesReadCount=is.read(bytes);
if(bytesReadCount==-1) continue;
ofs.write(bytes,0,bytesReadCount); //OutputFileStream wirte the given bytes from bytes array on upload folder of server
ofs.flush();
j=j+bytesReadCount;
}
ofs.close();
ack[0]=1;
os.write(ack,0,1);
os.flush();
System.out.println("Acknowledgement sent");
System.out.println("server is reciving File : "+file_name+", plese for a minute.");
System.out.println("File saved as : "+file.getAbsolutePath());
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}// class end
class server4
{
private ServerSocket serverSocket;
server4()
{
try
{
serverSocket=new ServerSocket(5500);
startListening();
}catch(Exception e)
{
System.out.println(e);
}
}
private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Krish's server is ready to accept your request"+'\n'+"How can I help you");
socket=serverSocket.accept(); // server goes in wait mode to accept request and when request arrives then it is diverted to another socket and address of that socket is return to us so that we can use the feature of multithreading
requestProcessor=new RequestProcessor(socket);
}
}catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String gg[])
{
new server4();
}
}