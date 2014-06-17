package Glowne;

/* Klasa ProcessManager:
- Obsługa generatora procesów
- Zliczanie wykonań
- Generowanie statystyk
- Wybór algorytmu
*/

import Algorytmy1.Simulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class MemoryManager
{
    public int workTime = 0; // Liczba zrealizowanych kwantów czasu
    
    public ArrayList<Process> processList = new ArrayList<Process>();  // Lista procesów do zrealizowania
    public ArrayList<Page> ciagOdwolan = new ArrayList<Page>();    // Globalny ciąg odwołań
    public ArrayList<Page> callHistory = new ArrayList<Page>();     // Historia wywolan
    
    public Memory memory;   // Pamięć
    
    private IOController controller; // Kontroler wejścia/wyjścia
    public Simulation alg;
    public Simulation additionalSim;
    
    
    // Stworz ciag odwolan
    public void createCallList()
    {
        ciagOdwolan = new ArrayList<Page>();
        for(Process proc : processList)
        {
            for(int i = 0 ; i<proc.callList.length ; i++)
                ciagOdwolan.add(proc.callList[i]);
        }
        java.util.Collections.shuffle(ciagOdwolan);
    }
    
    // Zwraca sumę ciągu odwołań
    public int sumRemainingCalls()
    {
        return ciagOdwolan.size();
    }
    
    // Suma stron
    public int sumPages()
    {
        int i = 0;
        for(Process proc : processList)
            i += proc.callList.length;
        return i;
    }
    
    // Zwraca sumę błędów
    public int sumErrors()
    {
        int err = 0;
        for(Process proc : processList)
            err += proc.getErrors();
        return err;
    }
    
    // Zwraca sumę odwołań
    public int sumCalls()
    {
        int calls = 0;
        for(Process proc : processList)
        {
            for(int i = 0 ; i<proc.callList.length ; i++)
                calls += proc.callList[i].calls;
        }
        return calls;
    }
    
    // Błędów na odwołanie
    public Double getErrorsPerCalls()
    {
        return (double)sumErrors()/(double)sumCalls();
    }
    
    // Błędów na stronę
    public Double getErrorsPerPage()
    {
        int pages = 0;
        int errors = 0;
        for(Process proc : processList)
        {
            pages += proc.callList.length;
            for(int i = 0 ; i<proc.callList.length ; i++)
            {
                errors += proc.callList[i].errors;
            }
        }
        return (double)errors/(double)pages;
        
    }
    
    
    // Generuje losowe ciągi odwołań
    private void processGenerator(int maxProc, int maxPage)
    {
        java.util.Random rand = new java.util.Random();
        
        int proc = rand.nextInt(maxProc-1)+1;   // Losowanie ilości procesów

        for(int i = 0 ; i<proc ; i++)
        {
           String randomID = UUID.randomUUID().toString(); // Generowanie losowego ID
           Process newProcess = new Process(randomID, workTime, rand.nextInt(maxPage-1)+1); // Tworzenie nowego procesu z losową ilością odwołań
           processList.add(newProcess);
           
           int uniquePages = newProcess.callList.length;  // Tworzenie nowego zbioru stron
           for(int j = 0 ; j<uniquePages ; j++)
           {
               Page newPage = new Page(newProcess, memory);
               newProcess.callList[j] = newPage;
               ciagOdwolan.add(newPage);
           }
           
           int r = rand.nextInt(Math.abs(maxProc-uniquePages)+1);
           for(int k = 0 ; k<r ; k++)
           {
               ciagOdwolan.add(newProcess.callList[rand.nextInt(uniquePages)]);
           }
           
        }
        Collections.shuffle(ciagOdwolan);
    }
        
    public void nextStep() //kolejny krok (jednostka czasu)
    {        
        if(additionalSim != null) additionalSim.serve(null);
        if(!ciagOdwolan.isEmpty()) ciagOdwolan.get(0).call();
        callHistory.add(ciagOdwolan.remove(0));
        workTime++;
        controller.update(); 
    }
    
    public void endSimulation() //przeskoczenie do końca symulacji
    {       
        while(!ciagOdwolan.isEmpty())
        {
        	nextStep();
        }
        controller.update(); 
    }
    
    // Inicjalizuje PM
    public void initialize(Simulation sim, Simulation sim2)
    {
        alg = sim;
        additionalSim = sim2;
        controller = new IOController(this);
        controller.initialize();
    }
    
    public MemoryManager(int maxProc, int maxPage, int ramek)
    {        
        memory = new Memory(this, ramek);
        processGenerator(maxProc, maxPage);
    }
    
}
