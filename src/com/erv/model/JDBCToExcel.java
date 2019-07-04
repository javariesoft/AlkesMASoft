/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class JDBCToExcel {
    Connection connection=null;
    Statement statement=null;
    ResultSet resultSet=null;
    String[] columnNames = {};
    List<List<Object>> rows = new ArrayList<List<Object>>();
    ResultSetMetaData metaData;

    public JDBCToExcel(Connection con) {
        connection = con;
    }
    
    public void queryToExcel(String query){
        
    }
    
}
