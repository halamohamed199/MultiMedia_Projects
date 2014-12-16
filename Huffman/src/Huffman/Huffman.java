/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Huffman;

import java.util.ArrayList;

/**
 *
 * @author Hala Mohamed
 */
public class Huffman {
    public void compress(String text){
        ArrayList<Integer> occs       = new ArrayList<>();
        ArrayList<Node>    tree       = new ArrayList<>();
        ArrayList<Node>    search     = new ArrayList<>();
        ArrayList<String>  dictionary = new ArrayList<>();
        for(int i=0;i<128;i++){
            occs.add(0);
        }
        for(int i=0;i<text.length();i++){
            int ascii=text.charAt(i);
            occs.set(ascii, occs.get(ascii)+1);
        }
        for(int i=0;i<occs.size();i++){
            if(occs.get(i)!=0){
                Node node=new Node();
                node.setCode("");
                node.setOcc(occs.get(i));
                char c=(char) i;
                String s= String.valueOf(c);
                node.setValue(s);
                tree.add(node);
                search.add(node);
            }
        
        }
        
        while(search.size()!=1){
            Pair min = min(search);
            Node min1=search.get(min.min1);
            Node min2=search.get(min.min2);
            Node newnode = new Node();
            newnode.setValue(min1.getValue()+min2.getValue());
            newnode.setOcc(min1.getOcc()+min2.getOcc());
            newnode.setLeft(min1);
            newnode.setRight(min2);
            tree.add(newnode);
            search.add(newnode);
            search.remove(min1);
            search.remove(min2);
        }
        recursion(search.get(0), "");
        for(int j=0;j<128;j++){
            dictionary.add("null");
        }
        for(int i=0;i<tree.size();i++){
            if(tree.get(i).getValue().length()==1){
                int ascii=tree.get(i).getValue().charAt(0);
                String code=tree.get(i).getCode();
                dictionary.set(ascii, code);
            }
        }
        String str="";
        for(int i=0;i<text.length();i++){
            int a=text.charAt(i);
            str+=dictionary.get(a);
        }
        new IO().writeOutput("output.txt", str);
        String dict = "";
        for(int i = 0 ; i < dictionary.size(); i++){
            dict += i + " " + dictionary.get(i) + "\r\n";
        }
        new IO().writeOutput("dictionary.txt", dict);
        
    }
    public void decompress(){
        String str=new IO().getInput("output.txt");
        String y=new IO().getInput("dictionary.txt");
        String[] dict = y.split("\r\n");   
        String res = "";
        ArrayList<String> dictionary = new ArrayList<>();
        for(int i = 0 ; i < dict.length;i++){
            String[] tmp = dict[i].split(" ");
            String c = tmp[1];
            dictionary.add(c);
        }
        for(int i=0;i<str.length();i++){
            String s = String.valueOf(str.charAt(i));
            while(found(s,dictionary) == -1){
              //  System.out.println(s);
                s += String.valueOf(str.charAt(++i));
            }
            int idx = found(s,dictionary);
            res += String.valueOf((char)idx);
        }
        new IO().writeOutput("input.txt", res);
    }
    private int found(String s,ArrayList<String> d){
        for(int i = 0; i < d.size(); i++){
            if(d.get(i).equals(s)){
                return i;
            }
        }
        return -1;
    }
    private void recursion(Node node, String code){
        if(node == null)return;
        node.setCode(code);
        recursion(node.getLeft(), code + "0");
        recursion(node.getRight(), code + "1");
    }
    
    public Pair min(ArrayList<Node> sea){
        int idxmin1,idxmin2;
        if(sea.get(0).getOcc()<sea.get(1).getOcc()){
            idxmin1=0;
            idxmin2=1;
        }else{
            idxmin2=0;
            idxmin1=1;
        }
        for(int i=2;i<sea.size();i++){
            if(sea.get(i).getOcc()<sea.get(idxmin1).getOcc()){
                idxmin2=idxmin1;
                idxmin1=i;
            }else if(sea.get(i).getOcc()<sea.get(idxmin2).getOcc()){
                idxmin2=i;
            }
                
        }
   
        return new Pair(idxmin1,idxmin2);
    }
    
}

class Pair{
    public int min1;
    public int min2;
    public Pair(int i1,int i2){
        min1=i1;
        min2=i2;
    }    
}


