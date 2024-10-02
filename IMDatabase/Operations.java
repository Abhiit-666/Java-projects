package IMDatabase;

import java.io.BufferedReader;
import java.util.*;

public class Operations {

    public static void main(String args[]){
        Storage st=new Storage();
        Scanner sc=new Scanner(System.in);
        System.out.println(">> Write your Query:");
        String query=sc.nextLine();
        
        st.executeQuery(query);
    }
    
}
