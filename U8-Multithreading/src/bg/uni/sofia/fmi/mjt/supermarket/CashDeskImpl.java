package bg.uni.sofia.fmi.mjt.supermarket;

public class CashDeskImpl implements CashDesk {

    private static final int MAX_CASH = 100;
    
    private double currentAmount;

    public CashDeskImpl() {
    	currentAmount = 0;
    }

    @Override
    public void serveCustomer(Customer customer) {
        
    }

    @Override
    public double getAmount() {
    	return currentAmount;
    }

    @Override
    public synchronized void setAmount(double amount) {
    	this.currentAmount += amount;
    }

}