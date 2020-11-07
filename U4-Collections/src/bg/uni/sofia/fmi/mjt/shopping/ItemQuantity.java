package bg.uni.sofia.fmi.mjt.shopping;

public class ItemQuantity implements Comparable<ItemQuantity> {
	private Item item;
	private int quantity;
	
	public ItemQuantity(Item item, int quantity)
	{
		this.item = item;
		this.quantity = quantity;
	}
	
	public ItemQuantity(Item item)
	{
		this.item = item;
		quantity = 0;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setItem(Item item)
	{
		this.item = item;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null || !(o instanceof ItemQuantity))
		{
			return false;
		}
		
		if(o == this || ((ItemQuantity)o).getItem().equals(item))
		{
			return true;
		}
		
		return false;
	}

	@Override	
	public int compareTo(ItemQuantity other)
	{
		//descending order
		if(quantity < other.quantity)
		{
			return 1;
		}
		else if(quantity > other.quantity)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
