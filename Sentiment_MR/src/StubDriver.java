import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
public class StubDriver {

  public static void main(String[] args) throws Exception {
	  Configuration conf = new Configuration();
	  if(args.length !=2){
		  System.err.println("Please enter a value");
		  System.exit(-1);
		  
	  }
	// Path inpath = new Path("/user/cloudera/Downloads/table.csv");
	// Path outpath= new Path("/user/cloudera/Downloads/verzinop");
	 
	 
		Path  outpath=new Path(args[1]);
		  
		Job job=new Job(conf,"PageRank");
			 job.setJarByClass(StubDriver.class);
			  //job.setJobName(conf,"PageRank");
			 FileInputFormat.addInputPath(job,new Path(args[0]));
			  FileOutputFormat.setOutputPath(job, outpath);
			  job.setMapperClass(StubMapper.class);
			  job.setCombinerClass(StubReducer.class);
			  job.setReducerClass( StubReducer.class);
			  job.setOutputKeyClass(Text.class);
			  job.setOutputValueClass(IntWritable.class);
			  job.waitForCompletion(true);
			  System.exit(job.waitForCompletion(true)? 0 : 1);
			
			
			 
	  
	 
	  
      
	 
}
}

