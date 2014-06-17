package Algorytmy1;

import Glowne.MemoryManager;
import Glowne.Memory;
import Glowne.Page;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class FIFOsimulation extends Simulation
{
    public Process current = null;	
    
    public Memory mem = procMan.memory;
    
    public int findLongestIndexGlobal()
    {
        int index = -1;
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(index != -1)
            {
                if(mem.segments[index].added > mem.segments[i].added) index = i;
            }
            else index = i;
        }
        return index;
    }
    
    public int findLongestIndexLocal(Page page)
    {
        int index = -1;
        for(Integer i : page.owner.pula)
        {
            if(index != -1)
            {
                if(mem.segments[index].added > mem.segments[i].added) index = i;
            }
            else index = i;
        }
        return index;
    }
    
    
    public FIFOsimulation(MemoryManager processManager)
    {
        super(processManager);
    }
    
    public void serve(Page page)
    {
        int i = (page.owner.pula.isEmpty() ? findLongestIndexGlobal() : findLongestIndexLocal(page));
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i].added = System.nanoTime();
        mem.segments[i] = page;
                mem.segments[i].added = System.nanoTime();
        mem.segments[i].presenceBit = true;
        mem.segments[i].segmentNumber = i;
    }
}
