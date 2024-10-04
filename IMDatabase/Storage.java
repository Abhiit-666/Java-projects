package IMDatabase;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {

    private final Logger logger=Logger.getLogger(Storage.class.getName());
    Map<String,Class<?>> schema;
    Map<String,Object> data; 
    //Think of a way to insert into this when data is sent
    public void executeQuery(String query){
        String [] commands=query.split(" ");
        switch(commands[0].toUpperCase()){
            case "INSERT":
            if(schema == null){
                logger.log(Level.SEVERE, ">>Create the Schema first");
            }
            String IAttributes[] =commands[2].split(",");
            Object Values[]=commands[3].split(",");
            if(IAttributes.length != Values.length){
                logger.log(Level.SEVERE,">> Enter the same number of Attributes and Values");
            }
            for(int i=0;i<IAttributes.length;i++){
                String className=Values[i].getClass().getSimpleName();
                if(className.equalsIgnoreCase(schema.get(IAttributes[i]).toString())){
                    data.put(IAttributes[i],Values[i]);
                }
                else{
                    logger.log(Level.WARNING,">> Provide the correct type for the Attribute: "+IAttributes[i]);
                    logger.log(Level.INFO, ">> Insertion Failed Try again with the correct values");
                    break;
                }
            }
            break;
            case "CREATE":
                //Might need to handle of () and refine the Create query later.
                schema=new HashMap<>();
                data=new HashMap<>();
                String CAttributes[] =commands[2].split(",");
                String typeNames[]=commands[3].split(",");
                if(CAttributes.length != typeNames.length)
                {
                    logger.log(Level.SEVERE,">>Enter the same number of Attributes and their types: ");
                    break;
                }
                Class<?>[] types=new Class<?>[typeNames.length];
                for (int i=0;i<typeNames.length;i++){    
                    try {
                        types[i]= Class.forName(typeNames[i].trim());
                    } catch (ClassNotFoundException e) {
                        logger.log(Level.SEVERE, ">>Wrong Type: "+e);
                    }
                }
                for(int i=0;i<CAttributes.length;i++){
                    schema.put(CAttributes[i], types[i]);
                }
                logger.log(Level.INFO,">> Table Created");
                
            break;
            case "SELECT":
                StringBuilder select=new StringBuilder();
                if(schema == null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
                String SValues[]=commands[1].split(",");
                //will need to refine the * query and also the output needs to be in a tabular form
                if(SValues.length >= 1 && !SValues[0].endsWith("*")){
                    for(int i=0;i<SValues.length;i++){
                        select.append(data.get(SValues[i])).append(",");
                    }
                    System.out.println(select.toString());
                    logger.log(Level.INFO,">> Data fetched and displayed");
                }
                //same the output need to be refined
                else{
                    for(Object value:data.values()){
                          select.append(value).append(",");  
                    }
                    System.out.println(select.toString());
                    logger.log(Level.INFO,">> Data fetched and displayed");
                }
            break;
            case "UPDATE":
                if(schema == null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;
            case "DELETE":
                if(schema == null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;
            case "ALTER":
                if(schema == null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
            break;

        }
    }

}
