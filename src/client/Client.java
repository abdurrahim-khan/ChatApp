package client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class Client implements ActionListener 
{
	JFrame jf;
	JScrollPane js;
	JTextField jtf;
	JTextArea jta;
	Socket socket;
	String ip_address;
	DataInputStream dis;
	DataOutputStream dos;
	
	
	
		Thread thread = new Thread() {
			public void run()
			{
				while(true)
				{
					readMessage();
				}
			}
		};
		
		
		public Client()
		{
			ip_address = JOptionPane.showInputDialog("Enter the IP address");
			if(ip_address != null && !ip_address.equals(""))
			{
				jf = new JFrame("Client");
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
				jf.add(jtf, BorderLayout.SOUTH);
				
				jf.setVisible(true);
			}
			else
			{
				thread.stop();
				System.exit(0);
			}
			
			
		}
		public void connectToServer()
		{
			try
			{
				socket = new Socket(ip_address,1111);
				jta.setText("Connected To server\n");
				jta.append("--------------------------------------------------------------------\n");
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			sendMessage(e.getActionCommand());
			jta.append("Me : "+e.getActionCommand()+"\n");
			jtf.setText("");
			//showMessage(e.getActionCommand());
			
		}
		public void showMessage(String message)
		{
			jta.append(message+"\n");
			
		}
		public void readMessage()
		{
			try
			{
				String message = dis.readUTF();
				showMessage("Server : "+message);
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
}
