/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.erv.exception;

/**
 *
 * @author ervan
 */
public class GiroException extends Exception {

    /**
     * Creates a new instance of <code>CabangException</code> without detail
     * message.
     */
    public GiroException() {
    }

    /**
     * Constructs an instance of <code>CabangException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public GiroException(String msg) {
        super(msg);
    }
}
