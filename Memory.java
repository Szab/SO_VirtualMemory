public class Memory
{
    public Page[] pages;
    public Page[] segments;
    
    private Simulation alghoritm;
    
    public int findFreeSegment()
    {
        int i;
        for(i = 0 ; i<segments.length && segments[i] != null; i++);
        return i == segments.length ? -1 : i;
    }
    
    public int findFreePage()
    {
        int i;
        for(i = 0 ; i<pages.length && pages[i] != null; i++);
        return i == pages.length ? -1 : i;      
    }
    
    public void callPageReplace(Page page)
    {
        alghoritm.serve(page);
    }

    public Memory(Simulation sim)
    {
        this.alghoritm = sim;
    }
}
