import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class ClientProtocol {

	
	public double processReply(String theInput) throws IOException {
		double start,end,step,i,x,sum = 0;
		// getting the message from server and splitting it to extract the start, end and the step.
		String[] words = theInput.split(" ");
		words[0] = words[0].replaceAll(".+:", "");
		words[1] = words[1].replaceAll(".+:", "");
		words[2] = words[2].replaceAll(".+:", "");
		start = Double.parseDouble(words[0]);
		end = Double.parseDouble(words[1]);
		step = Double.parseDouble(words[2]);
		for  (i = start; i < end; i++) {
			x = ((double) i + 0.5) * step;
			sum += 4.0 / (1.0 + x * x);
		}
		return sum*step;
	}
}


