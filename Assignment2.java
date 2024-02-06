
public class Assignment2 
{

	public static void main(String[] args) 
	{
		
		int interArrivalTime = Integer.parseInt(args[0]);
		int serviceTime = Integer.parseInt(args[1]);
		int tellers = Integer.parseInt(args[2]);
		int length = Integer.parseInt(args[3]);
		
		System.out.println("Mean inter-arrival time: " + interArrivalTime);
		System.out.println("Mean service time: " + serviceTime);
		System.out.println("Number of tellers: " + tellers);
		System.out.println("Length of simulation: " + length + "\n");
		
		Simulation sim = new Simulation(interArrivalTime, serviceTime, tellers, length);
		sim.start();
		
		try 
		{
			sim.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

}
