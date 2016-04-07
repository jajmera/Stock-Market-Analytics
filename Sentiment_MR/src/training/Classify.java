package training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Classify {
	
	public void Classify()
	
	{
		
	}
	public static int sentiment(String s)
	{
		int sen =0;
		String line = new String();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("/home/cloudera/Downloads/lexicon.csv"));
			while( (line = br.readLine())!= null)
			{
				String sentiment[] = line.split(",");
				if(s.equalsIgnoreCase(sentiment[0]))
				{
					sen= Integer.parseInt(sentiment[1]);
					return sen;
					
				}
				
			}
			return 0;
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		return 0;

	}

}
