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
        Page page = procMan.ciagOdwolan.get(procMan.ciagOdwolan.size()-1);
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(mem.segments[i] == page) return i;
        }
        return -1;
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
        for(int i = procMan.ciagOdwolan.size()-1 ; i>=0 ; i--)
        {
            pg = procMan.ciagOdwolan.get(i);
            if(pg.owner == owner && pg.presenceBit)
            {
                break;
            }
        }
        
        for(int i = 0 ; i<mem.segments.length ; i++)
        {
            if(mem.segments[i] == pg) return i;
        }
        return -1;
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
    }
}
