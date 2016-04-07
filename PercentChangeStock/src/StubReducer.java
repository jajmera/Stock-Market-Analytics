import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

public class StubReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable>{
	
	
	 public void reduce(Text key, Iterable<DoubleWritable> values,Context context)
		      throws IOException, InterruptedException {

		 double sum=0.0;
			for (DoubleWritable val : values) {
				sum += val.get();
			}
			context.write(key, new DoubleWritable(sum));
		  }
	}


