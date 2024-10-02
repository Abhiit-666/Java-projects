package IMDatabase;

import java.lang.System.LoggerFinder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {

    private final Logger logger=Logger.getLogger(Storage.class.getName());
    Map<String,Class<?>> schema;
    //Think of a way to insert into this when data is sent
    public void executeQuery(String query){
        String [] commands=query.split(" ");
        switch(commands[0].toUpperCase()){
            case "INSERT":
            if(schema != null){
                logger.log(Level.SEVERE, ">>Create the Schema first");
            }
            break;
            case "CREATE":
                schema=new HashMap<>();
                // make the user insert like SQL where the attributes come together and the values together.
                // schema.put("id", Long.class);
            break;
            case "SELECT":
                if(schema != null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;
            case "UPDATE":
                if(schema != null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;
            case "DELETE":
                if(schema != null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;
            case "ALTER":
                if(schema != null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;

        }
    }

}
