/* Klasa ProcessManager:
- Obsługa generatora procesów
- Zliczanie wykonań
- Generowanie statystyk
- Wybór algorytmu
*/

import java.util.ArrayList;
import java.util.UUID;

public class MemoryManager
{
    public int workTime = 0; // Liczba zrealizowanych kwantów czasu
    
    public ArrayList<Process> processList; // Lista procesów do zrealizowania
    
    public Memory memory;   // Pamięć
    
    private IOController controller; // Kontroler wejścia/wyjścia
    
    // Zwraca sumę ciągu odwołań
    public int sumRemainingCalls()
    {
        int rc = 0;
        for(Process proc : processList)
            rc += proc.getRemaining();
        return rc;
    }
    
    // Zwraca sumę błędów
    public int sumErrors()
    {
        int errors = 0;
        for(int i = 0 ; i<memory.pages.length ; i++)
            errors += memory.pages[i].errors;
        return errors;
    }
    
    // Zwraca sumę odwołań
    public int sumCalls()
    {
        int calls = 0;
        for(int i = 0 ; i<memory.pages.length ; i++)
            calls += memory.pages[i].calls;
        return calls;
    }
    
    // Błędów na odwołanie
    public Double getErrorsPerCalls()
    {
        return (double)sumErrors()/(double)sumCalls();
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
           int uniquePages = rand.nextInt(newProcess.callList.length-1)+1;  // Tworzenie nowego zbioru stron
           for(int j = 0 ; j<uniquePages ; j++)
           {
               Page newPage = new Page(newProcess, memory);
               newProcess.callList[j] = newPage;
               memory.pages[memory.findFreePage()] = newPage;
           }
           
           for(int k = uniquePages ; k<newProcess.callList.length ; k++)
           {
               newProcess.callList[k] = newProcess.callList[rand.nextInt(k)];
           }
        }
    }
        
    // Inicjalizuje PM
    public void initialize(Simulation sim)
    {
        memory = new Memory(sim);
        controller.initialize();
    }
    
    public MemoryManager(int maxProc, int maxPage)
    {
        this.processList = new ArrayList<Process>();
        processGenerator(maxProc, maxPage);
        controller = new IOController(this);
    }
    
}
