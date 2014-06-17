package Algorytmy1;

import Glowne.Memory;
import Glowne.Page;
import Glowne.MemoryManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Szab
 */
public class OPTsimulation extends Simulation
{
    public Process current = null;	
    
    public Memory mem = procMan.memory;
    
    public int find()
    {
        for(int i = 0 ; i<mem.segments.length ; i++)
            if(!procMan.ciagOdwolan.contains(mem.segments[i]))
                return i;
        
        Page page = procMan.ciagOdwolan.get(procMan.ciagOdwolan.size()-1);
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(mem.segments[i] == page) return i;
        }
        return -1;
    }
    
    public OPTsimulation(MemoryManager procMan)
    {
        super(procMan);
    }
    
    public void serve(Page page)
    {
        int i = find();
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i] = page;
    }
}
