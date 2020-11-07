
public enum CourseType {
	REQUIRED('R'),
	ELECTIVE('E'),
	PRACTICE('P');

	private final char code;
	
	private CourseType(char code)
	{
		this.code = code;
	}
	
	public char getCode()
	{
		return code;
	}

	public static CourseType valueOf(char code) {
		for(CourseType e : values())
		{
			if(e.getCode() == code)
			{
				return e;
			}
		}
		
		return null;
	}
}
