import java.util.regex.Pattern;
import java.util.Scanner;

public class PatternMatcher {
	public static boolean match_regex(String s, String p)
	{
		String p_new = ".*";
		
		for(int i = 0; i < p.length(); i++)
		{
			if(p.charAt(i) == '?')
			{
				p_new += ".{1}";
			}
			else if(p.charAt(i) == '*')
			{
				p_new += ".*";
			}
			else
			{
				p_new += p.charAt(i);
			}
		}
		
		p_new += ".*";
		
		return Pattern.matches(p_new, s);
	}
	
	public static boolean match(String s, String p)
	{
		if(s.length() == 0 && p == "*")
		{
			return true;
		}
		
		int sIndex = 0;
		int pIndex = 0;
		
		int sStartingIndex = 0;
		
		while(pIndex < p.length())
		{
			if(sIndex > s.length() - 1)
			{
				sStartingIndex++;
				pIndex = 0;
				sIndex = sStartingIndex;
			}
			
			if(sStartingIndex > s.length() - 1)
			{
				return false;
			}
			
			if(p.charAt(pIndex) == sIndex || p.charAt(pIndex) == '?')
			{
				pIndex++;
				sIndex++;
			}
			else if(p.charAt(pIndex) == '*')
			{
				if(pIndex == p.length() - 1)
				{
					return true;
				}
				else if(pIndex == 0)
				{
					char nextChar = p.charAt(pIndex + 1);
					int foundAt = -1;
					
					for(int i = sIndex; i < s.length(); i++)
					{
						if(nextChar == s.charAt(i))
						{
							foundAt = i;
							break;
						}
					}
					
					if(foundAt == -1)
					{
						sStartingIndex++;
						pIndex = 0;
						sIndex = sStartingIndex;
					}
					else
					{
						pIndex++;
						sIndex = foundAt;
					}
				}
				else
				{
					char nextChar = p.charAt(pIndex + 1);
					
					for(int i = sIndex + 1; i < s.length(); i++)
					{
						if(nextChar == s.charAt(i))
						{
							if(match(s.substring(i), p.substring(pIndex + 1)))
							{
								return true;
							}
						}
					}
					
					sStartingIndex++;
					pIndex = 0;
					sIndex = sStartingIndex;
				}
			}
			else
			{
				sStartingIndex++;
				pIndex++;
				sIndex = sStartingIndex;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		String p = sc.nextLine();
		System.out.println(match(s, p));
		sc.close();
	}
}
