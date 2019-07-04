/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.erv.handler;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TI-PNP
 */
public class Initiater {
    List<LogListener> listeners = new ArrayList<LogListener>();

    public void addListener(LogListener toAdd) {
        listeners.add(toAdd);
    }

    public void sayHello() {
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (LogListener hl : listeners)
            hl.someoneSaidHello();
    }
}
