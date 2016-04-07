 
import twitter4j.*; 
import twitter4j.auth.OAuth2Token; 
import twitter4j.conf.ConfigurationBuilder;

import java.io.PrintWriter;
import java.util.Map; public class TweetMain { 
	private static final String CONSUMER_KEY	= "CONSUMER_KEY";
	private static final String CONSUMER_SECRET = "CONSUMER_SECRET";
	//	How many tweets to retrieve in every call to Twitter. 100 is the maximum allowed in the API 
	private static final int TWEETS_PER_QUERY	= 100; 
	
	private static final int MAX_QUERIES	= 1000; 
	 
	
	
	private static final String SEARCH_TERM	= "#Amazon"; 
	
	public static String cleanText(String text) 
	{ 
		text = text.replace("\n", "\\n"); 
		text = text.replace("\t", "\\t"); 
		return text;
	} 
	
	public static OAuth2Token getOAuth2Token() 
	{
		OAuth2Token token = null; 
		ConfigurationBuilder cb; 
		cb = new ConfigurationBuilder(); 
		cb.setApplicationOnlyAuthEnabled(true); 
		cb.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET); 
		try { 
			token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token(); 
			} 
		catch (Exception e) 
		{ 
			System.out.println("Could not get OAuth2 token"); 
			e.printStackTrace(); System.exit(0);
			} 
		return token; 
		} 
	public static Twitter getTwitter()
	{ 
		OAuth2Token token; 
		token = getOAuth2Token();
		
		ConfigurationBuilder cb = new ConfigurationBuilder(); 
		cb.setApplicationOnlyAuthEnabled(true); 
		cb.setOAuthConsumerKey("6dHtBO7p3Gezpw1cNrcx6Vfk2"); 
		cb.setOAuthConsumerSecret("irodXGwggZEtN7VXEXCzVzaP0Y7mX4SYOUvd1ZUhC4gKKifvr2"); 
		cb.setOAuth2TokenType(token.getTokenType()); 
		cb.setOAuth2AccessToken(token.getAccessToken()); 
	
		return new TwitterFactory(cb.build()).getInstance(); 
		}
	public static void main(String[] args) 
	{ 
		try{
		PrintWriter writer = new PrintWriter("tweets_amazon.txt","UTF-8");
		
	
		int	totalTweets = 0; 
		
		long maxID = -1; 
		Twitter twitter = getTwitter(); 
		
		try { 
			
			//	This returns all the various rate limits in effect for us with the Twitter API 
			Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search"); 
			//	This finds the rate limit specifically for doing the search API call we use in this program 
			RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets"); 
			//	Debugging code... 
			System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n", searchTweetsRateLimit.getRemaining(), searchTweetsRateLimit.getLimit(), searchTweetsRateLimit.getSecondsUntilReset());
			//	Retrieves multiple blocks of tweets from Twitter 
			for (int queryNumber=0;queryNumber < MAX_QUERIES; queryNumber++)
			{ System.out.printf("\n\n!!! Starting loop %d\n\n", queryNumber);
		
			if (searchTweetsRateLimit.getRemaining() == 0) { 
				 
				System.out.printf("!!! Sleeping for %d seconds due to rate limits\n", searchTweetsRateLimit.getSecondsUntilReset()); 
				
				Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset()+2) * 1000l); 
				} 
			Query q = new Query("#Amazon");	
			
			q.setCount(200000);	
			
			q.setLang("en");	
			
			if (maxID != -1) { q.setMaxId(maxID - 1); } 
			QueryResult r = twitter.search(q);
		 
			if (r.getTweets().size() == 0) 
			{ break;	
			}
			
			for (Status s: r.getTweets())	
			
				{  
				totalTweets++; 
				
				if (maxID == -1 || s.getId() < maxID) { maxID = s.getId(); } 
				
				writer.write("At"+s.getCreatedAt().toString()+" "+ s.getUser().getScreenName().toString()+"said: "+cleanText(s.getText()).toString());
				writer.println();

				} 
			
			searchTweetsRateLimit = r.getRateLimitStatus(); } 
			} 
		catch (Exception e) { 
			System.out.println("That didn't work well...wonder why?"); e.printStackTrace();
			} 
		System.out.printf("\n\nA total of %d tweets retrieved\n", totalTweets); 
			writer.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
			}
			
	} 
 

