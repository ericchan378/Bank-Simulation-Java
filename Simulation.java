import java.util.concurrent.Semaphore;

public class Simulation extends Thread
{
	private int meanInterArrivalTime;
	private int meanServiceTime;
	private int numOfTellers;
	private int length;
	private int totalWaitTime;
	private int totalCustomers;
	
	public Simulation(int iATime, int servTime, int tellers, int l)
	{
		this.meanInterArrivalTime = iATime;
		this.meanServiceTime = servTime;
		this.numOfTellers = tellers;
		this.length = l;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() 
	{
		simulate();
		while(this.activeCount() != 2);
		
		System.out.println("Simulation terminated after " + totalCustomers + " customers served");
		double avgWaitTime = ((double)totalWaitTime) / ((double)totalCustomers);
		System.out.printf("Average waiting time = %10.2f", avgWaitTime);
	}

	
	@SuppressWarnings("static-access")
	private void simulate()
	{
		int currTime = 0;
		int customerNumber = 1;
		
		Semaphore sem = new Semaphore(numOfTellers, true);
		
		BankWaitTime getWait = new BankWaitTime()
				{
					@Override
					public synchronized void returnWait(int waitTime) 
					{
						totalWaitTime += waitTime;
					}			
				};
		
		Random_Int_Mean r = new Random_Int_Mean();
		
		while(currTime < this.length)
		{
			int arrivalTime = r.random_int(this.meanInterArrivalTime / 10);
			
			try 
			{
				sleep(arrivalTime * 1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			currTime += (arrivalTime * 10);
			
			if(currTime < this.length)
			{
				Customer c = new Customer(customerNumber, currTime, sem, meanServiceTime);
				c.setWaitTime(getWait);
				c.start();
				customerNumber++;
			}
			
		}
		this.totalCustomers = customerNumber;

		
		
	}
		
}
