
public class SusiCockpit implements Susi {
	
	private static final int maxStudents = 1000;
	
	private Student[] registeredStudents;
	private int registeredStudentsCount;
	
	SusiCockpit()
	{
		registeredStudents = new Student[maxStudents];
		registeredStudentsCount = 0;
	}

	@Override
	public void registerStudent(Student student) throws StudentAlreadyRegisteredException, CapacityExceededException {
		if(registeredStudentsCount < maxStudents)
		{
			for(int i = 0; i < registeredStudentsCount; i++)
			{
				if(registeredStudents[i].equals(student))
				{
					throw new StudentAlreadyRegisteredException();
				}
			}
			
			registeredStudents[registeredStudentsCount++] = student;
		}
		else
		{
			throw new CapacityExceededException();
		}
	}

	@Override
	public int getStudentsCount() {
		return registeredStudentsCount;
	}

	@Override
	public void setGrade(Student student, Course course, double grade) throws StudentNotFoundException, CapacityExceededException {		
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student))
			{
				registeredStudents[i].gradeCourse(course, grade);
				return;
			}
		}
		
		throw new StudentNotFoundException();
	}

	@Override
	public double getTotalCredits(Student student) throws StudentNotFoundException {
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student))
			{
				return registeredStudents[i].getCredits();
			}
		}
		
		throw new StudentNotFoundException();
	}

	@Override
	public double getGPA(Student student) throws StudentNotFoundException {
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student)) {
				return registeredStudents[i].getGPA();
			}
		}
		
		throw new StudentNotFoundException();
	}
	
	@Override
	public double getCreditsPerType(Student student, CourseType type) throws StudentNotFoundException
	{
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student))
			{
				return registeredStudents[i].getCreditsByCourseType(type);
			}
		}
		
		throw new StudentNotFoundException();
	}
}
