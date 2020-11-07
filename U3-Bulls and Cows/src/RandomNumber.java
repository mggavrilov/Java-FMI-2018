import java.util.Random;

public class RandomNumber {
	private static StringBuilder secretNumber;
	
	public static void generateNumber(int length)
	{
		if(length < 1)
		{
			return;
		}
		
		secretNumber = new StringBuilder(length);
		
		Random rand = new Random();
		
		for(int i = 0; i < length; i++)
		{
			if(i == 0)
			{
				secretNumber.append(rand.nextInt(9) + 1);
			}
			else
			{
				secretNumber.append(rand.nextInt(10));
			}
		}
	}
	
	public static int getNumber()
	{
		if(secretNumber == null || secretNumber.length() == 0)
		{
			return 0;
		}
		
		return Integer.parseInt(secretNumber.toString());
	}
	
	public static String getString()
	{
		return secretNumber.toString();
	}
	
	public static void clearNumber()
	{
		secretNumber = new StringBuilder();
	}
}
