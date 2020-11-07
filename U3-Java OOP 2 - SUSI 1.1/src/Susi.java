public interface Susi {
  
  /**
  * Registers a student in the system.
  * @throws StudentAlreadyRegisteredException, if the student is already registered
  * @throws CapacityExceededException, if the university capacity is exceeded
  */
  void registerStudent(Student student) throws StudentAlreadyRegisteredException, CapacityExceededException;

  /**
   * Returns the number of registered students
   */
  int getStudentsCount();
  
  /**
  * Sets a grade for the student for the specified course and adds the credits of the
  * course to the total credits of the student.
  * @throws StudentNotFoundException, if the student is not found
  * @throws CapacityExceededException, if the student already took the maximum number of courses
  */
  void setGrade(Student student, Course course, double grade) throws StudentNotFoundException,
                                                             CapacityExceededException;
  
  /**
  * Returns the total sum of credits for this student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getTotalCredits(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the grade point average for the given student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getGPA(Student student) throws StudentNotFoundException;
  
  /**
  * Returns the sum of credits per course type accumulated by the specified student
  * @throws StudentNotFoundException, if the student is not found
  */
  double getCreditsPerType(Student student, CourseType type) throws StudentNotFoundException;

}