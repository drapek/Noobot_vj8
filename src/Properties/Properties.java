/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Properties;

/**
 *
 * @author drapek
 */
public class Properties {
    int nGramType;
    String dbPath;
    
    public Properties() {
        nGramType = 2;
        
    }
    
    public int getNGram() {
        return nGramType;
    }
    
    //nie trzeba zabezpieczać bo w gui jest suwak 2-15
    void setNgram (int n) {
        nGramType = n;
    }
}
