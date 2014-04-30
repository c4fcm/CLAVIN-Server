package org.mediameter.cliff.places.substitutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSubstitutionMap extends AbstractSubstitutionMap {

    private static final Logger logger = LoggerFactory.getLogger(CustomSubstitutionMap.class);
        
    public CustomSubstitutionMap(String fileName){
        try {
            loadFromFile(fileName,",",';',true,true);
        } catch (IOException e) {
            logger.error("Unable to load substitution list! "+e);
        }
    }

    public CustomSubstitutionMap(String fileName,String separator,char comment,boolean skipFirstRow, boolean ignoreCase){
        try {
            loadFromFile(fileName, separator, comment,skipFirstRow,ignoreCase);
        } catch (IOException e) {
            logger.error("Unable to load substitution list! "+e);
        }
    }
    
    protected void loadFromFile(String fileName,String separator,char comment,boolean skipFirstRow,boolean ignoreCase) throws IOException{
        logger.info("Loading custom substitutions from "+fileName);
        map = new HashMap<String,String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        // drop the table header rows
        if(skipFirstRow) br.readLine();
        // now walk each line
        String row = null;  
        while ((row = br.readLine()) != null) {
            logger.debug(row);
            if(row.length()==0) continue;
            if(row.charAt(0)==comment) continue;    // skip comment lines
            String[] columns = row.split(separator);
            String original = columns[0].trim();
            String replacement = columns[1].trim();
            if(ignoreCase){
                original = columns[0].toLowerCase().trim();
                replacement = columns[1].toLowerCase().trim();
            }
            map.put( original, replacement ); 
        }         
        logger.debug(this.toString());
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\n");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("  " + entry.getKey() + " => " + entry.getValue()+"\n");
        }
        return sb.toString(); 
    }

}

