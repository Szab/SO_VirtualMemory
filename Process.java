/* Klasa Process: reprezentacja procesu w programie */

public class Process
{
    public int timeCreated = 0; // Jednostka czasu w której utworzono proces
    String id = "";          // Identyfikator procesu
    
    Page[] callList;    // Lista stron do której będą odwołania.
    int currentCall = 0;
            
    // Zwraca pozostałą liczbę odwołań
    public int getRemaining()
    {
        int r = 0;
        for(int i = 0 ; i<callList.length ; i++)
        {
            if(callList[i] != null) r++;
        }
        return r;
    }
    
    // Konstruktor dwuparametrowy dla procesów wczytywanych na początku	
    public Process(String id, Integer duration)
    {
        this(id, duration, 0);
    }
    
    // Właściwy konstruktor
    public Process(String id, Integer timeCreated, int pages)
    {
	this.timeCreated = timeCreated;
        this.callList = new Page[pages];
        this.id = id;
    }
    
    public boolean isDone()
    {
        for(int i = 0 ; i<callList.length ; i++)
        {
            if(callList[i] != null) return true;
        }
        return false;
    }
}