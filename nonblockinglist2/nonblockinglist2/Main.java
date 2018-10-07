package nonblockinglist2;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class Main {
    private static final class ThreadPerTaskExecutor implements Executor {
        ThreadPerTaskExecutor() {}      // prevent access constructor creation
        private Thread th = new Thread();
        public void execute(Runnable r) { r.run(); }
    }
	public static void main(String[] args) throws InterruptedException {
		
//		try(SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>()){
		try(SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>(new ThreadPerTaskExecutor(), Flow.defaultBufferSize())){
//		try(Publisher<Integer> publisher = new SubmissionPublisher<>()){
			Runnable run = new run(publisher);
			Thread th = new Thread(run);
			th.start();
			
//		Subscriber<Integer> subscriber_1 = new MySubscriber<>("subsc-1");
//		publisher.subscribe(subscriber_1);
//		
//		Subscriber<Integer> subscriber_2 = new MySubscriber<>("subsc-2");
//		publisher.subscribe(subscriber_2);
//		
//		Subscriber<Integer> subscriber_3 = new MySubscriber<>("subsc-3");
//		publisher.subscribe(subscriber_3);
//		
//		IntStream.range(0, 5).forEach(i->publisher.submit(i));

		Thread.sleep(10000);
		
		System.out.println("Sleep Finished.(before close)");
		}
		Thread.sleep(1);
		System.out.println("after close?");
		
	}
	
	private static class run implements Runnable{
		run(SubmissionPublisher publisher){
			this.publisher = publisher;
		}
		private static SubmissionPublisher<Integer> publisher; // = new SubmissionPublisher<>(new ThreadPerTaskExecutor(), Flow.defaultBufferSize());

		@Override
		public void run() {
			Subscriber<Integer> subscriber_1 = new MySubscriber<>("subsc-1");
			publisher.subscribe(subscriber_1);
			
			Subscriber<Integer> subscriber_2 = new MySubscriber<>("subsc-2");
			publisher.subscribe(subscriber_2);
			
			Subscriber<Integer> subscriber_3 = new MySubscriber<>("subsc-3");
			publisher.subscribe(subscriber_3);
			
			IntStream.range(0, 5).forEach(i->publisher.submit(i));
			
		}
	}
}
