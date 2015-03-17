package org.syftkog.misc;

import java.util.Map.Entry;
import org.testng.annotations.Test;


/**
 *
 * @author BenjaminLimb
 */
public class OutputAllPropertiesAndEnvironmentVariablesToSystemOut {

  /**
   *
   */
  @Test(groups="simple")
    public void printSystemProperties(){
        System.out.println("Properties");
        for(Entry<Object,Object> prop : System.getProperties().entrySet()){
            System.out.println(prop.getKey()+":"+prop.getValue());
        }
        System.out.println("Environment Variables");
        for(Entry<String,String> prop : System.getenv().entrySet()){
            System.out.println(prop.getKey()+":"+prop.getValue());
        }        
    }
    
    
   
}
