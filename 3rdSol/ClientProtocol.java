import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ClientProtocol {

	
	public double processReply(String theInput) throws IOException {
		double start,end,step,i;
		String[] words = theInput.split(" ");
		words[0] = words[0].replaceAll(".+:", "");
		words[1] = words[1].replaceAll(".+:", "");
		words[2] = words[2].replaceAll(".+:", "");
		start = Double.parseDouble(words[0]);
		end = Double.parseDouble(words[1]);
		step = Double.parseDouble(words[2]);
		int cores = Runtime.getRuntime().availableProcessors();
		ArrayList<CalculatePi> threads = new ArrayList<CalculatePi>() ;
		double pieces, temp, pi = 0;
		double numSteps = end - start;
		pieces = numSteps / cores;
		int remainder = (int) (numSteps % cores); // remainder to add it on the last thread.
		temp = start + pieces; //end
		int j = 1;
		double Start = start ;
		for (i = 0; i < cores; i ++) {
			CalculatePi thread = new CalculatePi(Start,temp,step);
			threads.add(thread);
			thread.start();
			if (i==3) {
				temp = temp + pieces + remainder;
			}
			else 
				temp += pieces;
			Start += pieces;
		}
		for (j=0 ; j<threads.size();j++) {
			CalculatePi x = threads.get(j);
			try {
				x.join();
				pi += x.getpi();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pi;
	}
	static class CalculatePi extends Thread {
		private double start;
		private double stop;
		private double sum;
		private double step;
		private double pi;
		
		public CalculatePi(double start, double stop, double step) {
			this.start = start;
			this.stop = stop;
			this.step = step;
		}

		@Override
		public void run() {
			for  (double i = start; i < stop; i++) {
				double x = ((double) i + 0.5) * step;
				sum += 4.0 / (1.0 + x * x);
			}
			pi = sum * step;
		}
		
		
		
		public double getpi() {
			return pi;
		}
	}
}


