import java.util.Arrays;

public class Student implements User {
	
	private static final int maxCourses = 50;
	
	private String name;
	private int facultyNumber;
	
	private Course[] coursesTaken;
	private int coursesTakenCount;
	
	private double[] grades;
	private double credits;
	
	Student()
	{
		name = "";
		facultyNumber = 0;
		
		init();
	}
	
	Student(String name, int facultyNumber)
	{
		this.name = name;
		this.facultyNumber = facultyNumber;
		
		init();
	}
	
	private void init()
	{
		coursesTaken = new Course[maxCourses];
		coursesTakenCount = 0;
		
		grades = new double[maxCourses];
		credits = 0;
	}
	
	public Course[] sortCourses()
	{
		if(coursesTakenCount == 0)
		{
			return null;
		}
		
		CourseGrade[] courseGradeArray = new CourseGrade[coursesTakenCount];
		
		for(int i = 0; i < coursesTakenCount; i++)
		{
			courseGradeArray[i] = new CourseGrade(coursesTaken[i], grades[i]);
		}
		
		Arrays.sort(courseGradeArray);
		
		Course[] sortedCourses = new Course[coursesTakenCount];
		
		for(int i = 0; i < coursesTakenCount; i++)
		{
			sortedCourses[i] = courseGradeArray[i].getCourse();
		}
		
		return sortedCourses;
	}
	
	public void gradeCourse(Course course, double grade) throws CapacityExceededException
	{
		for(int i = 0; i < coursesTakenCount; i++)
		{
			if(coursesTaken[i].equals(course))
			{
				grades[i] = grade;
				return;
			}
		}
		
		if(coursesTakenCount < maxCourses)
		{
			coursesTaken[coursesTakenCount] = course;
			grades[coursesTakenCount] = grade;
			
			credits += course.getCredits();
			
			++coursesTakenCount;
			
			return;
		}
		else
		{
			throw new CapacityExceededException();
		}
	}
	
	public double getGPA()
	{
		if(coursesTakenCount == 0)
		{
			return 0.0;
		}
		
		double totalGrade = 0.0;
		
		for(int i = 0; i < coursesTakenCount; i++)
		{
			totalGrade += grades[i];
		}
		
		return totalGrade / coursesTakenCount;
	}
	
	public double getCredits() {
		return credits;
	}
	
	public double getCreditsByCourseType(CourseType type)
	{
		double credits = 0.0;
		
		for(int i = 0; i < coursesTakenCount; i++)
		{
			if(coursesTaken[i].getType() == type)
			{
				credits += coursesTaken[i].getCredits();
			}
		}
		
		return credits;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof Student))
		{
			return false;
		}
		
		if(o == this || ((Student)o).getFacultyNumber() == facultyNumber)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getFacultyNumber() {
		return facultyNumber;
	}

	@Override
	public void setFacultyNumber(int facultyNumber) {
		this.facultyNumber = facultyNumber;
	}

}
