package bg.uni.sofia.fmi.mjt.shopping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapShoppingCart implements ShoppingCart {
	
	private HashMap<Item, Integer> cart;
	
	public MapShoppingCart()
	{
		cart = new HashMap<Item, Integer>();
	}

	@Override
	public Collection<Item> getUniqueItems() {
		return cart.keySet();
	}

	@Override
	public Collection<Item> getSortedItems() {
		ArrayList<ItemQuantity> cartList = new ArrayList<ItemQuantity>();
		
		for(Map.Entry<Item, Integer> entry : cart.entrySet())
		{
			cartList.add(new ItemQuantity(entry.getKey(), entry.getValue()));
		}
		
		Collection<Item> returnCollection = new ArrayList<Item>();
		
		cartList.sort(null);
		
		for(ItemQuantity itemQuantity : cartList)
		{
			returnCollection.add(itemQuantity.getItem());
		}
		
		return returnCollection;
	}

	@Override
	public void addItem(Item item) {
		if(item != null)
		{
			if(cart.containsKey(item))
			{
				cart.put(item, cart.get(item) + 1);
			}
			else
			{
				cart.put(item, 1);
			}
		}
	}

	@Override
	public void removeItem(Item item) throws ItemNotFoundException {
		if(cart.containsKey(item))
		{
			if(cart.get(item) > 1)
			{
				cart.put(item, cart.get(item) - 1);
			}
			else
			{
				cart.remove(item);
			}
		}
		else
		{
			throw new ItemNotFoundException();
		}
	}

	@Override
	public double getTotal() {
		double total = 0.0;
		
		for(Map.Entry<Item, Integer> entry : cart.entrySet())
		{
			total += (entry.getKey().getPrice() * entry.getValue());
		}
		
		return total;
	}

}
