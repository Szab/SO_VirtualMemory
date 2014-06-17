package Glowne;


import Algorytmy1.Simulation;

public class Memory
{
    public Page[] segments;
    public MemoryManager memMan = null;

    public int findFreeSegment()
    {
        int i;
        for(i = 0 ; i!=segments.length && segments[i] != null; i++);
        return i == segments.length ? -1 : i;
    }
    
    public void callPageReplace(Page page)
    {
        memMan.alg.serve(page);
    }

    public Memory(MemoryManager memMan)
    {
        this.memMan = memMan;
        segments = new Page[10];
    }
}
