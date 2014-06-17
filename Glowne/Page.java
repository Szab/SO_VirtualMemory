package Glowne;

public class Page
{
    public Process owner;
    public Memory memory;
    
    public boolean presenceBit = false;
    public boolean recentlyUsed = false;
    
    public long lastUsed = -1;
    public long added = -1;
    
    public int segmentNumber = -1;
    public int calls = 0;
    public int errors = 0;
    
    public void call()
    {
        calls++;
        if(presenceBit == false)
        {
            errors++;
            
            int space = -1;
            if(owner.pula.isEmpty())
            {
                space = memory.findFreeSegment();
            }
            else
            {
                for(Integer i : owner.pula)
                {
                    if(memory.segments[i] == null) space = i;
                }
            }
            
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
