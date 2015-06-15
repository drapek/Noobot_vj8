/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author drapek
 */
public class Database {
    private String dbPath;
    public ArrayList<OneSentence> sentencesCollector = new ArrayList<>();
    
    public Database() {
        
        //rozpoznawanie bazy danych
        String platform = System.getProperty("os.name");
        String dbPath = System.getProperty("user.home");
        if( platform.equals("Linux")) 
            dbPath = dbPath + "/.Noobot.dat";
        else if ( platform.equals("Windows"))
            dbPath = dbPath + "\\Local Settings\\Application Data\\Noobot.dat";
        
        this.dbPath = dbPath;
        try {
            readDbFromCSV(dbPath);
        }
            catch(IOException e) {
            
        }
        
    }


    //chwilowo niedostępne - być może roziwnięte zostanie w kolejnej werjsi
    public void readDbFromFile(String Path) throws IOException {
        
        ObjectInputStream input = null;
        try { 
            input = new ObjectInputStream(new FileInputStream(Path));
            sentencesCollector = (ArrayList<OneSentence>) input.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("[Error] Nie odnalazłem pliku z zapisaną bazą danych albo nie mogłem jej wczytać.");
        }
        finally {
            if( input != null) {
                input.close();
            }
            
        }
        
    }
    //chwilowo niedostępne - być może roziwnięte zostanie w kolejnej werjsi
    public void writeDbToActualFile() throws IOException {
        ObjectOutputStream pl = null;
        try {
            pl = new ObjectOutputStream( new FileOutputStream(dbPath));
            pl.writeObject(sentencesCollector);
            pl.flush();
        }
        catch (Exception e) {
            
        }
        finally {
            if( pl != null ) 
                    pl.close();
        }
    }
    
    public void addSentenceToDb(String sentence, String sentenceOrigin) {
        //zapis zbiorów gdzie jest mniej niz 3 słowa nie ma sensu, bo wtedy nie można utworzyc nawet najmiejszego ngramu
        if(sentence.length() < 3)
            return;
        
        OneSentence tmp = new OneSentence(sentence, sentenceOrigin);
        sentencesCollector.add(tmp); 
        
        
    }
    
    public void writeDbAsCSV (String Path) {
        PrintWriter zapisz = null;
        try {
            zapisz = new PrintWriter(Path);
            
            for(OneSentence zbiorSlow : sentencesCollector ) {
                zapisz.print(zbiorSlow.getSenteceOrgin() + ";");
                
                for( String slowo : zbiorSlow.words) {
                    zapisz.print(slowo + ";");
                }
                
                zapisz.print("\n");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("[err] nie mogłem zapisać do bazy, może brak uprawnień");
            // [UWAGA] można tu by dodać powiadomienie w gui o tym fakcie...
        }
        
        if( zapisz != null)
           zapisz.close();
        
    }
    
    //można rzucić wyjątek nie znalezienie pliku na zewnątrz i obsłużyć go z GUI
    public void readDbFromCSV ( String Path) throws FileNotFoundException {
        Scanner in = null;
        
        File file = new File(Path);
        in = new Scanner(file);
        
        this.dbPath = Path;
        ArrayList<OneSentence> newColection = new ArrayList<>();
        
        do {
            String oneLine = null;
         try {   
                oneLine = in.nextLine();
         } catch( Exception e) {
             break;
         }
         
          //gdy koniec pliku
        if( oneLine == null)
            break;
       
        
        String podzielone[] = oneLine.split(";");
        StringBuilder builder = new StringBuilder();
        int i;
        for( i = 1; i < podzielone.length; i++)
            builder.append(podzielone[i]).append(" ");
  
        OneSentence tmp = new OneSentence(builder.toString(), podzielone[0]);
        newColection.add(tmp);
                
        
        } while ( true );
        
        this.sentencesCollector = newColection;
        
        if( in != null)
            in.close();
    }
    
    public String getDbPath() {
        return dbPath;
    }
    
   
}

