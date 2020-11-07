package bg.uni.sofia.fmi.mjt.shopping;

import java.util.Objects;

public class StoreItem implements Item {
	
	protected String name;
	protected String description;
	protected double price;
	
	protected StoreItem(String name, String description, double price)
	{
		this.name = name;
		this.description = description;
		this.price = price;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double getPrice() {
		return price;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof StoreItem))
		{
			return false;
		}
		
		if(o == this)
		{
			return true;
		}
		
		if(((StoreItem)o).getName().equals(name) && ((StoreItem)o).getDescription().equals(description) && ((StoreItem)o).getPrice() == price)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(name, description, price);
	}
}
