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
        /*
        // Sprawdź czy jakiś proces nie zakończył już działania
        Process proc1 = null;
        for(Process proc : procMan.processList)
        {
            boolean kontrolne = true;
            for(int i = 0 ; i<proc.callList.length ; i++)
            {
                if(procMan.ciagOdwolan.contains(proc.callList[i])) kontrolne = false;
            }
            if(kontrolne)
            {
                proc1 = proc;
            } 
        }
        if(proc1 != null) 
        {
            procMan.processList.remove(proc1);
        }
        */
        if(procMan.workTime % (int)(10*((double)procMan.callHistory.size()+(double)procMan.ciagOdwolan.size())/(double)procMan.sumCalls()) != 0 || procMan.workTime == 0) return;
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            Page pg = mem.segments[i];
            if(mem.segments[i] != null)
            {
                mem.segments[i] = null;
                pg.presenceBit = false;
                pg.segmentNumber = -1;           
            }
            
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
                    unique.get(i).added = System.nanoTime();
                    proc.pula.add(j);
                }
            }
        }
    }
}
