package scpbb;

import java.net.*;
import java.io.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Client extends JFrame {

	private String name, ipadd ;
	private int port;
	
	//Socket
	Socket s;
	ObjectOutputStream os;
	ObjectInputStream is;
	
	private JPanel contentPane;
	private JTextField txtchat;
	private JTextArea txthistory;
	private JButton btnSend;
	
	

	private void createConnection() throws IOException
	{
		s= new Socket(ipadd,port);
		
	}
	private void setConnectionStreams() throws IOException
	{
		os= new ObjectOutputStream(s.getOutputStream());
		is=new ObjectInputStream(s.getInputStream());
	}
	private void whileChattingSend(String msg) throws IOException
	{
		try {
			os.writeUTF(msg);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Client(String a, String b, int c) throws IOException {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		name=a;
		ipadd=b;
		port=c;
		
		
		
		
		
		setTitle(name+" Chat Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 500, 360);
		setSize(500,360);
		setLocationRelativeTo(null);
		setVisible(true);
		createWindow();
		
		console("Waiting for connection :");
		createConnection();
		console("Connection Established");
		setConnectionStreams();
		
		Thread receive= new Thread("receive") {
				public void run() 
				{
					try
					{
						String msg=is.readUTF();
						while(!msg.equals("END"))
						{
							console(msg);
							msg=is.readUTF();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			};
			
			receive.start();
		
		
		
	}
	
	
	private void createWindow()
	{
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0,440,60};
		gbl_contentPane.rowHeights = new int[]{0,300,60};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
	    txthistory = new JTextArea();
	    
	    JScrollPane scroll= new JScrollPane(txthistory);
	    
		txthistory.setEditable(false);
		GridBagConstraints gbc_scroll = new GridBagConstraints();
		gbc_scroll.gridheight = 2;
		gbc_scroll.gridwidth = 3;
		gbc_scroll.insets = new Insets(0, 0, 5, 5);
		gbc_scroll.fill = GridBagConstraints.BOTH;
		gbc_scroll.gridx = 0;
		gbc_scroll.gridy = 0;
		contentPane.add(scroll, gbc_scroll);
		console("User: "+ name + "  IpAdd: "+ ipadd + "  Port: "+ port);
		
		txtchat = new JTextField();
		txtchat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String msg=txtchat.getText();
					txtchat.setText(null);
					send(msg);
					try {
						whileChattingSend(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_txtchat = new GridBagConstraints();
		gbc_txtchat.gridwidth = 2;
		gbc_txtchat.insets = new Insets(0, 0, 0, 5);
		gbc_txtchat.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtchat.gridx = 0;
		gbc_txtchat.gridy = 2;
		contentPane.add(txtchat, gbc_txtchat);
		txtchat.setColumns(10);
		txtchat.grabFocus();
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg=txtchat.getText();
				send(msg);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		
		contentPane.add(btnSend, gbc_btnSend);
	}
	
	
	private void send(String msg)
	{
		
		if(msg.equals(""))return;
		console(name + ": " +msg);
		
	}
	
	private void console(String msg)
	{
		txthistory.append(msg+"\n");
		
	}
}
