package scpbb;

import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.lang.*;

public class ClientHandler implements Runnable{
	
	private Socket s;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	


	public ClientHandler(Socket s) {
		
		this.s=s;
	}
	
	private void setConnectionStreams() throws IOException
	{
		os= new ObjectOutputStream(s.getOutputStream());
		is= new ObjectInputStream(s.getInputStream());
	}
	
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
			
			do
			{
				try {
					msg=is.readUTF();
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

}
