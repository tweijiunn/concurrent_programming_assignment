/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock; 
/**
 *
 * @author tweij
 */
public class Waiter extends Thread{
    String name;
    boolean c;
    boolean m;
    boolean cof;
    boolean busy;
    boolean juice;
    int accessRight;
    JuiceTap juiceFountainTap;
    Coffee coffee;
    Milk milk;
    Cupboard cupboard;
    Customer customer;
    Seat seat;
    Clock clock;
    
    
    
    Lock re= new ReentrantLock();
    
    Waiter(String name,Seat seat,Cupboard cupboard,JuiceTap juiceFountainTap,Coffee coffee,Milk milk,int accessRight, Clock clock){
        this.name=name;
        this.seat=seat;
        this.cupboard= cupboard;
        this.juiceFountainTap=juiceFountainTap;
        this.accessRight=accessRight;
        this.clock=clock;
        this.coffee=coffee;
        this.milk=milk;
        
    }
    
    public void run(){
        try{
            
            while(true){
                
                if(seat.closed)
                        {

                            break;
                        }
            synchronized(this){
                    if(!seat.lastcall){
                        wait();
                    }
                    
                    serveDrink();
                    busy=false;
                    notifyAll();
                
            }

                        if(accessRight==1){
                            closingTime();
                        }
                        
                        if(seat.closed)
                        {
                            break;
                        }

            }
        }
        catch(Exception e){
            System.out.println(e);
        }       
    }
    
    
    public void serveDrink(){
        try{
            
        if(clock.total-clock.seconds<=10 && !seat.lastcall){
            if(accessRight==1){
                System.out.println(getName()+": LAST CALL BY THE OWNER");
                seat.lastcall=true;
            }
            
        }
            
                if(customer.myNumber==seat.currentQueue){
                    busy= re.tryLock(); 
                    
            }
            if(busy){
                    System.out.println(getName()+": "+name +" is serving customer "+ customer.getString());
                    customer.served=true;
                    seat.currentQueue++;
                Thread.sleep(3000);
                order();
                }
           
                
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void order(){
        try{
            double number = (double) (Math.random());
            
            synchronized(seat){
                seat.servedCustomer++;
            }
            
            
            //check drinks available or not
            if((number>0.5&&!milk.available)||(number>0.5&&!coffee.available)){
                System.out.println(getName()+": "+customer.name+ " ordered 1 cappucino");
                System.out.println("Cappucinno is not available today.");
                number=0.2;
            }
            else if((number<=0.5&&!juiceFountainTap.available)){
                System.out.println(getName()+": "+customer.name+ " ordered 1 fruit juice.");
                System.out.println("Fruit Juice is not available today.");
                number=0.8;
            }
            
            if(number>=0.5){
                System.out.println(getName()+": "+customer.name+ " ordered 1 Cappucino");
                Thread.sleep(3000);
                synchronized(this){
                    c=cupboard.usingCupboard(this);
                    while(!c){
                        System.out.println(getName()+": "+name+ " is waiting for cupboard");
                        Thread.sleep(2000);
                        c= cupboard.usingCupboard(this);
                    }
                    System.out.println(getName()+": "+name +" takes a cup.");

                
                    cof= coffee.usingCoffee(this);
                    m= milk.usingMilk(this);
                    System.out.println(getName()+": "+name +" obtains the coffee and milk.");
                    Thread.sleep(3000);
                    coffee.returnCoffee(this);
                    milk.returnMilk(this);
                    System.out.println(getName()+": "+name+" put back the coffee and milk");
                
                    cupboard.leaveCupboard(this);
                }
                System.out.println(getName()+": "+name +" mixes the cappucino.");
                Thread.sleep(5000);
                System.out.println(getName()+": "+name +" served the Cappucino to "+customer.name);
            }
            else{
                System.out.println(getName()+": "+customer.name+ " ordered 1 Fruit Juice");
                Thread.sleep(2000);
                c=cupboard.usingCupboard(this);
                
                while(!c){
                    System.out.println(getName()+": "+name+ " is waiting for cupboard");
                    Thread.sleep(2000);
                    c= cupboard.usingCupboard(this);
                }
                System.out.println(getName()+": "+name+ " takes a glass.");
                Thread.sleep(2000);
                cupboard.leaveCupboard(this);
                juice= juiceFountainTap.usingTap(this);
                while(!juice){
                    System.out.println(getName()+": "+name+ " is waiting for juice fountain tap");
                    Thread.sleep(2000);
                    juice= juiceFountainTap.usingTap(this);
                }
                System.out.println(getName()+": "+name + " obtain the juice fountain tap.");
                Thread.sleep(2000);
                juiceFountainTap.leaveJuiceTap(this);
                System.out.println(getName()+": "+name+" released the juice fountain tap");
                
                System.out.println(getName()+": "+name+ " fills in the glass with fruit juice");
                Thread.sleep(5000);
                System.out.println(getName()+": "+name +" served the fruit juice to " + customer.name);
                
            }
            customer.served= true; 
            re.unlock();

        }
        catch(Exception e){}
    }
    
    public void closingTime(){
        
        if(seat.seats==0 && seat.lastcall){
            System.out.println(getName()+": "+"The restaurant has closed!");
            System.out.println(getName()+": "+"Total number of customer served: "+(seat.servedCustomer));
            seat.closed=true;
        }
    }
    
    public void findCustomer(Customer customer){
        this.customer=customer;
    }
    
}
