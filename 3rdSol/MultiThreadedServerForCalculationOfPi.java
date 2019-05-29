import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MultiThreadedServerForCalculationOfPi {
	private static final int PORT = 1234;

	public static void main(String args[]) throws IOException {
        int clients=0 ,numSteps = 0;
		if (args.length != 2) {
	    System.out.println("Usage: java MultiThreadedServerForCalculationOfPi <numSteps> <clients>");
	    System.exit(1);
        }
        try {
	    numSteps = Integer.parseInt(args[0]);
	    clients = Integer.parseInt(args[1]);
        }
		catch (NumberFormatException nfe) {
	    System.out.println("Integer argument expected");
	    System.exit(1);
		}
        List<Double> start = new ArrayList<Double>();
        List<Double> end = new ArrayList<Double>();
        List<Socket> Currently_Clients = new ArrayList<Socket>();
        List<ServerThread> threads = new ArrayList<ServerThread>();
		double pieces,temp,step,pi = 0;
		ServerSocket connectionSocket = new ServerSocket(PORT);
		pieces = numSteps / clients;
		int remainder = numSteps % clients; // remainder to add it on the last thread.
		temp = pieces; //end
		step = 1.0 / (double) numSteps;
		int j = 1;
		double Start = 0; // start 
		while (j<=clients) {
			j ++;
			start.add(Start);
			end.add(temp);
			if (j == clients)
				temp = temp + pieces + remainder;
			else
				temp = temp + pieces;
			Start += pieces;
		}

		for (j = 0; j<clients; j++) {
			System.out.println("Server is listening to port: " + PORT);
			Socket dataSocket = connectionSocket.accept();
			Currently_Clients.add(dataSocket);
			System.out.println("Received request from " + dataSocket.getInetAddress());
		}
		/* start timing */
		long startTime = System.currentTimeMillis();
		/* do computation */
		for (j=0 ; j<clients;j++) {
			ServerThread sthread = new ServerThread(Currently_Clients.get(j),start.get(j),end.get(j),step);
			sthread.start();
			threads.add(sthread);
		}
		for (j = 0;j<clients;j++) {
			ServerThread Currthread=threads.get(j);
			try {
				Currthread.join();
				pi += Currthread.getPi();	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/* end timing and print result */
		long endTime = System.currentTimeMillis();
		System.out.printf("computed pi = %22.20f\n",pi );
		System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
		System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
	}
}


