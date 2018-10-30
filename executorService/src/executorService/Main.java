package executorService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		
		ExecutorService executorService = 
				  new ThreadPoolExecutor(1, 5, 0L, TimeUnit.MILLISECONDS,   
				  new LinkedBlockingQueue<Runnable>());
		
		Runnable runnableTask = () -> {
			try {
				System.out.println("runnable start.[" + Thread.currentThread().getName() + "]");
				TimeUnit.MILLISECONDS.sleep(300);
				System.out.println("runnable finished.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
			
		Callable<String> callableTask = () -> {
			System.out.println("callable start.[" + Thread.currentThread().getName() + "]");
			TimeUnit.MILLISECONDS.sleep(300);
			System.out.println("callable finished.");
			// ここでshutdownしたらどうなる？ 
//			executorService.shutdownNow();
			// -> ここでexecutorServiceが終了するので次のexecuteが失敗する
			//    java.util.concurrent.RejectedExecutionException
			return "Task's execution";
		};
		
		List<Callable<String>> callableTasks = new ArrayList<>();
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		
//		executorService.execute(runnableTask);
		
		System.out.println("submit st.");
		Future<String> future = executorService.submit(callableTask);
		try {
			System.out.println(future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("invokeAny st.");
			// invokeAnyのタスク内でshutdownNow()したら?
			// -> ここでexception.(result.がでない) 次のタスクが実行できずにしぬ 
			String result = executorService.invokeAny(callableTasks);
			System.out.println("result:" + result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("invokeAll st.");
			List<Future<String>> futures = executorService.invokeAll(callableTasks);
			futures.forEach(f->{
				try {
					// ここでシャットダウンしたらどうなる？ -> すでに処理が終わってるので特になんともない
					System.out.println(f.get());
					executorService.shutdownNow();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("shutdown!");
		executorService.shutdown();
		
		System.out.println("End Of ExecutorService");
		
		CyclicBarrier barrier = new CyclicBarrier(3, ()-> {System.out.println("barrier.");});
		Runnable r1 = () -> {
			System.out.println("r1 start." + Thread.currentThread().getName());
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("finished.");
		};
		
		var t1 = new Thread(r1, "r-t1");
		var t2 = new Thread(r1, "r-t2");
		var t3 = new Thread(r1, "r-t3");
		
		if(barrier.isBroken()) {
			t1.start();
			t2.start();
			t3.start();
		}
		System.out.println("r-complete");
	}
}
