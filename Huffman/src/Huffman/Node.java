/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

/**
 *
 * @author Hala Mohamed
 */
public class Node {
    private String value;
    private int occ;
    private String code;
    private Node left;
    private Node right;
    
    public Node(){}
    public Node(String v, int o , String c){
        value=v;
        occ=o;
        code=c;
    }
    public void setValue(String v){
        value=v;
    }
    public void setOcc(int o){
        occ=o;
    }
    public void setCode(String c){
        code=c;
    }
    public String getValue(){
        return value;
    }
    public int getOcc(){
        return occ;
    }
    public String getCode(){
        return code;
    }
    public void setLeft(Node n){
        left=n;
    }
    public Node getLeft(){
        return left;
    }
    public void setRight(Node r){
        right=r;
    }
    public Node getRight(){
        return right;
    }
    
    
}
