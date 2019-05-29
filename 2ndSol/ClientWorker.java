import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ClientWorker {
        private static final String HOST = "localhost";
	private static final int PORT = 1234;
	private static final String EXIT = "CLOSE";

	public static void main(String args[]) throws IOException {

		Socket dataSocket = new Socket(HOST, PORT);
		
		InputStream is = dataSocket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		OutputStream os = dataSocket.getOutputStream();
		PrintWriter out = new PrintWriter(os,true);
		       	
		System.out.println("Connection to " + HOST + " established");
		double outmsg;
		String inmsg;
		inmsg = in.readLine(); // message sent by server!
		ClientProtocol app = new ClientProtocol();
		outmsg = app.processReply(inmsg);
		out.println(outmsg); // message sent by client to server.
		dataSocket.close();
		System.out.println("Data Socket closed");

	}
}			
