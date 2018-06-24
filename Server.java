package scpbb;

import java.net.*;
import java.io.*;
import java.lang.*;

//importing class




public class Server {
	
	private ServerSocket ss;
	private Socket s;
	
	
	public Server(int port) throws IOException
	{
		ss = new ServerSocket(port);
	}
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		Server ms=new Server(5001);
		
		while(true)
		{
			System.out.println("Waiting for connection");
			ms.s=ms.ss.accept();
			System.out.println("Connected");
			
			
			
			Thread ch= new Thread(new ClientHandler(ms.s));
			ch.start();
			long cp=ms.s.getPort();
			String chname=ch.getName();
			long chid=ch.getId();
			System.out.println("CHT:INFO " );
			System.out.println("CHT: "+ chname  + "idd "+ chid + " cp"+ cp+ " "+ ms.s.getInetAddress());
			
			
			
			
		}
	}

}
