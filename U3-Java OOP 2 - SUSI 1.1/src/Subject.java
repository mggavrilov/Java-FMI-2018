public interface Subject {
	void setId(String id);
	String getId();
	
	void setName(String name);
	String getName();
	
	CourseType getType();
}