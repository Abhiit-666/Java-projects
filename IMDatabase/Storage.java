package IMDatabase;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {


    private final Logger logger=Logger.getLogger(Storage.class.getName());
    Map<String,Class<?>> schema;
    Map<String,List<Object>> data; 
    //Think of a way to insert into this when data is sent,
    public void executeQuery(String query){
        String [] commands=query.split(" ");
        switch(commands[0].toUpperCase()){
            case "INSERT":
            if(schema == null){
                logger.log(Level.SEVERE, ">>Create the Schema first");
            }
            //format INSERT TN (a,v,b,f,) (3,4,5,6,4)
            String IAttributes[] =commands[2].split(",");
            Object Values[]=commands[3].split(",");
            if(IAttributes.length != Values.length){
                logger.log(Level.SEVERE,">> Enter the same number of Attributes and Values");
            }
            for(int i=0;i<IAttributes.length;i++){
                String className=Values[i].getClass().getSimpleName();
                if(className.equalsIgnoreCase(schema.get(IAttributes[i]).toString())){
                    //fetch existing list of elements for the attribute
                    List<Object> values=data.get(IAttributes[i]);
                    //add new value to the list
                    values.add(Values[i]);
                    //update the list
                    data.put(IAttributes[i],values);
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
                boolean catchAll=false;
                for(String vals : SValues){
                    if(vals == "*"){
                        catchAll=true;
                    }
                }
                
                //will need to refine the * query and also the output needs to be in a tabular form
                //using StringBuilder is faster than formatting with printf
                if(SValues.length >= 1 && !catchAll){
                    StringBuilder cols=new StringBuilder();
                    StringBuilder values=new StringBuilder();
                    
                    for(String vals :SValues){
                        
                        cols.append(String.format("%-20s",vals));
                    }
                    System.out.println(cols);
                    System.out.println("--------------------------------------------------------------------------------------------------------------------");
                    //Currently its printing one column at a time. Which is wrong i need to do it row at a time
                    
                    for (int j = 0; j < data.get(SValues[0]).size(); j++) {
                        for (String SValue : SValues) {
                            Object value= data.get(SValue).get(j);
                            values.append(String.format("%-20s", value));
                            
                        }
                            System.out.println(values);
                    }
            
                    
                    logger.log(Level.INFO,">> Data fetched and displayed");
                }
                
                //same the output need to be refined
                else if (catchAll){
                    //Using String builder as it is faster than using multiple printf statements
                    List<String> cloumns=new ArrayList<>(data.keySet());
                    int numofelements=data.get(cloumns.get(0)).size();

                    StringBuilder col=new StringBuilder();
                    for (String column: cloumns)
                    {
                        col.append(String.format("%-20s", column));
                    }
                    System.out.println(col);
                    System.out.println("--------------------------------------------------------------------------------------------------------------------");

                    for (int j = 0; j < numofelements; j++) {
                        StringBuilder val=new StringBuilder();
                        for (String column : cloumns) {
                            val.append(String.format("%-20s", data.get(column).get(j)));
                        }
                        System.out.println(val);
                    }
                    logger.log(Level.INFO,">> Data fetched and displayed");
                }
            break;
            case "UPDATE":
                if(schema == null){
                    logger.log(Level.SEVERE, ">>Create the Schema first");
                }
                //format UPDATE TN val 45 where condition ; val 23 where sadasd ; and;
                //conditions can support mulitplet variables--> where a>2andc<3 but 
                // then must be separated by ands and should not have spaces
                // we could split by where and the parse both sides and split again but that is 
                // unnecessary at this point of time
                String UpdateSplit[]=query.split(";");
                List<String> FieldsToUpdate=new ArrayList<>();
                List<Object> values=new ArrayList<>();
                String UAttribute;
                String UValue;
                List<Map<String,String>> conditions=new ArrayList<>();
                List<List<Map<String,String>>> conList=new ArrayList<>();
                
                for(int i=0;i<UpdateSplit.length;i++){
                    String individualUpdates[]=UpdateSplit[i].split(" ");
                    if(i == 0){
                        UAttribute=individualUpdates[2];
                        UValue=individualUpdates[3];
                        conditions=Utility.parseConditions(individualUpdates[5].split("and"));
                        conList.add(conditions);
                    }else{
                        UAttribute=individualUpdates[0];
                        UValue=individualUpdates[1];
                        conditions=Utility.parseConditions(individualUpdates[3].split("and"));
                        conList.add(conditions);
                    }
                    FieldsToUpdate.add(UAttribute);
                    values.add(UValue);
                    //need to Parse the conditions;
                }

                   //a            b           c
                  // 1            2           3
                  // 4            1           3
                  // 3            1           4 

                for(int i=0; i<FieldsToUpdate.size();i++){
                    List<Object> currentValues = data.get(FieldsToUpdate.get(i));
                    List<Map<String,String>> conditionList= conList.get(i);
                    
                    conditionList.forEach(cond -> {
                        cond.forEach((key,value)->{
                            String conColumn=cond.get("column");
                            List<Object> conColumnValues=data.get(conColumn);
                            String conOperator=cond.get("Operator");
                            String conValue=cond.get("value");

                            for(int j=0;j<conColumnValues.size();j++){
                                switch(conOperator){
                                    case ">":
                                        if(conColumnValues[j] > conValue){}
                                }
                            }

                        });
                    });
                    


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
