/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.ArrayList;

/**
 *
 * @author drapek
 */
public class OneSentence {
    private String sentenceOrgin;
    public ArrayList<String> words = new ArrayList<>();

    public OneSentence(String sentence, String sentenceOrigin) {
        this.sentenceOrgin = sentenceOrigin;
        
        //rozdziel po spacji
        String slowa[] = sentence.split(" ");
        for ( String slowo : slowa) {
            words.add(slowo);
        }
    }
    
    public String getSenteceOrgin() {
        return sentenceOrgin;
    }
    
    
}
