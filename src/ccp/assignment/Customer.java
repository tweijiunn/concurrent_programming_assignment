/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tweij
 */
public class Customer extends Thread{
    String name;
    Waiter owner;
    Waiter waiter;
    Clock clock;
    Seat seat;
    
    int numberofDrink;
    int myNumber;
    boolean entered;
    boolean served=false;
    
    Customer(String name,Waiter owner,Waiter waiter,Seat seat,Clock clock){
        this.name=name;
        this.owner=owner;
        this.clock=clock;
        this.waiter= waiter;
        this.seat = seat;
    }
    
    public String getString(){
        return name;
    }

    
    
    
    
    public void run(){
        
         try {
             Thread.sleep((long)(Math.random() * 1000));
             // customer get their queue number,increase queue number by 1
             synchronized(seat){
                myNumber=seat.queueNumber;
                seat.queueNumber++;
                System.out.println(getName()+": "+name +"  queue number is "+myNumber);
             }
             
         } catch (Exception ex) {}

         while(!entered){
                    if(myNumber==seat.enterNumber && !seat.lastcall ){
                        //enter the restaurant
                        
                        seat.takeSeats(this);
                        break;
                    }
                    if(seat.lastcall){
                        System.out.println(getName()+": "+name +" has left the restaurant.");
                        return;
                    }
            }

            try{
                    // keep waiting to be served
                    while(!served){
                        
                        Thread.sleep(1000);
                        //call order when they are not busy
                        if(!waiter.busy){
                            waiter.findCustomer(this);
                            synchronized(waiter){
                                waiter.notifyAll();
                                waiter.wait(); 
                            }
                        }
                        else if(!owner.busy){
                            owner.findCustomer(this);
                            synchronized(owner){
                                owner.notifyAll();
                                owner.wait();
                            }
                        }
                    }
                //leave the restaurant and decrease seat number
                synchronized(seat){
                    seat.leaveSeats(this);
                }
            }
            catch(Exception e){
            }
        }
    }

