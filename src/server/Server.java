package server;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



class Server implements ActionListener
{
	JFrame jf;
	JScrollPane js;
	JTextField jtf;
	JTextArea jta;
	ServerSocket serversocket;
	InetAddress inet_address;
	DataInputStream dis;
	DataOutputStream dos;
	Socket socket;
	
	
	
	Thread thread = new Thread() {
		public void run()
		{
			while(true)
			{
				readMessage();
			}
		}
	};
	
	
	public Server()
	{
		jf = new JFrame("Server");
		jf.setSize(500,500);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		jta = new JTextArea();
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setFont(new Font("Arial",Font.PLAIN,18));
		jta.setEditable(false);
		js = new JScrollPane(jta);
		jf.add(js);
		
		jtf = new JTextField();
		jtf.setFont(new Font("Arial",Font.PLAIN,18));
		jtf.addActionListener(this);
		jtf.setEditable(false);
		jf.add(jtf, BorderLayout.SOUTH);
		
		jf.setVisible(true);
	}
	
	
	
	

	public void waitingForClient() {
		// TODO Auto-generated method stub
		String ip_address = getIPAddress();
		try
		{
			
			serversocket = new ServerSocket(1111);
			jta.setText("To connect with server, please provide the IP address :     "+ip_address);
			socket = serversocket.accept();
			jtf.setEditable(true);
			jta.setText("Client connected\n");
			jta.append("--------------------------------------------------------------------\n");
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getIPAddress()
	{
		String ip = "";
		try
		{
			inet_address = InetAddress.getLocalHost();
			ip = inet_address.getHostAddress();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ip;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		sendMessage(e.getActionCommand());
		jta.append("Me : "+e.getActionCommand()+"\n");
		jtf.setText("");
		//showMessage(e.getActionCommand());
		
	}
	public void setIOStream()
	{
		try
		{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		thread.start();
		
	}
	public void readMessage()
	{
		try
		{
			String message = dis.readUTF();
			showMessage("Client : "+message);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void sendMessage(String message)
	{
		try
		{
			dos.writeUTF(message);
			dos.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public void showMessage(String message)
	{
		jta.append(message+"\n");
	}

}
