import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import training.Classify;

import java.io.IOException;
import java.text.DecimalFormat;

public class StubMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
	
	//private final static IntWritable one = new IntWritable(1); 
	//private Text word = new Text();
	Classify classify = new Classify();
	
	public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException
	{
		
		
		
		try {
			String[] tokens = value.toString().split(" ");  
			//System.out.println(value);
			if(tokens[0].contains("At"))
			{
				String date = tokens[1]+tokens[2]+tokens[5];
				System.out.println(date);
				int sentValFinal=0;
				for(int i =7;i< tokens.length-1;i++)
				{
					String content = tokens[i];
					int  sentVal = Classify.sentiment(content);
					System.out.println(sentVal);
					context.write(new Text(date), new IntWritable(sentVal));

				}

			}
			else
				context.write(new Text("0"),new IntWritable(0));
			
			/*
			//This is for % change in daily stock prices
			float open = Float.valueOf(tokens[1]);
			float close = Float.valueOf(tokens[4]);
	   		float change = ((close - open)/open) * 100;   
	   		*/
	    	//word.set(new DecimalFormat("0.##").format((double) change) + "%");
			
			/*int volume = Integer.valueOf(tokens[5]);
	   		word.set(tokens[0]);
			context.write(word, new IntWritable(volume))*/;    
		}   
		catch (NumberFormatException e) {
			// ignore first line
		}
		
	}

}
