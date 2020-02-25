import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentHashMapImplementation {

	static Map<String, AtomicLong> ordersMap = new ConcurrentHashMap<>();

	static void processOrders() {
		double currentTime = System.currentTimeMillis();
		System.out.println("Cuurent time ::  "+ System.currentTimeMillis());
		for(int x =0; x<500000; x++) {
			for (String city : ordersMap.keySet()) {
//				for (int i = 0; i < 25; i++) {
					((AtomicLong) ordersMap.get(city)).getAndIncrement();
					ordersMap.put(String.valueOf(System.currentTimeMillis()/1000), new AtomicLong());
//				}
			}
		}
		double elapsedTime = System.currentTimeMillis();
		System.out.println("Time taken  ::  "+(elapsedTime-currentTime));
	}

	public static void main(String[] args) throws InterruptedException {
		ordersMap.put("Delhi", new AtomicLong());
		ordersMap.put("London", new AtomicLong());
		ordersMap.put("New York", new AtomicLong());
		ordersMap.put("Sydney", new AtomicLong());
		int threads = Integer.parseInt(args[0]);

		ExecutorService service = Executors.newFixedThreadPool(threads);

		service.submit(new Runnable() {
			@Override
			public void run() {
				processOrders();
			}
		});

		service.awaitTermination(1, TimeUnit.SECONDS);
		service.shutdown();
		System.out.println(ordersMap);
	}
}