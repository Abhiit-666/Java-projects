import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parsertxt{

    private static final Logger logger=Logger.getLogger(Parsertxt.class.getName());
    public static void main(String args[]){
        Parsertxt parser=new Parsertxt();
        String fileName="Log.txt";
        parser.ReadFile(fileName);
    }
    public void ReadFile(String fileName){
        
        try{
            BufferedReader br= new BufferedReader(new FileReader(fileName));
            parseLine(br.readLine());
    
        }catch(IOException e){
            logger.log(Level.SEVERE, "Error reading file : "+e);
        }

    }    

    public void parseLine(String Line){
        
        String parts [] =Line.split(" ",6);
        
        String DTTM=parts[0]+ " " +parts[1];
        String LogLevel=parts[2];
        String func=parts[3];
        String message=parts[5];
        
        switch(LogLevel){
            case "INFO": 
            processInfo(message,func,DTTM);
            break;
            
            case "ERROR": 
            processError(message,func,DTTM);
            break;
            
            case "DEBUG":
            processDebug(message,func,DTTM);
            default:
            break;
        }
    }
        
        
        
        
    Map <String,List<String>> ReqRes= new HashMap<>(); 
    Map <String,Long> ResponseTime= new HashMap<>();
    public void processInfo(String message,String func,String DTTM){

        if(func.equalsIgnoreCase("api_call")){
            String div[]= message.split(" ",3);
            
                String apiP[]=div[2].split(",");
                String api=apiP[0];
                String requestId=apiP[2];
                if(ReqRes.get(api)!=null)
                {
                    calcTime(api,DTTM);
                }else{
                    ReqRes.put(api,Arrays.asList(requestId,DTTM));
                }   

            
        }
    }
    public void calcTime(String api,String DTTM){
        
        String reqTime=ReqRes.get(api).get(1);
        String resTime= DTTM;
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("YYYY-MM-DD HH:MM:SS");
        LocalDateTime reqTimeA=LocalDateTime.parse(reqTime, formatter);
        LocalDateTime resTimeA=LocalDateTime.parse(resTime,formatter);
        Duration responseTime=Duration.between(resTimeA,reqTimeA);
        
        ResponseTime.put(api, responseTime.getSeconds());
        StringBuilder diff=new StringBuilder();
        
    }

    public void processError(String message,String func,String DTTM){
        
    }
    
    public void processDebug(String message,String func,String DTTM){

    }
    
}