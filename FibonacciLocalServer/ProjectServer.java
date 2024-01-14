package cop2805;
import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class ProjectServer {
	
	static int fibonacci(int n) {
		int v1 = 0, v2 = 1, v3 = 0;

		for (int i = 2; i <= n; i++) {
			v3 = v1 + v2;
			v1 = v2;
			v2 = v3;
		}

		return v3;
	}

	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;
		
		try {
			server = new ServerSocket(1234);
			System.out.println("Port bound. Accepting Connections");
			
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n","");
				System.out.println("Client entered: " + clientInput);
				
				String response = "" + fibonacci(Integer.parseInt(clientInput));
				output.write(response.length());
				output.write(response.getBytes());
				
				client.close();
				if(clientInput.equalsIgnoreCase("shutdown")) {
					System.out.print("Shutting down...");
					shutdown = true;
				}
				
			} catch(IOException e) {
				e.printStackTrace();
				continue;
			}
					
		}
	}

}
