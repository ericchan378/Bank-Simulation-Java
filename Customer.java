import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Customer extends Thread
{
	private int customerNumber;
	private int arrivalTime;
	private int meanServiceTime;
	private Semaphore sem;
	
	private BankWaitTime bankWaitTime;
	private int wait;
	
	public Customer (int number, int arr, Semaphore s, int sT)
	{
		this.customerNumber = number;
		this.arrivalTime = arr;
		this.sem = s;
		this.meanServiceTime = sT;
	}
	
	public void setWaitTime(BankWaitTime bankWaitTime)
	{
		this.bankWaitTime = bankWaitTime; 
	}
	
	@Override
	public void run() 
	{
		runCustomer();
		bankWaitTime.returnWait(wait);
	}
	
	@SuppressWarnings("static-access")
	private synchronized void runCustomer()
	{
		Random_Int_Mean r = new Random_Int_Mean();
		int timeToServe = r.random_int(this.meanServiceTime / 10);
		System.out.printf("At time %20d, customer %20d arrives in line\n", arrivalTime, customerNumber);
		
		long startTime = System.nanoTime();
		try 
		{
			sem.acquire();
		} 
		catch (InterruptedException e) 
		{

			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		
		int waitTime = (int) TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
		
		this.wait = waitTime * 10;

		int serveStart = arrivalTime + (waitTime * 10);
		
		System.out.printf("At time %20d, customer %20d starts being served\n", serveStart, customerNumber);
		
		try 
		{
			sleep(timeToServe * 1000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		int leaveTime = (timeToServe * 10) + serveStart;
		
		System.out.printf("At time %20d, customer %20d leaves the bank\n", leaveTime, customerNumber);
		
		sem.release(); 
		
	}
	
}
