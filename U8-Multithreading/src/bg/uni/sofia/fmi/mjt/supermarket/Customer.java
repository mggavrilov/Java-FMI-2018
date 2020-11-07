package bg.uni.sofia.fmi.mjt.supermarket;

public class Customer implements Runnable {

    private int serviceTime;
    private double totalPrice;
    private CashDesk cashDesk;

    public Customer(CashDesk cashDesk, int serviceTime, double totalPrice) {
        this.cashDesk = cashDesk;
        this.totalPrice = totalPrice;
        this.serviceTime = serviceTime;
    }

    @Override
    public void run() {
    	
    }

    public double buyGoods() throws InterruptedException {
    	Thread.sleep(serviceTime);
    	return totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getServiceTime() {
        return serviceTime;
    }

}