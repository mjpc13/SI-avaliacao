package exercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * A simple greeting client. More information about sockets at:
 * http://download.oracle.com/javase/tutorial/networking/sockets/index.html
 * and in the book "Head First Java" - Chapter 15.
 */
public class Client
{
	// 'throws IOException' enables us to write the code without try/catch blocks
	// but it also keeps us from handling possible IO errors 
	// (when for instance there is a problem when connecting with the other party) 
	public static void main(String args[]) throws IOException 
	{
		// Open your connection to a server, at port 1234
		Socket socket = new Socket("localhost", 1234);

		// Build DataStreams (input and output) to send and receive messages to/from the server
		InputStream in = socket.getInputStream();
		DataInputStream dataIn = new DataInputStream(in);

		OutputStream out = socket.getOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);

		Scanner scan = new Scanner(System.in);
		
		boolean connected = true;
		try {
		while(connected){
			// obtem a resposta vinda do servidor
			String serverResponse = dataIn.readUTF();
			
			// escreve resposta do servidor na consola
			System.out.print(serverResponse); 

			if (serverResponse.equals("> You just disconnected")){
				connected = false;
			}
			
			else{	
				dataOut.writeUTF(scan.nextLine());
				dataOut.flush();
			}
		}
		} catch(IOException io) {
			
			System.out.println("Connection with server was terminated");
			
		}

		// Cleanup operations, close the streams, socket and then exit
		dataOut.close();
		dataIn.close();
		socket.close();
		scan.close();
	}
	
}
