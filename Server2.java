package scpaa;

import scpaa.Rb;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;

class server
{
	ServerSocket ss;
	Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;


	private void setConnections() throws IOException
	{
		
		ss= new ServerSocket(5000);
		
	}

	private void setConnectionStreams() throws IOException
	{
		os= new ObjectOutputStream(s.getOutputStream());
		is= new ObjectInputStream(s.getInputStream());
	}
	
	private void whileChattingSend() throws IOException
	{
		String msg="start";
		Scanner scn = new Scanner(System.in);
		while(!msg.equals("end"))
		{
			
			msg=scn.nextLine();
			os.writeUTF(msg);
			os.flush();
			System.out.println(msg);
		}
		scn.close();
		
	}

	private void whileChattingReceive() throws IOException
	{
		String msg="start";
		while(!msg.equals("end"))
		{
			msg=is.readUTF();
			System.out.println(msg);

		}
	}

	private void closeConnectionns() throws IOException
	{
		ss.close();
		
	}

	public static void main(String args[]) throws IOException
	{
		
		server sa=new server();
		
		sa.setConnections();
		while(true)
		{
			System.out.println("Waiting for connection");
			sa.s=sa.ss.accept();
			System.out.println("Connected");
			sa.setConnectionStreams();
			Rb t= new Rb(sa.s,sa.os,sa.is);
			t.start();
			
			Thread st=new Thread("send") {
				ObjectOutputStream tos;
				
				public void run()
				{
					tos=sa.os;
					System.out.println("Thread send running");
					Scanner scn= new Scanner(System.in);
					String msgg = scn.nextLine();
					do
					{
						try {
							tos.writeUTF(msgg);
							tos.flush();
							//tos.reset();
							msgg=scn.nextLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}while(!msgg.equals("END"));
				}
			};
			st.start();
		
		}
		//sa.closeConnectionns();
	}
		
}
