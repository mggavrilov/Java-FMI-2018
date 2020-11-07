import java.util.Scanner;

public class Main {
	
	public static int getComputerNumber()
	{
		return RandomNumber.getNumber();
	}
	
	public static void clearComputerNumber()
	{
		RandomNumber.clearNumber();
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		//System.out.print("Enter length of play number: ");
		int length = sc.nextInt();
		
		RandomNumber.generateNumber(length);
		
		String userGuess;
		
		while(true)
		{
			//System.out.print("Enter your suggestion: ");
			userGuess = sc.next();
			
			String actualNumber = RandomNumber.getString();
			
			int bulls = 0;
			int cows = 0;
			
			boolean[] checked = new boolean[10];
			
			for(int i = 0; i < length; i++)
			{
				if(!checked[Character.getNumericValue(userGuess.charAt(i))])
				{
					checked[Character.getNumericValue(userGuess.charAt(i))] = true;
					
					int guessOccurrences = userGuess.length() - userGuess.replace(Character.toString(userGuess.charAt(i)), "").length();
					int actualOccurrences = actualNumber.length() - actualNumber.replace(Character.toString(userGuess.charAt(i)), "").length();
					
					if(guessOccurrences > 0 && actualOccurrences > 0)
					{
						if(guessOccurrences < actualOccurrences)
						{
							cows += guessOccurrences;
						}
						else
						{
							cows += actualOccurrences;
						}
					}
				}
			}
			
			for(int i = 0; i < length; i++)
			{
				if(userGuess.charAt(i) == RandomNumber.getString().charAt(i))
				{
					bulls++;
					cows--;
				}
			}
			
			System.out.println(bulls + " bulls and " + cows + " cows");
			
			if(bulls == length)
			{
				break;
			}
		}
		
		System.out.println("Finished");
		
		sc.close();
	}

}
