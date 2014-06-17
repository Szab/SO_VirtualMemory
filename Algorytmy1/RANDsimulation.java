package Algorytmy1;

import Glowne.Memory;
import Glowne.MemoryManager;
import Glowne.Page;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class RANDsimulation extends Simulation
{
    public Process current = null;	
    
    public Memory mem = procMan.memory;
    
    public RANDsimulation(MemoryManager processManager)
    {
        super(processManager);
    }
    
    public void serve(Page page)
    {
        int i;
        java.util.Random rand = new java.util.Random();
        if(page.owner.pula.isEmpty())
        {
            i = rand.nextInt(mem.segments.length);           
        }
        else
        {
            i = page.owner.pula.get(rand.nextInt(page.owner.pula.size()));
        }
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i].added = System.nanoTime();
        mem.segments[i] = page;
    }
}
