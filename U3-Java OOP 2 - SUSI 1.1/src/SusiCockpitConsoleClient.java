import java.util.Scanner;

public class SusiCockpitConsoleClient {

	public static void main(String[] args) {
		SusiCockpit susi = new SusiCockpit();
		
		Scanner sc = new Scanner(System.in);
		
		int choice = -1;
		
		while(choice != 0)
		{
			System.out.println("Welcome to SUSI v0.5");
			System.out.println();
			System.out.println("1. Register student");
			System.out.println("2. Show number of registered students");
			System.out.println("3. Set grade");
			System.out.println("4. Get a student's total credits");
			System.out.println("5. Get a student's GPA");
			System.out.println("6. Get a student's total credits by course type");
			System.out.println();
			System.out.println("0. Exit");
			System.out.println();
			System.out.print("Input: ");
			
			choice = sc.nextInt();
			
			/* 
			 * I could remove the student and course name prompts from (3), (4) and (5)
			 * by setting the name parameter of the anonymous object to "".
			 * However, the Susi interface doesn't imply that the possible courses should be kept track of,
			 * since the setGrade() method accepts a Course object, and not a String.
			 * Furthermore, it doesn't define a function for registering a course.
			 */
			
			switch(choice)
			{
				case 1:
					System.out.print("Enter student's name: ");
					String studentName = sc.next();
					System.out.print("Enter student's faculty number: ");
					int studentFNumber = sc.nextInt();
					
					try
					{
						susi.registerStudent(new Student(studentName, studentFNumber));
						System.out.println("Student " + studentName + ", " + studentFNumber + " registered successfully.");
					}
					catch(StudentAlreadyRegisteredException e)
					{
						System.err.println(e.getMessage());
					}
					catch(CapacityExceededException e)
					{
						System.err.println(e.getMessage());
					}
					
					break;
				case 2:
					System.out.println("Number of currently registered students: " + susi.getStudentsCount());
					break;
				case 3:
					System.out.print("Enter student's name: ");
					studentName = sc.next();
					System.out.print("Enter student's faculty number: ");
					studentFNumber = sc.nextInt();
					System.out.print("Enter course's name: ");
					String courseName = sc.next();
					System.out.print("Enter course's ID: ");
					String courseId = sc.next();
					System.out.print("Enter amount of credits for course: ");
					double credits = sc.nextDouble();
					System.out.print("Enter the type of the course (R = Required, E = Elective, P = Practice): ");
					char courseType = sc.next().charAt(0);
					System.out.print("Enter student's grade for course: ");
					double grade = sc.nextDouble();
					
					try
					{
						susi.setGrade(new Student(studentName, studentFNumber), new Course(courseId, courseName, credits, courseType), grade);
						System.out.println("Grade set successfully.");
					}
					catch(StudentNotFoundException e)
					{
						System.err.println(e.getMessage());
					}
					catch(CapacityExceededException e)
					{
						System.err.println(e.getMessage());
					}
					
					break;
				case 4:
					System.out.print("Enter student's name: ");
					studentName = sc.next();
					System.out.print("Enter student's faculty number: ");
					studentFNumber = sc.nextInt();
					
					try
					{
						System.out.println("Credits: " + susi.getTotalCredits(new Student(studentName, studentFNumber)));
					}
					catch(StudentNotFoundException e)
					{
						System.err.println(e.getMessage());
					}
					
					break;
				case 5:
					System.out.print("Enter student's name: ");
					studentName = sc.next();
					System.out.print("Enter student's faculty number: ");
					studentFNumber = sc.nextInt();
					
					try
					{
						System.out.println("GPA: " + susi.getGPA(new Student(studentName, studentFNumber)));
					}
					catch(StudentNotFoundException e)
					{
						System.err.println(e.getMessage());
					}
					
					break;
				case 6:
					System.out.print("Enter student's name: ");
					studentName = sc.next();
					System.out.print("Enter student's faculty number: ");
					studentFNumber = sc.nextInt();
					System.out.print("Enter the type of the course (R = Required, E = Elective, P = Practice): ");
					courseType = sc.next().charAt(0);
					
					try
					{
						System.out.println("Credits: " + susi.getCreditsPerType(new Student(studentName, studentFNumber), CourseType.valueOf(courseType)));
					}
					catch(StudentNotFoundException e)
					{
						System.err.println(e.getMessage());
					}
					
					break;
				case 7:
					
					break;
				default:
					break;
			}
		}
		sc.close();
	}
}
