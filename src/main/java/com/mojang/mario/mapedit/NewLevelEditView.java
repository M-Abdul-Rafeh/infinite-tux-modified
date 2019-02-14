/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mojang.mario.mapedit;

import com.mojang.mario.Art;
import com.mojang.mario.LevelRenderer;
import com.mojang.mario.level.Level;
import com.mojang.mario.level.SpriteTemplate;
import com.mojang.mario.sprites.Enemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author pedro
 */
public class NewLevelEditView extends javax.swing.JPanel{
    private static final long serialVersionUID = -7696446733303717142L;

    private LevelRenderer levelRenderer;
    private Level level;

    private int xTile = -1;
    private int yTile = -1;
    private NewTilePicker tilePicker;
    private EnemyTilePicker enemyTilePicker;
    public int tileFrom;
    /**
     * Creates new form NewLevelEditView
     */
    public NewLevelEditView() {
        initComponents();
        level = new Level(640, 15);
        Dimension size = new Dimension(level.width * 16, level.height * 16);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);        
        setOpaque(true);
    }
    
    public NewLevelEditView(NewTilePicker tilePicker)
    {
        initComponents();
        this.tilePicker = tilePicker;
        level = new Level(640, 15);
        Dimension size = new Dimension(level.width * 16, level.height * 16);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //addMouseListener(this);
        //addMouseMotionListener(this);
        //setOpaque(true);
    } 
    public NewLevelEditView(NewTilePicker tilePicker, EnemyTilePicker enemyTilePicker)
    {
        initComponents();
        this.tilePicker = tilePicker;
        this.enemyTilePicker = enemyTilePicker;
        level = new Level(640,15);
        Dimension size = new Dimension(level.width * 16, level.height * 16);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //addMouseListener(this);
        //addMouseMotionListener(this);
        //setOpaque(true);
    }    
    
    public NewLevelEditView(NewTilePicker tilePicker, int levelLength, int levelWidth)
    {
        initComponents();
        this.tilePicker = tilePicker;
        level = new Level(640, 15);
        Dimension size = new Dimension(level.width * 16, level.height * 16);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        //addMouseListener(this);
        //addMouseMotionListener(this);
        //setOpaque(true);
    }   
    
    public void setLevel(Level level)
    {
        this.level = level;
        Dimension size = new Dimension(level.width * 16, level.height * 16);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        repaint();
        levelRenderer.setLevel(level);
        this.addNotify();
    }
    public Level getLevel()
    {
        return level;
    }

    public void addNotify()
    {
        super.addNotify();
        Art.init(getGraphicsConfiguration(), null);
        levelRenderer = new LevelRenderer(level, getGraphicsConfiguration(), level.width * 16, level.height * 16);
        levelRenderer.renderBehaviors = true;
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(0x8090ff));
        g.fillRect(0, 0, level.width * 16, level.height * 16);
        levelRenderer.render(g, 0, 0);
        g.setColor(Color.BLACK);
        // make sure that stuff is only drawn inthe loaded image
        if (xTile <= level.width - 1 && yTile <= level.height) {

            if (tilePicker != null && this.tileFrom == 0) {
                {
                    int x = (tilePicker.pickedTile & 0xff) % 16;
                    int y = (tilePicker.pickedTile & 0xff) / 16;

                    Image img = Art.level[x][y];
                    g.drawImage(img, xTile * 16 - 1, yTile * 16 - 1, 16, 16, null);
                    g.drawRect(xTile * 16 - 1, yTile * 16 - 1, 17, 17);

                }
            }
            if (this.tileFrom == 1) {
                {
                    if (enemyTilePicker.pickedEnemy > -1 && enemyTilePicker.status >-1 && enemyTilePicker.pickedEnemy < 7 && enemyTilePicker.status < 3)
                    {
                    Image img = Art.enemypicker[enemyTilePicker.status][this.getEnemyMap(enemyTilePicker.pickedEnemy)];
                    //Image img = Art.enemies[enemyTilePicker.status][enemyTilePicker.pickedEnemy];
                    g.drawImage(img, xTile * 16 - 1, yTile * 16 - 16, 16, 32, null);
                }
                    g.drawRect(xTile * 16 - 1, yTile * 16 - 16, 17, 33);

                }
            }            
            
            
        }
        
        
        
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
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        switch (tileFrom) 
        {
            case 0:
                xTile = evt.getX() / 16;
                yTile = evt.getY() / 16;

                level.setBlock(xTile, yTile, tilePicker.pickedTile);
                levelRenderer.repaint(xTile - 1, yTile - 1, 3, 3);
                break;
            case 1:
                xTile = evt.getX() / 16;
                yTile = evt.getY() / 16;

                level.setSpriteTemplate(xTile, yTile,new SpriteTemplate(enemyTilePicker.pickedEnemy,false));
                
                
                    boolean winged = false;
                    boolean clear = false;
                    int status = enemyTilePicker.status;
                    switch (status) {
                        case 0:
                            winged = false;
                            break;
                        case 1:
                            winged = true;
                            break;
                        case 2:
                            winged = false;
                            clear = true;
                            break;
                    }
                    if (!clear) {
                        level.setSpriteTemplate(xTile, yTile, new SpriteTemplate(enemyTilePicker.pickedEnemy, winged));
                    } else {
                        level.setSpriteTemplate(xTile, yTile, null);

                    }
                    levelRenderer.repaint(xTile - 1, yTile - 1, 3, 3);

                    repaint();                
                
                
                levelRenderer.repaint(xTile - 1, yTile - 1, 3, 3);                
                break;

        }
        repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        xTile = -1;
        yTile = -1;
        repaint();
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xTile = evt.getX() / 16;
        yTile = evt.getY() / 16;
        switch (tileFrom) {
            case 0:

                if (evt.getButton() == 3) {
                    tilePicker.setPickedTile(level.getBlock(xTile, yTile));
                } else {
                    level.setBlock(xTile, yTile, tilePicker.pickedTile);
                    levelRenderer.repaint(xTile - 1, yTile - 1, 3, 3);

                    repaint();
                }
                break;
            case 1:
                SpriteTemplate st = level.getSpriteTemplate(xTile, yTile);
                boolean clear = false;

                boolean winged = false;

                int status = enemyTilePicker.status;
                switch (status) {
                    case 0:
                        winged = false;
                        break;
                    case 1:
                        winged = true;
                        break;
                    case 2:
                        winged = false;
                        clear = true;
                        break;
                }
                if (evt.getButton() == 3) {
                    //enemyTilePicker.setPickedTile(level.getBlock(xTile, yTile));
                    // SpriteTemplate st = level.getSpriteTemplate(xTile, yTile);

                    if (st != null) {
                        if (!clear) {
                            enemyTilePicker.setPickedEnemy(status, st.type);
                        }

                    } else {
                        level.setSpriteTemplate(xTile, yTile, null);
                        //enemyTilePicker.setPickedEnemy(status, st.type);
                    }


                    
                } else {

                    if (!clear) {
                        level.setSpriteTemplate(xTile, yTile, new SpriteTemplate(enemyTilePicker.pickedEnemy, winged));
                    } else {
                        level.setSpriteTemplate(xTile, yTile, null);

                    }
                    levelRenderer.repaint(xTile - 1, yTile - 1, 3, 3);

                    repaint();
                }
                break;
        }

        tilePicker.getNewLevelEditor().setFilenamePanelTextEdited();
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseReleased

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved

        xTile = evt.getX() / 16;
        yTile = evt.getY() / 16;
        ((NewLevelEditor) this.getRootPane().getParent()).setCoordinates(xTile, yTile);

        repaint();
    }//GEN-LAST:event_formMouseMoved

 

    





   



    
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

   private  int getEnemyMap(int enemyType) {
        int yPos = -1;
        switch (enemyType) {
            case Enemy.ENEMY_RED_KOOPA:
                yPos = 0;
                break;

            case Enemy.ENEMY_GREEN_KOOPA:
                yPos = 1;
                break;

            case Enemy.ENEMY_GOOMBA:
                yPos = 2;
                break;
            case Enemy.ENEMY_SPIKY:
                yPos = 3;
                break;
            case Enemy.ENEMY_FLOWER:
                yPos = 6;
                break;

        }

        return yPos;
    }
}
