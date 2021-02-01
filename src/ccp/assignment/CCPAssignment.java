/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author tweij
 */
public class CCPAssignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Clock clock = new Clock(30);
        Seat s= new Seat();
        Cupboard c= new Cupboard();
        
        //juice tap set to false
        JuiceTap juice = new JuiceTap(true);
        Milk milk= new Milk(true);
        Coffee coffee= new Coffee(true);
        
        
        Waiter w1= new Waiter("Owner",s,c,juice,coffee,milk,1,clock);
        Waiter w2= new Waiter("Waiter",s,c,juice,coffee,milk,0,clock);
        Customer [] customer = new Customer [100];
        clock.start();
        w1.start();
        w2.start();
        for(int i=0;!clock.timeUp;i++){
            
            customer[i]= new Customer("c"+(Integer.toString(i+1)),w1,w2,s,clock);
            customer[i].start();
            try{
                Thread.sleep(5000);
            }catch(Exception e){}
        }

    }
    
}
