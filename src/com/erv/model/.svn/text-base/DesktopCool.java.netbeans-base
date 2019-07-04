/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author erwadi
 */
public class DesktopCool extends JDesktopPane {
    public  Image gambar,gambar1,gambar2=null;
    public String namaGambar="cashboxN.png";
    public String namaGambar1="personalN.png";
    public String namaGambar2="jurnalN.png";
    public Rectangle rectA,rectB,rectC=null;
    
    public DesktopCool() {
        super();
        
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        gambar=new ImageIcon(getClass().getResource(namaGambar)).getImage();
        gambar1=new ImageIcon(getClass().getResource(namaGambar1)).getImage();
        gambar2=new ImageIcon(getClass().getResource(namaGambar2)).getImage();
        final Graphics2D g2=(Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0,0, Color.BLUE,0,gambar.getHeight(null), Color.black));
        g2.fillRect(0, 0, getWidth(), getHeight());
        rectA= new Rectangle(getWidth()/2-(gambar.getWidth(null)/2), getHeight()/2-(gambar.getHeight(null)/2), gambar.getWidth(null), gambar.getHeight(null));
        rectB= new Rectangle(getWidth()/2-(gambar.getWidth(null)/2+gambar1.getWidth(null)+40), getHeight()/2-(gambar1.getHeight(null)/2), gambar1.getWidth(null), gambar1.getHeight(null));
        rectC= new Rectangle((getWidth()/2+gambar1.getWidth(null)/2)+40, getHeight()/2-(gambar2.getHeight(null)/2), gambar2.getWidth(null), gambar2.getHeight(null));
        g2.drawImage(gambar, getWidth()/2-(gambar.getWidth(null)/2), getHeight()/2-(gambar.getHeight(null)/2),null);
        g2.drawImage(gambar1, getWidth()/2-(gambar.getWidth(null)/2+gambar1.getWidth(null)+40), getHeight()/2-(gambar1.getHeight(null)/2),null);
        g2.drawImage(gambar2, (getWidth()/2+gambar1.getWidth(null)/2)+40, getHeight()/2-(gambar2.getHeight(null)/2),null);
    }
}
