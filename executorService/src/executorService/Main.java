package executorService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
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
			return "Task's execution";
		};
		
		List<Callable<String>> callableTasks = new ArrayList<>();
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		
		executorService.execute(runnableTask);
		
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
		
		System.out.println("End Of Main");
	}
}
