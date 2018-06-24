package scpaa;

import java.util.*;
import java.net.*;
import java.io.*;
import java.lang.*;

public class Rb extends Thread {
	
	Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;
	public Rb(Socket ts,ObjectOutputStream tos, ObjectInputStream tis)
	{
		this.s=ts;
		this.os=tos;
		this.is=tis;
	}
	
	
	private void whileChattingReceive() throws IOException
	{
		String msg="start";
		while(!msg.equals("end"))
		{
			msg=is.readUTF();
			System.out.println(msg);

		}
	//	s.close();
		//os.close();
		//is.close();
	}
	
	
	
	@Override
	public void run()
	{
		System.out.println("Thread main running");
		try
		{
		System.out.println(this.getId()+" Thread");
		this.whileChattingReceive();
		
		
		
		}
		catch(Exception e)
		{
			
		}
		
	}

	/*
	public static void main(String[] args) throws IOException
	{
		
		for(int i=0;i<5;i++)
		{
		Rb ra=new Rb(),rb=new Rb();
		ra.start();
		rb.start();
		}

	}
	*/

}
