package Algorytmy2;


import Glowne.Memory;
import Glowne.Page;
import Glowne.MemoryManager;
import Glowne.Process;
import Algorytmy1.Simulation;
import java.util.HashMap;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Szab
 */
public class Strefowysimulation extends Simulation
{
    public Process current = null;	
    public MemoryManager procMan;
    
    public Memory mem;
    
    public Strefowysimulation(MemoryManager processManager)
    {
               super(processManager);
        procMan = processManager;
    }
    
    public void serve(Page page)
    {
                mem = procMan.memory;
        if(procMan.workTime % 10 != 0 || procMan.workTime == 0) return;
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            Page pg = mem.segments[i];
            mem.segments[i] = null;
            pg.presenceBit = false;
            pg.segmentNumber = -1;           
        }
        
        for(Process proc : procMan.processList)
        {
            // Zlicz unikalne
            ArrayList<Page> unique = new ArrayList<Page>();
            for(Page pg : procMan.callHistory)
            {
                if(pg.owner == proc) 
                {
                    if(!unique.contains(pg))
                    {
                        unique.add(pg);
                    }
                }
            }
            
            // Przydziel ramki
            for(int i = 0 ; i<unique.size() ; i++)
            {
                int j = mem.findFreeSegment();
                if(j >= 0)
                {
                    mem.segments[j] = unique.get(i);
                    unique.get(i).presenceBit = true;
                    unique.get(i).segmentNumber = j;
                    proc.pula.add(j);
                }
            }
        }
    }
}
