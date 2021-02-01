/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author tweij
 */
public class Clock extends Thread{
    int seconds=0;
    int total;
    boolean timeUp=false;
    Clock(int total){
        this.total=total;
    }
 
    
    
    public void run(){
        System.out.println(getName()+": "+"Restaurant started! It will end in "+ total+" seconds.");
        try{
            while(!timeUp){
                seconds++;
                Thread.sleep(1000);
                if(seconds == total){
                    timeUp=true;
                }
            }
        }
        catch(Exception e){}
        
    }
}
