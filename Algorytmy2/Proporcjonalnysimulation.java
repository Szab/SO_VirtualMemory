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
public class Proporcjonalnysimulation extends Simulation
{
    public Process current = null;	
    public MemoryManager procMan;
    
    public Memory mem;
    
    public Proporcjonalnysimulation(MemoryManager processManager)
    {
               super(processManager);
        procMan = processManager;
    }
    
    public void serve(Page page)
    {
        mem = procMan.memory;
        boolean czyObsluzyc = false;
        if(procMan.workTime == 0) czyObsluzyc = true;   // Na samym początku symulacji
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
            czyObsluzyc = true;
        }
        
        if(!czyObsluzyc) return;
        */
        // Zlicz wszystkie
        int wszystkie = 0;
        for(Process proc : procMan.processList)
            wszystkie += proc.callList.length;
        
        // Ustal procent i przydziel
        for(Process proc : procMan.processList)
        {
            double procent = (double)proc.callList.length/(double)wszystkie;
            double p = procent*(double)mem.segments.length;
            int przydzial = (int)p;
            
            if(przydzial>proc.callList.length) przydzial = proc.callList.length;
            
            for(int i = 0 ; i<przydzial ; i++)
            {
                int wolny = mem.findFreeSegment();
                if(wolny >= 0)
                {
                    proc.pula.add(wolny);
                    mem.segments[wolny] = proc.callList[i];
                    mem.segments[wolny].presenceBit = true;
                    mem.segments[wolny].segmentNumber = wolny;
                    mem.segments[wolny].added = System.nanoTime();
                }
            }
            
        }
        
        // Przydziel pozostałe
        for(int i = mem.findFreeSegment() ; i<mem.segments.length && i>=0 ; i++)
        {
            for(Process proc : procMan.processList)
            {
                proc.pula.add(i);
            }
        }
        
    }
}
