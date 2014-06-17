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
public class Stalysimulation extends Simulation
{
    public Process current = null;	
    public MemoryManager procMan;
    
    public Memory mem;
    
    public Stalysimulation(MemoryManager processManager)
    {
        super(processManager);
                procMan = processManager;
    }
    
    public void serve(Page page)
    {
        mem = procMan.memory;
        if(procMan.workTime != 0) return;
        
        int najmniejsza = procMan.processList.get(0).callList.length;
        
        for(Process proc : procMan.processList)
        {
            if(proc.callList.length < najmniejsza) najmniejsza = proc.callList.length;
        }
        
        for(Process proc : procMan.processList)
        {
            for(int i = 0 ; i<najmniejsza ; i++)
            {
                int n = mem.findFreeSegment();
                if(i<proc.callList.length)
                {
                    mem.segments[n] = proc.callList[i];
                    mem.segments[n].presenceBit = true;
                    mem.segments[n].segmentNumber = n;
                }
                if(n>=0) proc.pula.add(n);
            }
        }
        
        // Przydziel pozostałe
        while(mem.findFreeSegment() != -1)
        {
            for(Process proc : procMan.processList)
            {
                proc.pula.add(mem.findFreeSegment());
            }
        }
        
    }
}
