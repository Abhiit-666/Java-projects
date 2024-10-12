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
}
