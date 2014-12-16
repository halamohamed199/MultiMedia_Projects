/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mulimedia;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Hala Mohamed
 */
public class LZW {
  private Vector<String> dictionary = new Vector<String>();
    public void ascii(){
        for(int i = 0 ; i < 128 ; i++){
            dictionary.add(String.valueOf((char)i));    
        }
    }
    /**
     *
     * @param s
     * @return
     */
    public int search(String s){
        for (int i=0 ; i<dictionary.size() ; i++){
            if ( dictionary.get(i).equals(s)){
                return i;
            }         
        }
        return -1;
    }
            
    
    static void Main(String []args){ 
        Vector <Integer> index = new Vector<Integer>();
        Scanner input=new Scanner(System.in);
            String str=input.nextLine();
            String string="";
            LZW obj = new LZW();
            obj.ascii();
            for (int i=0 ; i < str.length() ; i++){
                string+=String.valueOf(str.charAt(i));
                
                    for (int k=i+1 ; k<str.length() ; k++){
                        int temp=obj.search(string);
                        string+=str.charAt(k);
                        if(obj.search(string)==-1){
                            index.add(temp);
                            obj.dictionary.add(string);
                            string="";
                            i=k-1;   
                        }     
                   }
            } 
            for(int i=0;i<index.size();i++){
                System.out.print(index.get(i));
            }
    }
}