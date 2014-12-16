
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniformQuantizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.BitSet;

/**
 *
 * @author Hala Mohamed
 */
 public class UQuantizer {
    
    public void compress(int [][] img) throws FileNotFoundException, IOException{        
        int numIntervals = 8;
        int step = 32;
        ArrayList <Node> Intervals=new ArrayList<>();
        int preBeg=0;
        String quantizer = "C\tB -> E\tM\n";
        for(int i=0 ; i<numIntervals ; i++){
            Node temp=new Node(preBeg,preBeg+step);
            Intervals.add(temp);
             preBeg+=step;
            quantizer += String.format("%d\t%d -> %d\t%.1f\n", i,temp.getBegin(),
                    temp.getEnd(),(temp.getBegin()+temp.getEnd())/2.0);
        }
        new IO().writeOutput("Intervals.txt", quantizer);
        
        String str="";
        
        
        int[][] codeArray = new int[img.length][img.length];
        for(int i=0 ; i<img.length ; i++){
           for(int j=0 ; j<img.length ; j++){
               int pixel=img[i][j];
               int idx=pixel/step;
               codeArray[i][j] = idx;
               
           } 
        }
         
        BitSet b = new BitSet();
        int idx = 0;
        for(int i = 0 ; i < codeArray.length; i++){
            for(int j = 0 ; j < codeArray.length; j++){
                switch (codeArray[i][j]){
                    case 0:
                        b.set(idx, idx+3 , false);
                        break;
                    case 1:
                        b.set(idx, idx+2 , false);
                        b.set(idx+2 , true);
                        break;
                    case 2:
                        b.set(idx,false);
                        b.set(idx+1,true);
                        b.set(idx+2,false);
                        break;
                    case 3:
                        b.set(idx,false);
                        b.set(idx+1,true);
                        b.set(idx+2,true);
                        break;
                    case 4:
                        b.set(idx,true);
                        b.set(idx+1,false);
                        b.set(idx+2,false);
                        break;
                    case 5:
                        b.set(idx,true);
                        b.set(idx+1,false);
                        b.set(idx+2,true);
                        break;
                    case 6:
                        b.set(idx,true);
                        b.set(idx+1,true);
                        b.set(idx+2,false);
                        break;
                    case 7:
                        b.set(idx,idx+3,true);
                        break;
                }
                idx += 3;
            }
        }
        
        File outFile = new File("bitsOut.txt");
        IO.writeBytesToFile(outFile, b.toByteArray());
        
        
    }
    public void decompress() throws IOException{
        ArrayList <Node> Intervals=new ArrayList<>();
        int preBeg=0;
        int numIntervals = 8;
        int step = 32;
        for(int i=0 ; i<numIntervals; i++){
            Node temp=new Node(preBeg,preBeg+step);
            Intervals.add(temp);
            preBeg+=step;
         }
        
        File outFile = new File("bitsOut.txt");
        byte[] a = IO.readBytesFromFile(outFile);
        
        BitSet tmp = BitSet.valueOf(a);
        
        int newImg[][] = new int[ImageRW.height][ImageRW.width];
        int idx1 = 0 , idx2 = 0;
        
        for(int i =0 ; i < tmp.length(); i+=3){
            String tmpB = "";
            if(tmp.get(i))tmpB += "1";
            else tmpB += "0";
            if(tmp.get(i+1))tmpB += "1";
            else tmpB += "0";
            if(tmp.get(i+2))tmpB += "1";
            else tmpB += "0";
            
            int intervalIdx = Integer.parseInt(tmpB, 2);
            int mid = (Intervals.get(intervalIdx).getBegin() + 
                    Intervals.get(intervalIdx).getEnd() )/2;
            newImg[idx1][idx2++] = mid;
            
            if(idx2 == ImageRW.width){
                idx2 = 0;
                idx1++;
            }
            if(idx1 == ImageRW.height){
                break;
            }
        }
        
        ImageRW.writeImage(newImg, "copy.jpg");
        
    }
    public int MSE(int [][]img,int [][]copy){
        int mse =0;
        for(int i=0 ; i<img.length ; i++){
            for(int j=0 ; j<img.length ; j++){
                int diff=copy[i][j]-img[i][j];
                int error=diff*diff;
                mse+= error;
            }
        }
        return mse/(ImageRW.height*ImageRW.width);
    }
    
}
