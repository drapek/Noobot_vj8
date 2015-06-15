/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MarkovChains;

import Database.Database;
import Database.OneSentence;
import Properties.Properties;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author drapek
 */
public class MarkovLogic {
    static Database dbHandler;
    static Properties propertHandler;
    public static Statistics statystyki;
    
    public MarkovLogic(Database dbHandler, Properties propertHandler) {
        this.dbHandler = dbHandler;
        this.propertHandler = propertHandler;
        statystyki = new Statistics();
    }
    
    public String generateSentence(String UserInput) {
        statystyki.countNumerOfWordsInDb();
        
        String splited[] = UserInput.split(" ");
        Random r = new Random();
        String WygenerowanyTekst = "";
        String ngram[] = new String[propertHandler.getNGram()];
        
        //wpierw policz ile wyrazowa ma byc odpowiedz (gdy userINput.lengt < 3 to dawaj 3... w innym przyapdku wzór)
        int dlugOdpowiedzi;
        
        if(splited.length < 3)
            dlugOdpowiedzi = 3;
        else {
            if( r.nextInt(2) == 0 )
                dlugOdpowiedzi = splited.length - r.nextInt( (int) (0.4 * splited.length) );
            else
                dlugOdpowiedzi = splited.length + r.nextInt( (int) (0.4 * splited.length) );
        }
        
        //losowanie ngramu z tekstu wprowadzone przez użytkownika
        if( splited.length > propertHandler.getNGram()) {
            int startOfNgramInUserInput = r.nextInt( (splited.length - propertHandler.getNGram() + 1) );
            int j;
            for( j = 0; j < ngram.length; j++) 
                ngram[j] = splited[startOfNgramInUserInput + j];
            
        } else {
            int x = 0; //wybiera zbiór słów
            int fuse = 0; //aby nie było nieskończonej pętli
            do {
                x = r.nextInt(dbHandler.sentencesCollector.size()); //gdu przypadkiem zostanie zapisany zbiór słów, z którego nie można utworzyć ani jednego ngramu
                if( fuse == 10) 
                    return ":)";
                fuse++;
                
           } while( (dbHandler.sentencesCollector.get(x)).words.size() < (propertHandler.getNGram() + 1) );
            
           int y;
           y = r.nextInt(dbHandler.sentencesCollector.get(x).words.size() - propertHandler.getNGram());
            
           int j;
            for( j = 0; j < ngram.length; j++) 
                ngram[j] = dbHandler.sentencesCollector.get(x).words.get(y + j);
        }
        
        //dodanie wylosowanego ngramu do wypodziedzi końcowej (inaczej by ucinało zdanie)
        StringBuilder tmp = new StringBuilder();
        for(String slowoNgramu : ngram)
            tmp.append(slowoNgramu).append(" ");
        
        WygenerowanyTekst = tmp.toString();
            
        for (int i = 0; i < dlugOdpowiedzi; i++) {
            ArrayList<String> sufixy = makeSufixTable(ngram);

            if (sufixy.isEmpty()) {
                break; //bo nie może znaleźć kolejnego przejscia, dlatego kończy generacje
            } else {
                int wylosowany = r.nextInt(sufixy.size());
                WygenerowanyTekst = WygenerowanyTekst + " " + sufixy.get(wylosowany);
                statystyki.countProbabiltyByWordAndAffectActualProbabilty(sufixy.get(wylosowany));
               
                //zmiana ngramu
                int z;
                for(z = 0; z < ngram.length - 1; z ++) {
                    ngram[z] = ngram[z + 1];
                    
                }
                ngram[ngram.length - 1] = sufixy.get(wylosowany);
            }

        }
        
        //aby nie było brzydkich pustych pól
        if( WygenerowanyTekst.isEmpty())
            WygenerowanyTekst = "...";
        
        return WygenerowanyTekst;
    }
    
    private ArrayList<String> makeSufixTable (String ngram[]) {
        ArrayList<String> out = new ArrayList<>();
        
        for( OneSentence zbiorSlow : dbHandler.sentencesCollector) {
            int i;
            for(i = 0; i < zbiorSlow.words.size() - (propertHandler.getNGram()); i++ ) {
                int j;
                boolean isEqual = true;
                for( j = 0; j < ngram.length; j++) 
                    if(!(zbiorSlow.words.get(i + j)).equals(ngram[j])) {
                        isEqual = false;
                        break;
                    }
                
                if(isEqual) {
                    out.add(zbiorSlow.words.get(i + propertHandler.getNGram()));
                }
                        
            }
                
        }
        return out;
    }
    
    
}
