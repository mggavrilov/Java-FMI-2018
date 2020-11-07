package bg.uni.sofia.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;

public class ListShoppingCart implements ShoppingCart {
	
	private ArrayList<ItemQuantity> cart;
	
	public ListShoppingCart()
	{
		cart = new ArrayList<ItemQuantity>();
	}

	@Override
	public Collection<Item> getUniqueItems() {		
		Collection<Item> returnCollection = new ArrayList<Item>();
		
		for(ItemQuantity itemQuantity : cart)
		{
			returnCollection.add(itemQuantity.getItem());
		}
		
		return returnCollection;
	}

	@Override
	public Collection<Item> getSortedItems() {
		Collection<Item> returnCollection = new ArrayList<Item>();
		
		cart.sort(null);
		
		for(ItemQuantity itemQuantity : cart)
		{
			returnCollection.add(itemQuantity.getItem());
		}
		
		return returnCollection;
	}

	@Override
	public void addItem(Item item) {
		if(item != null)
		{
			int itemIndex = cart.indexOf(new ItemQuantity(item));
			
			if(itemIndex < 0)
			{
				cart.add(new ItemQuantity(item, 1));
			}
			else
			{
				cart.set(itemIndex, new ItemQuantity(item, cart.get(itemIndex).getQuantity() + 1));
			}
		}
	}

	@Override
	public void removeItem(Item item) throws ItemNotFoundException {
		int itemIndex = cart.indexOf(new ItemQuantity(item));
		
		if(itemIndex < 0)
		{
			throw new ItemNotFoundException();
		}
		else
		{
			int itemQuantity = cart.get(itemIndex).getQuantity();
			
			if(itemQuantity > 1)
			{
				cart.set(itemIndex, new ItemQuantity(item, itemQuantity - 1));
			}
			else
			{
				cart.remove(itemIndex);
			}
		}
	}

	@Override
	public double getTotal() {
		double total = 0.0;
		
		for(ItemQuantity itemQuantity : cart)
		{
			total += (itemQuantity.getQuantity() * itemQuantity.getItem().getPrice());
		}
		
		return total;
	}

}
