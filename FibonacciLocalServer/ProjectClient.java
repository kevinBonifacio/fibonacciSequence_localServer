package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.awt.event.*;

public class ProjectClient {
	
	private static void constructGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		
		//creates the GUI layout
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}	
		});
	}
}

class MyFrame extends JFrame {
	public JLabel result;
	public JTextField fibonacciNum;
	
	public MyFrame() {
		super();
		init();
	}

	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Fibonacci Calculator");
		this.setLayout(new GridLayout(2, 2));
		
		//first row
		this.add(new JLabel("Enter fibonacci number:"));
		
		fibonacciNum = new JTextField();
		this.add(fibonacciNum);
		
		//second row
		JButton btn = new JButton("Calculate");
		btn.addActionListener(new MyButtonListener(this));
		
		result = new JLabel();

		this.add(btn);
		this.add(result);
		this.pack();
		
		int frameWidth = 400;
		int frameHeight = 150;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((int) (screenSize.getWidth() / 2) - (frameWidth / 2), (int)(screenSize.getHeight() / 2) - frameHeight ,
		frameWidth, frameHeight);
	}
}

class MyButtonListener implements ActionListener {
	MyFrame fr;
	public MyButtonListener(MyFrame frame)
	{
		fr = frame;
	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			String userString = fr.fibonacciNum.getText();
			Socket connection = new Socket("127.0.0.1", 1234);
			InputStream input = connection.getInputStream();
			OutputStream output = connection.getOutputStream();
			
			output.write(userString.length());
			output.write(userString.getBytes());
			
			int n = input.read();
			byte[] data = new byte[n];
			input.read(data);
			
			String serverResponse = new String(data, StandardCharsets.UTF_8);
			fr.result.setText(serverResponse);
			
			if(!connection.isClosed())
				connection.close();
			
		} catch(IOException exc) {
			exc.printStackTrace();
		}	
	}
}
