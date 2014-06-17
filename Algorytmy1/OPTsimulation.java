package Algorytmy1;

import Glowne.Memory;
import Glowne.Page;
import Glowne.Process;
import Glowne.MemoryManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class OPTsimulation extends Simulation
{
    public Process current = null;	
    
    public Memory mem = procMan.memory;
    
    public int findGlobal()
    {
        // Wywal stronę która nie pojawi się juz w ogóle
        for(int i = 0 ; i<mem.segments.length ; i++)
            if(!procMan.ciagOdwolan.contains(mem.segments[i]))
                return i;
        
        // Wywal stronę która pojawi się najpóźniej
        Page page = null;
        for(int i = procMan.ciagOdwolan.size()-1 ; i>=0 && page == null ; i--)
        {
            if(procMan.ciagOdwolan.get(i).presenceBit) page=procMan.ciagOdwolan.get(i);
        }
        
        return page.segmentNumber;
    }
    
    public int findLocal(Page page)
    {
        Process owner = page.owner;
        
        // Wywal stronę która nie pojawi się już wcale
        for(Integer n : owner.pula)
        {
            Page strona = mem.segments[n];
            if(!procMan.ciagOdwolan.contains(strona))
                return n;
        }
        
        // Znajdź najpóźniej występującą stronę i ją wywal
        Page pg = null;
        for(Integer n : owner.pula)
        {
            if(pg == null) pg = mem.segments[n];
            else
            {
                if(procMan.ciagOdwolan.lastIndexOf(pg) < procMan.ciagOdwolan.lastIndexOf(mem.segments[n])) pg = mem.segments[n];
            }
        }
        
        return pg.segmentNumber;
    }
    
    public OPTsimulation(MemoryManager procMan)
    {
        super(procMan);
    }
    
    public void serve(Page page)
    {
        int i = (page.owner.pula.isEmpty() ? findGlobal() : findLocal(page));
        mem.segments[i].presenceBit = false;
        mem.segments[i].segmentNumber = -1;
        mem.segments[i] = page;
        mem.segments[i].added = System.nanoTime();
        mem.segments[i].presenceBit = true;
        mem.segments[i].segmentNumber = i;
    }
}
