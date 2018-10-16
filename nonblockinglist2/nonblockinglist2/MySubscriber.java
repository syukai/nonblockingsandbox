package nonblockinglist2;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MySubscriber<T> implements Subscriber<T> {
	
	private Subscription subscription;
	private final String name;
	
	public MySubscriber(String name) {
		this.name = name;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		System.out.println(name + ": " + Thread.currentThread().getName() + " onSubscribe " );
		this.subscription.request(1);

	}

	@Override
	public void onNext(T item) {
		System.out.println(name + ": " + Thread.currentThread().getName() + ": " + item );
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
