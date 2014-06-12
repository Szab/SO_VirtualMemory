/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Szab
 */
public class AprLRUsimulation extends Simulation
{
    public Process current = null;	
    public MemoryManager procMan;
    
    public Memory mem = procMan.memory;
    
    public int findLongestIndex()
    {
        int index = -1;
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(index != -1)
            {
                if(mem.segments[index].lastUsed > mem.segments[i].lastUsed) index = i;
            }
            else index = i;
        }
        return index;
    }
    
    public AprLRUsimulation(MemoryManager processManager)
    {
        super(processManager);
    }
    
    public void serve(Page page)
    {
        int i = findLongestIndex();
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i].added = System.nanoTime();
        mem.segments[i] = page;
    }
}
