package scpbb;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

import scpbb.ClientHandler.WhileReceiving;
import scpbb.ClientHandler.WhileSending;

//importing class

public class Server {
	
	private ServerSocket ss;
	private Socket s;
	private List<String> cln;
	private List<Long> cli;
	private List<Socket> cls;
	
	public Server(int port) throws IOException
	{
		ss = new ServerSocket(port);
		cln=new ArrayList<String>();
		cli=new ArrayList<Long>();
		cls= new ArrayList<Socket>();
	}
	
	private class Clscon implements Runnable
	{
		private Socket sa;
		private ObjectOutputStream os;
		private ObjectInputStream is;
		private int frndind;
		
		public Clscon() {
			
			sa=s;
		}
		
		//methods
		private void setConnectionStreams() throws IOException
		{
			os= new ObjectOutputStream(sa.getOutputStream());
			is= new ObjectInputStream(sa.getInputStream());
		}
		
		//classes
		
		class WhileSending implements Runnable{
			String msg;
			public void run()
			{
				Scanner scn = new Scanner(System.in);
				do
				{
					try {
						msg= scn.nextLine();
						os.writeUTF(msg);
						os.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}while(!msg.equals("END"));
				scn.close();
				
				
			}
		}
		
		class WhileReceiving implements Runnable{
			String msg;
			public void run()
			{
				try {
					cln.add(is.readUTF());
					for(String s:cln)
					{
						System.out.print(s+ "  ");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				do
				{
					try {
						msg=is.readUTF();
						if(msg.equals("giveonlinelist"))
						{
							os.writeUTF("haveonlinelist");
							Iterator<String> itr = cln.iterator();
							for(;itr.hasNext();)
							{
								os.writeUTF(itr.next());
								itr.remove();
							}
							os.writeUTF("endonlinelist");
							
							msg=is.readUTF();
							frndind=Integer.parseInt(msg);
				
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(msg);
					
				}while(!msg.equals("END"));
				
			}
		}
		@Override
		public void run() 
		{
			try {
				setConnectionStreams();
				
				Thread ws = new Thread(new WhileSending());
				ws.start();
				Thread wr = new Thread(new WhileReceiving());
				wr.start();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	};
	public static void main(String[] args) throws IOException
	{
		Server ms=new Server(5001);
		int whl=3;
		while(whl!=0)
		{
			System.out.println("Waiting for connection");
			ms.s=ms.ss.accept();
			System.out.println("Connected");
			
			//Entering into cls
			ms.cls.add(ms.s);
			
			Thread ch= new Thread(ms.new Clscon());
			ch.start();
			long cp=ms.s.getPort();
			String chname=ch.getName();
			long chid=ch.getId();
			
			//ms.cln.add(chname);
			ms.cli.add(chid);
			System.out.println("CHT:INFO " );
			System.out.println("CHT: "+ chname  + "idd "+ chid + " cp"+ cp+ " "+ ms.s.getInetAddress());
			
			
			
			whl--;
		}
		for(int i=0;i<10000;i++);
		for(String s:ms.cln)
		{
			System.out.print(s+ "  ");
		}
		System.out.println();
		for(Long ln :ms.cli)
		{
			System.out.println(ln+" ");
		}
			
	}

}
