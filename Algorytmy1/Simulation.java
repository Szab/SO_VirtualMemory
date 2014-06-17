package Algorytmy1;

import Glowne.Page;
import Glowne.MemoryManager;



public abstract class Simulation 
{
    public MemoryManager procMan;
	
	public Simulation(MemoryManager processManager)
	{
		procMan = processManager;
	}
        
	public abstract void serve(Page page);
}
