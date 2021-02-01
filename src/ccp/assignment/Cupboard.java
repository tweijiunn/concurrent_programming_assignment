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
public class Cupboard {
    Lock lock = new ReentrantLock();
    boolean r;

    synchronized boolean usingCupboard(Waiter waiter) {
        r = lock.tryLock();

        if (r) {
            System.out.println(waiter.name+ " is using the cupboard. ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            return true;
        }

        return false;
    }

    public void leaveCupboard(Waiter waiter) {
        lock.unlock();
        System.out.println(waiter.name + " leaved the cupboard.");
    }
    
}
