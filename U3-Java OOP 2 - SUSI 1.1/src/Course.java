
public class Course implements Subject {

	private String id;
	private String name;
	private double credits;
	private CourseType courseType;
	
	Course() {
		id = "";
		name = "";
		credits = 0;
		courseType = CourseType.REQUIRED;
	}
	
	Course(String id, String name, double credits, char type)
	{
		this.id = id;
		this.name = name;
		this.credits = credits;
		this.courseType = CourseType.valueOf(type);
	}
	
	public static int countByType(Course[] courses, CourseType type)
	{
		int counter = 0;
		
		for(int i = 0; i < courses.length; i++)
		{
			if(courses[i].getType() == type)
			{
				counter++;
			}
		}
		
		return counter;
	}
	
	public void setCredits(double credits) {
		this.credits = credits;
	}
	
	public double getCredits() {
		return credits;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof Course))
		{
			return false;
		}
		
		if(o == this || ((Course)o).getId() == id)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public CourseType getType()
	{
		return courseType;
	}
	
	public void setType(CourseType courseType)
	{
		this.courseType = courseType;
	}

}
