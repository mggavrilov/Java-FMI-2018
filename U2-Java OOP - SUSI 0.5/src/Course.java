
public class Course implements Subject {

	private String id;
	private String name;
	private double credits;
	
	Course() {
		id = "";
		name = "";
		credits = 0;
	}
	
	Course(String id, String name, double credits)
	{
		this.id = id;
		this.name = name;
		this.credits = credits;
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

}
