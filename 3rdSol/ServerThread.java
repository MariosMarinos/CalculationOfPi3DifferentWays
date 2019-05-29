import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

class ServerThread extends Thread{
	private Socket dataSocket;
	private InputStream is;

	private OutputStream os;
   	private PrintWriter out;
   	private BufferedReader in;
   	private double start;
   	private double end;
   	private double step;
   	private double pi = 0;
   	
   	public ServerThread(Socket socket, double start,double end, double step ){
      		dataSocket = socket;
      		this.start = start;
      		this.end = end;
      		this.step = step;
      		try {
			is = dataSocket.getInputStream();
      		in = new BufferedReader(new InputStreamReader(is));
			os = dataSocket.getOutputStream();
			out = new PrintWriter(os,true);
		}
		catch (IOException e)	{		
	 		System.out.println("I/O Error " + e);
      		}
    	}

	public void run() {
		String inmsg,temp;
		double pieceOfPi = 0 ;
		temp = "Start:" + String.valueOf(start) + " end:" + String.valueOf(end) + " step:"+String.valueOf(step);
		out.println(temp);
		try {
			inmsg = in.readLine();
			try{
				pieceOfPi = Double.valueOf(inmsg);
				pi = pi + pieceOfPi;
				}catch(NullPointerException ex){}// handle your exception  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}	
	public double getPi() {
		return pi;
	}
}	
			
		
