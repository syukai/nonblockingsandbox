package synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
	
	private static class Calc{
		private static int staticSum = 0;
	    private int sum = 0;
	    private int getSum() {return this.sum;}
	    private void setSum(int value) {this.sum = value;}
	    
	    public void calculate() {
	        setSum(getSum() + 1);
	    }
	    public void directCalculate() {
	    	sum++;
	    }
	    public synchronized void syncDirectCalculate() {
	    	sum++;
	    }
	    public void syncblockDirectCalculate() {
	    	synchronized(this) {
	    		sum++;
	    	}
	    }
	    
	    public static int getStaticSum() {return staticSum;}
	    public static void resetStaticSum() {staticSum = 0;}
	    public static void staticDirectCalculate() {
	    	staticSum++;
	    }
	    public static void staticSyncblockDirectCalculate() {
	    	synchronized(Calc.class) {
	    		staticSum++;
	    	}
	    }
	}

	public static void main(String[] argv) throws InterruptedException {
	    ExecutorService service = Executors.newFixedThreadPool(3);
	    Calc summation = new Calc();
	 
	    IntStream.range(0, 1000)
	      .forEach(count -> service.submit(summation::calculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	 
	    // 1000にならない！！！
	    System.out.println(summation.getSum());
	    
	    summation.setSum(0);
	    
	    IntStream.range(0,  1000)
	    	.forEach(count -> service.submit(summation::directCalculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	    // やっぱり1000にならない
	    System.out.println(summation.getSum());
	    
	    summation.setSum(0);
	    
	    IntStream.range(0,  1000)
	    	.forEach(count -> service.submit(summation::syncDirectCalculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	    // 1000になる！！！
	    System.out.println(summation.getSum());
	    
	    summation.setSum(0);

	    IntStream.range(0, 1000)
	    	.forEach(count -> service.submit(summation::syncblockDirectCalculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	    // 1000になる！！！
	    System.out.println(summation.getSum());
	    
	    
	    IntStream.range(0, 1000)
	    	.forEach(c -> service.submit(Calc::staticDirectCalculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	    // 1000にならない
	    System.out.println(Calc.getStaticSum());
	    
	    Calc.resetStaticSum();
	    
	    IntStream.range(0, 1000)
	    	.forEach(c -> service.submit(Calc::staticSyncblockDirectCalculate));
	    service.awaitTermination(1000, TimeUnit.MILLISECONDS);
	    // 1000になる！！！
	    System.out.println(Calc.getStaticSum());
	    service.shutdown();
	}
}
