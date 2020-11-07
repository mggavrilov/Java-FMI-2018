package bg.uni.sofia.fmi.mjt.shopping;

public class Main {

	public static void main(String[] args) {
		ListShoppingCart cart = new ListShoppingCart();
		
		Item item1 = new Apple("apple1", "a", 18.9);
		for(int i = 0; i < 5_000_000; i++)
		{
			cart.addItem(item1);
		}
		System.out.println(cart.getTotal());
		
		Item item11 = new Chocolate("apple1", "a", 18.9);
		for(int i = 0; i < 5_000_000; i++)
		{
			cart.addItem(item11);
		}
		System.out.println(cart.getTotal());
		for(int i = 0; i < 5_000_000; i++)
		{
			try
			{
				cart.removeItem(item1);
			}
			catch(ItemNotFoundException e)
			{
				
			}
		}
		System.out.println(cart.getTotal());
		System.exit(0);
		Item item2 = new Apple("apple2", "apple2 desc", 10);
		Item item3 = new Chocolate("chocolate1", "chocolate1 desc", 5);
		
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		cart.addItem(item1);
		
		//LinkedList<Item> test = new LinkedList<Item>(cart.getUniqueItems());

		cart.addItem(item2);
		cart.addItem(item2);
		cart.addItem(item2);
		cart.addItem(item2);
		cart.addItem(item2);

		cart.addItem(item3);
		cart.addItem(item3);
		cart.addItem(item3);
		cart.addItem(item3);
		cart.addItem(item3);
		
		
		
		for(Item item : cart.getSortedItems())
		{
			System.out.println(item.getName());
		}
		System.exit(0);
		System.out.println(cart.getTotal());
		
		try
		{
			cart.removeItem(item1);
			System.out.println(cart.getTotal());
			cart.removeItem(item2);
			System.out.println(cart.getTotal());
			cart.removeItem(item3);
			System.out.println(cart.getTotal());
			cart.removeItem(item1);
			System.out.println(cart.getTotal());
			cart.removeItem(item1);
			System.out.println(cart.getTotal());
			cart.removeItem(item1);
			System.out.println(cart.getTotal());
			cart.removeItem(item1);
			System.out.println(cart.getTotal());
		}
		catch(ItemNotFoundException e)
		{
			System.out.println("Item not found.");
		}
	}

}
