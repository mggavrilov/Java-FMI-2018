
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
	public boolean registerStudent(Student student) {
		if(registeredStudentsCount < maxStudents)
		{
			for(int i = 0; i < registeredStudentsCount; i++)
			{
				if(registeredStudents[i].equals(student))
				{
					return false;
				}
			}
			
			registeredStudents[registeredStudentsCount++] = student;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int getStudentsCount() {
		return registeredStudentsCount;
	}

	@Override
	public boolean setGrade(Student student, Course course, double grade) {		
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student))
			{
				return registeredStudents[i].gradeCourse(course, grade);
			}
		}
		
		return false;
	}

	@Override
	public double getTotalCredits(Student student) {
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student))
			{
				return registeredStudents[i].getCredits();
			}
		}
		
		return 0.0;
	}

	@Override
	public double getGPA(Student student) {
		for(int i = 0; i < registeredStudentsCount; i++)
		{
			if(registeredStudents[i].equals(student)) {
				return registeredStudents[i].getGPA();
			}
		}
		
		return 0.0;
	}

}
