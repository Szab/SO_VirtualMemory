package Glowne;
import java.util.ArrayList;

/* Klasa Process: reprezentacja procesu w programie */

public class Process
{
    public int timeCreated = 0; // Jednostka czasu w której utworzono proces
    String id = "";          // Identyfikator procesu
    
    public Page[] callList;    // Lista stron do której będą odwołania.
    public ArrayList<Integer> pula = new ArrayList<Integer>();      // Pula ramek

            
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
    
    // Zwraca ilość błędow w procesie
    public int getErrors()
    {
        int s = 0;
        for(int i = 0 ; i<callList.length ; i++)
            s += callList[i].errors;
        
        return s;
    }
    
    // Zwraca ilość błędów na stronę
    public double getErrorsPerPage()
    {
        return (double)getErrors()/(double)callList.length;
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
}