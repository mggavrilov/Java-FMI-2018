
public class CourseGrade implements Comparable<CourseGrade> {
	private Course course;
	private double grade;
	
	CourseGrade(Course course, double grade)
	{
		this.course = course;
		this.grade = grade;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public double getGrade()
	{
		return grade;
	}
	
	public int compareTo(CourseGrade other)
	{
		//descending order
		if(grade < other.grade)
		{
			return 1;
		}
		else if(grade > other.grade)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
