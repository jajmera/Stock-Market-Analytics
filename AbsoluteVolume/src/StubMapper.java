import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;
import java.text.DecimalFormat;

public class StubMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
	
	//private final static IntWritable one = new IntWritable(1); 
	private Text word = new Text();
	
	public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException
	{
		
		
		try {
			String[] tokens = value.toString().split(",");  
			System.out.println(value);
			/*
			//This is for % change in daily stock prices
			float open = Float.valueOf(tokens[1]);
			float close = Float.valueOf(tokens[4]);
	   		float change = ((close - open)/open) * 100;   
	   		*/
	    	//word.set(new DecimalFormat("0.##").format((double) change) + "%");
			
			int volume = Integer.valueOf(tokens[5]);
	   		word.set(tokens[0]);
			context.write(word, new IntWritable(volume));    
		}   
		catch (NumberFormatException e) {
			// ignore first line
		}
		
	}

}
