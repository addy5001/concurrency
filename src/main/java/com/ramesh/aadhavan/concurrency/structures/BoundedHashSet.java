package com.ramesh.aadhavan.concurrency.structures;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
    private Set<T> semSet;
    private Semaphore sem;

    public BoundedHashSet(int bound) {
        this.semSet = Collections.synchronizedSet(new HashSet<>());
        this.sem = new Semaphore(bound);
    }
    
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = semSet.add(o);
            return wasAdded;
        }
        finally {
            if(!wasAdded)
                sem.release();
        }
    }
    
    public boolean remove(T o) throws InterruptedException {
        boolean wasRemoved = semSet.remove(o);
        if(wasRemoved)
            sem.release();
        return wasRemoved;
    }
 }
