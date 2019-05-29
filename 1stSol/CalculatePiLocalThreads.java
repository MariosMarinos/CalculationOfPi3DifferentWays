import java.util.ArrayList;

public class CalculatePiLocalThreads {
	static long numSteps = 0;
	
	public static void main(String[] args) throws InterruptedException {

		/* parse command line */
		if (args.length != 1) {
			System.out.println("arguments:  number_of_steps");
			System.exit(1);
		}
		try {
			numSteps = Long.parseLong(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("argument " + args[0] + " must be long int");
			System.exit(1);
		}		
		/* start timing */
		long startTime = System.currentTimeMillis();
		/* do computation */
		int cores = Runtime.getRuntime().availableProcessors();
		ArrayList<CalculatePi> threads = new ArrayList<CalculatePi>() ;
		double pieces,temp,step,pi = 0;
		pieces = numSteps / cores; // cutting the calculation into 4 pieces for each thread.
		int remainder = (int) (numSteps % cores); // remainder to add it on the last thread.
		temp = 0; //end
		step = 1.0 / (double) numSteps; 
		int j = 0; 
		double Start = 0; // start 
		while (j<=cores) {
			if (j == cores)
				temp = temp + remainder; // last thread will take the remainder from the divide.
			else
				temp = temp + pieces; //otherwise it will take their piece.
			CalculatePi thread = new CalculatePi(Start,temp,step); //creating the thread
			threads.add(thread); // Adding the thread into a list so I can use join on them in the next for loop.
			thread.start();
			Start += pieces;
			j++;
		}
		// join the threads and after add the returned value from the thread to pi.
		for (int i=0 ; i<threads.size();i++) {
			CalculatePi x = threads.get(i);
			x.join();
			pi += x.getpi();
		}
		
		/* end timing and print result */
		long endTime = System.currentTimeMillis();
		System.out.printf("computed pi = %22.20f\n", pi);
		System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
		System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
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
				if (i==numSteps) break;
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
