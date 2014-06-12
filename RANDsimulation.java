/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Szab
 */
public class RANDsimulation extends Simulation
{
    public Process current = null;	
    public MemoryManager procMan;
    
    public Memory mem = procMan.memory;
    
    public int findPage()
    {
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(mem.segments[i].recentlyUsed = true) mem.segments[i].recentlyUsed = false;
            else return i;
        }
        return mem.segments.length-1;
    }
    
    public RANDsimulation(MemoryManager processManager)
    {
        super(processManager);
    }
    
    public void serve(Page page)
    {
        int i = findPage();
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i].added = System.nanoTime();
        mem.segments[i] = page;
    }
}
