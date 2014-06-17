public abstract class Simulation 
{
    public MemoryManager procMan;
	
	public Simulation(MemoryManager processManager)
	{
		procMan = processManager;
	}
        
	public abstract void serve(Page page);
}
