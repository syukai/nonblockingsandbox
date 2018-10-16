package nonblockinglist2;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber<T> implements Subscriber<T> {
	
	private Subscription subscription;
	private final String name;
	private final int interval;
	
	public MySubscriber(String name, int interval) {
		this.name = name;
		this.interval = interval;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onNext(T item) {
		System.out.println(name + ": " + Thread.currentThread().getName() + ": " + item + ", interval: " + this.interval);
		try {
			Thread.sleep(this.interval);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		System.out.println(name + ": " + Thread.currentThread().getName() + ": Error!");
		
	}

	@Override
	public void onComplete() {
		System.out.println(name + ": " + Thread.currentThread().getName() + ": Complete.");
	}

}
