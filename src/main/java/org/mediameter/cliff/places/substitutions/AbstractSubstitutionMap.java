package org.mediameter.cliff.places.substitutions;

import java.util.HashMap;

public abstract class AbstractSubstitutionMap {

    protected HashMap<String,String> map;

    public int getSize(){
        return map.keySet().size();
    }
    
    public boolean contains(String candidate){
        return map.keySet().contains(candidate.toLowerCase());
    }

    public String getSubstitution(String candidate) {
        return map.get(candidate.toLowerCase());
    }
    
    public String substituteIfNeeded(String candidate){
        if (contains(candidate)){
            return getSubstitution(candidate);
        }
        return candidate;
    }
        
}