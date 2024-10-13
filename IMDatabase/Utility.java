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

   public static Set<Object> convertType(Set<Object> keys, String value,String operator){
            
            Set<Object> resultSet= operation(keys,value,operator);
        return resultSet;
   }
 
   //from the indexed row check which values are valid to find the rows for them in the Storage class
   public static Set<Object> operation(Set<Object> keys, Object value, String operator){
    Set<Object> resultSet=new HashSet<>();
    for(Object key : keys){
        if(key instanceof Integer){
            int Ikey=(Integer) key;
            int Ivalue=(Integer) value;
            switch(operator){
                case "<":
                    if(Ikey < Ivalue){
                        resultSet.add(key);
                    }
                break;
                case ">":
                    if(Ikey > Ivalue){
                        resultSet.add(key);
                    }
                break;
                case "=":
                    if(Ikey == Ivalue){
                        resultSet.add(key);
                    }
                break;
                case "!=":
                    if(Ikey != Ivalue){
                        resultSet.add(key);
                    }
                break;
                case "<=":
                    if(Ikey <= Ivalue){
                        resultSet.add(key);
                    }
                break;
                case ">=":
                    if(Ikey >= Ivalue){
                        resultSet.add(key);
                    }
                break;
            }
        }
        else if(key instanceof Double){
            double Dkey=(Double) key;
            double Dvalue=(Double) value;
            switch(operator){
                case "<":
                    if(Dkey < Dvalue){
                        resultSet.add(key);
                    }
                break;
                case ">":
                    if(Dkey > Dvalue){
                        resultSet.add(key);
                    }
                break;
                case "=":
                    if(Dkey == Dvalue){
                        resultSet.add(key);
                    }
                break;
                case "!=":
                    if(Dkey != Dvalue){
                        resultSet.add(key);
                    }
                break;
                case "<=":
                    if(Dkey <= Dvalue){
                        resultSet.add(key);
                    }
                break;
                case ">=":
                    if(Dkey >= Dvalue){
                        resultSet.add(key);
                    }
                break;
            }
        }
        else{
            String Skey=(String)key;
            String Svalue=(String) value;
            switch(operator){
                case "=":
                    if(Skey.equalsIgnoreCase( Svalue)){
                        resultSet.add(key);
                    }
                break;
                case "!=":
                    if(!Skey.equalsIgnoreCase(Svalue)){
                        resultSet.add(key);
                    }
                break;
            }
        }
    }

    return null;
   }
}
