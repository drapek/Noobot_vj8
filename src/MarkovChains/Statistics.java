/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MarkovChains;

import Database.OneSentence;

/**
 *
 * @author drapek
 */
public class Statistics {
    private double actualProbabiltyOfSentece = 1;
    private int NumberOfWordsInDb;
   
    public double getActualProbabilityOfSentence() {
        return actualProbabiltyOfSentece;
    }
    
    public void resetActualProbabilityOfSentence() {
        actualProbabiltyOfSentece = 1;
    }
    
    public void multiplyActualProbabilityOfSentenceByDouble(Double what) {
        if( what > 1 || what < 0) 
            return;
        actualProbabiltyOfSentece *= what;
    }
    
    public void countProbabiltyByWordAndAffectActualProbabilty (String word) {
        int iloscWystSlowa = 0;
        for( OneSentence pojedynczyZbior : MarkovLogic.dbHandler.sentencesCollector ) {
            for( String slowo : pojedynczyZbior.words)
                if( slowo.equals(word) )
                    iloscWystSlowa++;
        }
        
        actualProbabiltyOfSentece *= ( (iloscWystSlowa * 1.0) /  NumberOfWordsInDb);
    }
    
    public void countNumerOfWordsInDb() {
        int sumary = 0;
        for( OneSentence pojedynczyZbior : MarkovLogic.dbHandler.sentencesCollector ) {
            sumary += pojedynczyZbior.words.size();
        }
        NumberOfWordsInDb = sumary;
    }
    
    public int getNumberOfWordsInDb() {
        return NumberOfWordsInDb;
    }
    
}
