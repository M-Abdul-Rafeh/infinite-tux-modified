/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mojang.mario.mapedit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;
import com.mojang.mario.*;

/**
 *
 * @author pedro
 */
public class NewTilePicker extends javax.swing.JPanel{
    private static final long serialVersionUID = -7696446733303717142L;

    private int xTile = -1;
    private int yTile = -1;
    
    public byte pickedTile;

    @SuppressWarnings("unused")
	private byte paint = 0;
    private NewLevelEditor tilePickChangedListener;
    /**
     * Creates new form NewTilePicker
     */
    public NewTilePicker() {
        initComponents();
        Dimension size = new Dimension(256, 256);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //addMouseListener(this);
        //addMouseMotionListener(this);   
        setOpaque(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        xTile = -1;
        yTile = -1;
        repaint();
    }//GEN-LAST:event_formMouseExited

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xTile = evt.getX() / 16;
        yTile = evt.getY() / 16;

        setPickedTile((byte) (xTile + yTile * 16));
        repaint();
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        xTile = evt.getX() / 16;
        yTile = evt.getY() / 16;

        repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        xTile = evt.getX() / 16;
        yTile = evt.getY() / 16;
        repaint();
    }//GEN-LAST:event_formMouseMoved

    
    
    @Override
    public void addNotify()
    {
        super.addNotify();
        Art.init(getGraphicsConfiguration(), null);
    }    
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(0x8090ff));
        g.fillRect(0, 0, 256, 256);
        
        for (int x=0; x<16; x++)
            for (int y=0; y<16; y++)
            {
                g.drawImage(Art.level[x][y], (x << 4), (y << 4), null);
            }

        g.setColor(Color.WHITE);
        int xPickedTile = (pickedTile&0xff)%16;
        int yPickedTile = (pickedTile&0xff)/16;
        g.drawRect(xPickedTile * 16, yPickedTile * 16, 15, 15);

        g.setColor(Color.BLACK);
        g.drawRect(xTile * 16 - 1, yTile * 16 - 1, 17, 17);
    }    





 


   
 
    public void setPickedTile(byte block)
    {
        pickedTile = block;
        repaint();
        if (tilePickChangedListener!=null)
            tilePickChangedListener.setPickedTile(pickedTile);
    }

        public void addTilePickChangedListener(NewLevelEditor editor)
    {
        this.tilePickChangedListener = editor;
        if (tilePickChangedListener!=null)
            tilePickChangedListener.setPickedTile(pickedTile);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}