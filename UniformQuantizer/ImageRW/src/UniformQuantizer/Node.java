/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniformQuantizer;

/**
 *
 * @author Hala Mohamed
 */
public class Node {
    private int begin;
    private int end;
    
    Node(int b , int e ){
        begin=b;
        end=e;
    }
    public void setBegin(int b){
        begin=b;
    }
    public void setEnd(int e){
        end=e;
    }
    public int getBegin(){
        return begin;
    }
    public int getEnd(){
        return end;
    }
    
}
