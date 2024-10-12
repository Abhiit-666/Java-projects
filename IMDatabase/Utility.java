package IMDatabase;

import java.util.*;

public class Utility {

   public static List<Map<String,String>> parseConditions(String[] Conditions){
   
    List<Map<String,String>> conditionList=new ArrayList<>();   
    for(String condition : Conditions){

        Map<String, String> conditionMap=new HashMap<>();
    
        int operatorindex=-1;
    
        for(int i=0;i<condition.length();i++){
            char ch=condition.charAt(i);
            if(ch == '<' || ch == '>' || ch == '!' || ch == '='){
                operatorindex=i;
                break;
            }
        }
    
        if(operatorindex != -1){
            conditionMap.put("column",condition.substring(0,operatorindex));
            
            if(operatorindex + 1 < condition.length() && 
                (condition.charAt(operatorindex+1) == '=')){
                    conditionMap.put("Operator",condition.substring(operatorindex,operatorindex+2));
                    conditionMap.put("value",condition.substring(operatorindex+2).trim());
                }
    
                else{
                    conditionMap.put("Operator",condition.substring(operatorindex,operatorindex+1));
                    conditionMap.put("value",condition.substring(operatorindex+1).trim());
                }
        
        }

        conditionList.add(conditionMap);        
    }

    return conditionList;
   }


   public static Index createOrupdateIndex(String column,Map<String,List<Object>> data){
        if(!data.containsKey(column)){
            //log the error
        }

        Index index=new Index();
        //need to check if an update is happening or just the creation of an index
        List<Object> values=data.get(column);

        for(int ri=0;ri<values.size();ri++){
            index.addToHashIndex(values.get(ri), ri);
        }
        return index;
   }
}
