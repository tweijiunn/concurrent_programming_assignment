/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccp.assignment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author tweij
 */
public class JuiceTap extends Thread{
    Lock lock = new ReentrantLock();
    boolean r;
    boolean available;
    
    JuiceTap(boolean available){
        this.available= available;
    }

    synchronized boolean usingTap(Waiter waiter) {
        r = lock.tryLock();
        
        if (r) {
            System.out.println(waiter.name+ " is using the fountain juice tap. ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            return true;
        }

        return false;
    }

    public void leaveJuiceTap(Waiter waiter) {
        lock.unlock();
        System.out.println(waiter.name + " leaved the fountain juice tap.");
    }

}