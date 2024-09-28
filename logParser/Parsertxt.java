import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;

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

    Map <String,String> ReqRes= new HashMap<>(); 
    public void parseLine(String Line){

        String parts [] =Line.split(" ",6);

        String DTTM=parts[0]+ " " +parts[1];
        String LogLevel=parts[2];
        String func=parts[3];
        String message=parts[5];

        switch(LogLevel){
            case "INFO": 
                processInfo(message,func);
                break;
            
            case "ERROR": 
                processError(message,func);
                break;
            
            case "DEBUG":
                processDebug(message,func);
            default:
                break;
        }

        
        
        
    }
    public void processInfo(String message,String func){

        if(func.equalsIgnoreCase("api_call")){
            String div[]= message.split(" ",3);
            if(div[0].equalsIgnoreCase("get")){
                
            }
        }
    }

    public void processError(String message,String func){
        
    }
    
    public void processDebug(String message,String func){

    }
    
}