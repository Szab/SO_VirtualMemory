public class Page
{
    Process owner;
    Memory memory;
    
    boolean presenceBit = false;
    boolean recentlyUsed = false;
    
    long lastUsed = -1;
    long added = -1;
    
    int segmentNumber = -1;
    int calls = 0;
    int errors = 0;
    
    public void call()
    {
        calls++;
        if(presenceBit = false)
        {
            errors++;
            int space = memory.findFreeSegment();
            if(space<0)
            {
                memory.callPageReplace(this);
            }
            else
            {
                presenceBit = true;
                memory.segments[space] = this;
                segmentNumber = space;
                added = System.nanoTime();
            }
        }
        recentlyUsed = true;
        lastUsed = System.nanoTime();
    }
    
    public Page(Process owner, Memory memory)
    {
        this.owner = owner;
        this.memory = memory;
    }
}
