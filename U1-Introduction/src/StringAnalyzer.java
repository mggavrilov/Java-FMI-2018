import java.util.Scanner;

public class StringAnalyzer {
	public static int getPlateauLength(String s)
	{
		if(s.length() == 0)
		{
			return 0;
		}
		
		int length = s.length();
		char lastChar = s.charAt(0);
		int reps = 1;
		int mostReps = reps;
		
		for(int i = 1; i < length; i++)
		{
			if(s.charAt(i) == lastChar)
			{
				reps++;
			}
			else
			{
				if(reps > mostReps)
				{
					mostReps = reps;
				}
				
				lastChar = s.charAt(i);
				reps = 1;
			}
		}
		
		if(reps > mostReps)
		{
			mostReps = reps;
		}
		
		return mostReps;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		System.out.println(getPlateauLength(str));
		sc.close();
	}
}
