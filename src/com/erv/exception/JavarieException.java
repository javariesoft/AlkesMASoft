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
public class JavarieException extends Exception {

    /**
     * Creates a new instance of <code>JavarieException</code> without detail
     * message.
     */
    public JavarieException() {
    }

    /**
     * Constructs an instance of <code>JavarieException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public JavarieException(String msg) {
        super(msg);
    }
}
