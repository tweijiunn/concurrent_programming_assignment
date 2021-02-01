/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

/**
 *
 * @author tweij
 */
public class Seat {
    public int seats;
    public int enterNumber=1;
    public int queueNumber=1;
    public int currentQueue=1;
    int servedCustomer=0;
    boolean lastcall=false;
    boolean closed=false;
    Seat(){
        seats =0;
    }
    
    synchronized public void takeSeats(Customer customer){
        
        if(seats<10&& !customer.entered){
            seats++;
            enterNumber++;
            System.out.println("Customer "+customer.name+" entered the bar.");
            System.out.println("current seat numbers: "+ seats);
            customer.entered=true;
        }
        
        
    }
    
     synchronized public void leaveSeats(Customer customer){
        seats--;
        System.out.println("Customer "+customer.name+" left the bar.");
    }
}
