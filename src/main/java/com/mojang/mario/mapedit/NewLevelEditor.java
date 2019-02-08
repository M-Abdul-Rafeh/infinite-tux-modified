/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mojang.mario.mapedit;

import com.mojang.mario.LevelScene;
import com.mojang.mario.MarioComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;


import com.mojang.mario.level.*;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author pedro
 */
public class NewLevelEditor extends JFrame {

    /**
     * Creates new form NewLevelEditor
     */
    private JCheckBox[] bitmapCheckboxes = new JCheckBox[8];
    private File tilesDir;
    private File levelsDir;
    private String tilesDataFilePath="";
    private String levelsDataFilePath="";
    private String lastJFileChooserPath=System.getProperty("user.home");
    private boolean unsaved = true;
    private boolean newLevel = true;
    //private  NewTilePicker tilePicker;
    //private  NewLevelEditView levelEditView;
   // private JLabel coordinates;
    private String coordinateText="X=P , Y=Q";
    
    public NewLevelEditor()  {
        super("Map Edit");
        initComponents();
        // crete default dirs if they don't exist
        tilesDir = new File(System.getProperty("user.home") + File.separatorChar + "infinitetux_data");
        try {
            levelsDir = new File(tilesDir.getCanonicalPath().toString() + File.separator + "levels");
        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        try {
            tilesDataFilePath = tilesDir.getCanonicalPath().toString() + File.separator + "tiles.dat";

        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

//        if (!levelsDir.exists()) {
//            levelsDir.mkdirs();
//
//        }

        try {
            lastJFileChooserPath = levelsDir.getCanonicalPath().toString();
        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        
        // try and load tiles.dat from default data dir. If not create it and copy tiles.dat over
        try {
            System.out.println("Loading " + tilesDataFilePath);
            Level.loadBehaviors(new DataInputStream(new FileInputStream(tilesDataFilePath)));
        } catch (Exception e) {

            try {
                if (!tilesDir.exists()) {
                    System.out.println("creating " + tilesDir.getName());
                    tilesDir.mkdirs();
                }
                Level.loadBehaviors(new DataInputStream(LevelScene.class.getResourceAsStream("/tiles.dat")));
                System.out.println("creating " + tilesDataFilePath);
                Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                System.out.println("loading " + tilesDataFilePath);
                Level.loadBehaviors(new DataInputStream(new FileInputStream(tilesDataFilePath)));

            } catch (IOException e1) {
                e1.printStackTrace();
                try {

                } catch (Exception e2) {
                    e2.printStackTrace();

                }

            }
            //JOptionPane.showMessageDialog(this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
        }
        
        
        // attempt to load example level
        
          try {
            levelsDataFilePath = levelsDir.getCanonicalPath().toString() + File.separator + "example_level.lvl";
            currentFileNamejLabel.setText(new File(levelsDataFilePath).getName());
            unsaved = false;
        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }     
        
        try {
            System.out.println("Loading " + levelsDataFilePath);
           levelEditView.setLevel(Level.load(new DataInputStream(new FileInputStream(levelsDataFilePath))));
           this.getLevelProperties();
           unsaved = false;
        } catch (Exception e) {

            try {
                if (!levelsDir.exists()) {
                    System.out.println("creating " + levelsDir.getName());
                    levelsDir.mkdirs();
                }
                levelEditView.setLevel(Level.load(new DataInputStream(LevelScene.class.getResourceAsStream("/example_level.lvl"))));
                System.out.println("creating " + levelsDataFilePath);
                levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream(levelsDataFilePath)));
                System.out.println("loading " + levelsDataFilePath);
               levelEditView.setLevel(Level.load(new DataInputStream(new FileInputStream(levelsDataFilePath))));
              currentFileNamejLabel.setText(new File(levelsDataFilePath).getName());
              this.getLevelProperties();
              unsaved = false;

            } catch (IOException e1) {
                e1.printStackTrace();
                try {

                } catch (Exception e2) {
                    e2.printStackTrace();

                }

            }
            //JOptionPane.showMessageDialog(this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
        }        
        
        
        
        
        //**********************************************
 
       
        
        bitmapCheckboxes[0] = this.blockUpperjCheckBox;
        bitmapCheckboxes[1] = this.blockAlljCheckBox;
        bitmapCheckboxes[2] = this.blockLowerjCheckBox;
        bitmapCheckboxes[3] = this.jspecialjCheckBox;
        bitmapCheckboxes[4] = this.bumpablejCheckBox;
        bitmapCheckboxes[5] = this.breakablejCheckBox;
        bitmapCheckboxes[6] = this.pickupablejCheckBox;
        bitmapCheckboxes[7] = this.animatedjCheckBox;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(screenSize);
        setSize(screenSize.width * 8 / 10, screenSize.height * 8 / 10);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        tilePicker = new NewTilePicker();
//        tilePickerPanel.add(tilePicker);
        //tilePickerPanel.add(buildBitmapPanel());
        //tilePickerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Tile picker"));
//        levelEditView = new NewLevelEditView(tilePicker);
//        levelEditViewjScrollPane.add(levelEditView);
        //buildBitmapPanel();
        tilePicker.addTilePickChangedListener(this);
        setContentPane(borderPanel);


    }
    
   

    
    public void setPickedTile(byte pickedTile)
    {
        int bm = Level.TILE_BEHAVIORS[pickedTile&0xff]&0xff;
        
        for (int i=0; i<8; i++)
        {
            bitmapCheckboxes[i].setSelected((bm&(1<<i))>0);
        }
    }
    

    public void setCoordinates(int x , int y)
    {
        coordinateText="X=" + x +" , " +"Y="+y;
        coordinatesjLabel.setText(coordinateText);
    

        //coordinates.repaint();
    
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        borderPanel = new javax.swing.JPanel();
        lowerPanel = new javax.swing.JPanel();
        tilePickerPanel = new javax.swing.JPanel();
        bitmapCheckBoxPanel = new javax.swing.JPanel();
        blockUpperjCheckBox = new javax.swing.JCheckBox();
        blockAlljCheckBox = new javax.swing.JCheckBox();
        blockLowerjCheckBox = new javax.swing.JCheckBox();
        jspecialjCheckBox = new javax.swing.JCheckBox();
        bumpablejCheckBox = new javax.swing.JCheckBox();
        breakablejCheckBox = new javax.swing.JCheckBox();
        pickupablejCheckBox = new javax.swing.JCheckBox();
        animatedjCheckBox = new javax.swing.JCheckBox();
        tilePicker = new com.mojang.mario.mapedit.NewTilePicker();
        levelInfojPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        exitXPositionjTextField = new javax.swing.JFormattedTextField();
        exitYPositionjTextField = new javax.swing.JFormattedTextField();
        levelWidthjTextField = new javax.swing.JFormattedTextField();
        levelHeightjTextField = new javax.swing.JFormattedTextField();
        levelEditViewjScrollPane = new javax.swing.JScrollPane();
        levelEditView = new com.mojang.mario.mapedit.NewLevelEditView(tilePicker);
        jPanel2 = new javax.swing.JPanel();
        currentFileNamejLabel = new javax.swing.JLabel();
        testLeveljButton = new javax.swing.JButton();
        coordinatesjLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        levelEditorjMenu = new javax.swing.JMenu();
        newLeveljMenuItem = new javax.swing.JMenuItem();
        openFilejMenuItem = new javax.swing.JMenuItem();
        SaveFilejMenuItem = new javax.swing.JMenuItem();
        saveAsjMenuItem = new javax.swing.JMenuItem();
        testLeveljMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        borderPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lowerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tilePickerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Tile Picker"));

        bitmapCheckBoxPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        blockUpperjCheckBox.setText("Block Upper");
        blockUpperjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockUpperjCheckBoxActionPerformed(evt);
            }
        });

        blockAlljCheckBox.setText("Block All");
        blockAlljCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockAlljCheckBoxActionPerformed(evt);
            }
        });

        blockLowerjCheckBox.setText("Block Lower");
        blockLowerjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blockLowerjCheckBoxActionPerformed(evt);
            }
        });

        jspecialjCheckBox.setText("Special");
        jspecialjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jspecialjCheckBoxActionPerformed(evt);
            }
        });

        bumpablejCheckBox.setText("Bumpable");
        bumpablejCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bumpablejCheckBoxActionPerformed(evt);
            }
        });

        breakablejCheckBox.setText("Breakable");
        breakablejCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakablejCheckBoxActionPerformed(evt);
            }
        });

        pickupablejCheckBox.setText("Pickupable");
        pickupablejCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickupablejCheckBoxActionPerformed(evt);
            }
        });

        animatedjCheckBox.setText("Animated");
        animatedjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animatedjCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bitmapCheckBoxPanelLayout = new javax.swing.GroupLayout(bitmapCheckBoxPanel);
        bitmapCheckBoxPanel.setLayout(bitmapCheckBoxPanelLayout);
        bitmapCheckBoxPanelLayout.setHorizontalGroup(
            bitmapCheckBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bitmapCheckBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bitmapCheckBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blockUpperjCheckBox)
                    .addComponent(blockAlljCheckBox)
                    .addComponent(blockLowerjCheckBox)
                    .addComponent(jspecialjCheckBox)
                    .addComponent(bumpablejCheckBox)
                    .addComponent(breakablejCheckBox)
                    .addComponent(pickupablejCheckBox)
                    .addComponent(animatedjCheckBox))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        bitmapCheckBoxPanelLayout.setVerticalGroup(
            bitmapCheckBoxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bitmapCheckBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(blockUpperjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blockAlljCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blockLowerjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspecialjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bumpablejCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(breakablejCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pickupablejCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(animatedjCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tilePickerLayout = new javax.swing.GroupLayout(tilePicker);
        tilePicker.setLayout(tilePickerLayout);
        tilePickerLayout.setHorizontalGroup(
            tilePickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        tilePickerLayout.setVerticalGroup(
            tilePickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout tilePickerPanelLayout = new javax.swing.GroupLayout(tilePickerPanel);
        tilePickerPanel.setLayout(tilePickerPanelLayout);
        tilePickerPanelLayout.setHorizontalGroup(
            tilePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tilePickerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tilePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(bitmapCheckBoxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tilePickerPanelLayout.setVerticalGroup(
            tilePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tilePickerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tilePickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tilePickerPanelLayout.createSequentialGroup()
                        .addGap(0, 64, Short.MAX_VALUE)
                        .addComponent(tilePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bitmapCheckBoxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        levelInfojPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Level Info"));

        jLabel3.setText("height");

        jLabel4.setText("Exit x position");

        jLabel2.setText("width");

        jLabel5.setText("Exit y positon");

        exitXPositionjTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        exitXPositionjTextField.setText("10");
        exitXPositionjTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                exitXPositionjTextFieldFocusLost(evt);
            }
        });
        exitXPositionjTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                exitXPositionjTextFieldKeyTyped(evt);
            }
        });

        exitYPositionjTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        exitYPositionjTextField.setText("10");
        exitYPositionjTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                exitYPositionjTextFieldFocusLost(evt);
            }
        });
        exitYPositionjTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                exitYPositionjTextFieldKeyTyped(evt);
            }
        });

        levelWidthjTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        levelWidthjTextField.setText("255");

        levelHeightjTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        levelHeightjTextField.setText("15");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exitYPositionjTextField)
                    .addComponent(exitXPositionjTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(levelWidthjTextField)
                    .addComponent(levelHeightjTextField, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(44, 44, 44))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(levelWidthjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(levelHeightjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(exitXPositionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(exitYPositionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout levelInfojPanelLayout = new javax.swing.GroupLayout(levelInfojPanel);
        levelInfojPanel.setLayout(levelInfojPanelLayout);
        levelInfojPanelLayout.setHorizontalGroup(
            levelInfojPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(levelInfojPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        levelInfojPanelLayout.setVerticalGroup(
            levelInfojPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(levelInfojPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout lowerPanelLayout = new javax.swing.GroupLayout(lowerPanel);
        lowerPanel.setLayout(lowerPanelLayout);
        lowerPanelLayout.setHorizontalGroup(
            lowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tilePickerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(levelInfojPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        lowerPanelLayout.setVerticalGroup(
            lowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowerPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(lowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(levelInfojPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tilePickerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout levelEditViewLayout = new javax.swing.GroupLayout(levelEditView);
        levelEditView.setLayout(levelEditViewLayout);
        levelEditViewLayout.setHorizontalGroup(
            levelEditViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
        );
        levelEditViewLayout.setVerticalGroup(
            levelEditViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );

        levelEditViewjScrollPane.setViewportView(levelEditView);

        currentFileNamejLabel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        currentFileNamejLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentFileNamejLabel.setText("NONE");

        testLeveljButton.setText("Test Level");
        testLeveljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testLeveljButtonActionPerformed(evt);
            }
        });

        coordinatesjLabel.setText("Coordinates");

        jLabel1.setText("Tile Position:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(currentFileNamejLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(testLeveljButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(coordinatesjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(currentFileNamejLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testLeveljButton)
                    .addComponent(coordinatesjLabel)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout borderPanelLayout = new javax.swing.GroupLayout(borderPanel);
        borderPanel.setLayout(borderPanelLayout);
        borderPanelLayout.setHorizontalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(levelEditViewjScrollPane)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        borderPanelLayout.setVerticalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, borderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(levelEditViewjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        levelEditorjMenu.setText("File");

        newLeveljMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newLeveljMenuItem.setText("New Level");
        newLeveljMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLeveljMenuItemActionPerformed(evt);
            }
        });
        levelEditorjMenu.add(newLeveljMenuItem);

        openFilejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFilejMenuItem.setText("Load Level");
        openFilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFilejMenuItemActionPerformed(evt);
            }
        });
        levelEditorjMenu.add(openFilejMenuItem);

        SaveFilejMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveFilejMenuItem.setText("Save Level");
        SaveFilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveFilejMenuItemActionPerformed(evt);
            }
        });
        levelEditorjMenu.add(SaveFilejMenuItem);

        saveAsjMenuItem.setText("Save As...");
        saveAsjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsjMenuItemActionPerformed(evt);
            }
        });
        levelEditorjMenu.add(saveAsjMenuItem);

        testLeveljMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        testLeveljMenuItem.setText("Test Level");
        testLeveljMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testLeveljMenuItemActionPerformed(evt);
            }
        });
        levelEditorjMenu.add(testLeveljMenuItem);

        jMenuBar1.add(levelEditorjMenu);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(borderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(borderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void blockUpperjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockUpperjCheckBoxActionPerformed
        // TODO add your handling code here:o Way
        //System.out.println("No Way");
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<0);
                    if (bitmapCheckboxes[0].isSelected()) bm|=(1<<0);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }        
    }//GEN-LAST:event_blockUpperjCheckBoxActionPerformed

    private void openFilejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFilejMenuItemActionPerformed
      final JFileChooser fileChooser = new JFileChooser(lastJFileChooserPath);
 
     
      File choice;
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      FileNameExtensionFilter levelFilter = new FileNameExtensionFilter("lvl files (*.lvl)", "lvl");
      fileChooser.setFileFilter(levelFilter);
      
     int returnVal = fileChooser.showOpenDialog(null);  
       
     //if the user confirms file selection display a message  
     if (returnVal == JFileChooser.APPROVE_OPTION) { 
         
            if (fileChooser.getSelectedFile() != null) {
                choice = fileChooser.getSelectedFile();
                lastJFileChooserPath=choice.getPath();
                
                try {
                    levelsDataFilePath = choice.getCanonicalFile().toString();
                    currentFileNamejLabel.setText(choice.getAbsoluteFile().getName());

                    levelEditView.setLevel(Level.load(new DataInputStream(new FileInputStream(levelsDataFilePath))));
                    newLevel=false;
                    getLevelProperties();
                } catch (IOException ex) {
                    Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }        
     }  
     
    }//GEN-LAST:event_openFilejMenuItemActionPerformed

    private void blockAlljCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockAlljCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<1);
                    if (bitmapCheckboxes[1].isSelected()) bm|=(1<<1);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_blockAlljCheckBoxActionPerformed

    private void blockLowerjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blockLowerjCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<2);
                    if (bitmapCheckboxes[2].isSelected()) bm|=(1<<2);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_blockLowerjCheckBoxActionPerformed

    private void jspecialjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jspecialjCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<3);
                    if (bitmapCheckboxes[3].isSelected()) bm|=(1<<3);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_jspecialjCheckBoxActionPerformed

    private void bumpablejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bumpablejCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<4);
                    if (bitmapCheckboxes[4].isSelected()) bm|=(1<<4);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_bumpablejCheckBoxActionPerformed

    private void breakablejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakablejCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<5);
                    if (bitmapCheckboxes[5].isSelected()) bm|=(1<<5);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_breakablejCheckBoxActionPerformed

    private void pickupablejCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickupablejCheckBoxActionPerformed
                     int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<6);
                    if (bitmapCheckboxes[6].isSelected()) bm|=(1<<6);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_pickupablejCheckBoxActionPerformed

    private void animatedjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animatedjCheckBoxActionPerformed
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<7);
                    if (bitmapCheckboxes[7].isSelected()) bm|=(1<<7);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
    }//GEN-LAST:event_animatedjCheckBoxActionPerformed

    private void SaveFilejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveFilejMenuItemActionPerformed

        if (newLevel) {
            newLevel = false;
            saveAs("unamed.lvl");
        } else {

            save();
        }
    }//GEN-LAST:event_SaveFilejMenuItemActionPerformed

    private void testLeveljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testLeveljButtonActionPerformed
        //levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream("temp.lvl")));

        String tmp = exitXPositionjTextField.getText();
        if (tmp != null) {
            this.levelEditView.getLevel().xExit = Integer.parseInt(tmp);

        }
        Level currentLevel = levelEditView.getLevel();
        Level level = copyLevel(currentLevel);
        //level.xExit = level.width - 10 ;

        startLevel(level);
    }//GEN-LAST:event_testLeveljButtonActionPerformed

    private void newLeveljMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLeveljMenuItemActionPerformed
        try {
            Level level = new Level(256, 15);
            this.levelEditView.setLevel(level);
            this.levelWidthjTextField.setText(level.width+"");
            this.levelHeightjTextField.setText(level.height+"");
            this.exitXPositionjTextField.setText(level.xExit+"");
             this.exitYPositionjTextField.setText(level.yExit+"");
            

            this.currentFileNamejLabel.setText("unamed.lvl");
            levelsDataFilePath = levelsDir.getCanonicalPath().toString() + File.separator + "unamed.lvl";
            unsaved = true;
            newLevel = true;
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newLeveljMenuItemActionPerformed

    private void saveAsjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsjMenuItemActionPerformed
        saveAs(currentFileNamejLabel.getText().replace("*", ""));
    }//GEN-LAST:event_saveAsjMenuItemActionPerformed

    private void testLeveljMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testLeveljMenuItemActionPerformed
    
               Level currentLevel = levelEditView.getLevel();
               Level level = copyLevel(currentLevel);
               //level.xExit = level.width - 10 ;
               
               startLevel(level);
    }//GEN-LAST:event_testLeveljMenuItemActionPerformed

    private void exitXPositionjTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exitXPositionjTextFieldKeyTyped
        
        this.setFilenamePanelTextEdited();
    }//GEN-LAST:event_exitXPositionjTextFieldKeyTyped

    private void exitYPositionjTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exitYPositionjTextFieldKeyTyped
        
        this.setFilenamePanelTextEdited();
    }//GEN-LAST:event_exitYPositionjTextFieldKeyTyped

    private void exitXPositionjTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exitXPositionjTextFieldFocusLost
        String tmp = exitXPositionjTextField.getText();
        if(tmp != null){
        this.levelEditView.getLevel().xExit = Integer.parseInt(tmp);

       
        }
    }//GEN-LAST:event_exitXPositionjTextFieldFocusLost

    private void exitYPositionjTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_exitYPositionjTextFieldFocusLost
       String tmp = exitYPositionjTextField.getText();
        if(tmp != null){
        this.levelEditView.getLevel().yExit = Integer.parseInt(tmp);

       
        } 
    }//GEN-LAST:event_exitYPositionjTextFieldFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        new NewLevelEditor().setVisible(true);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewLevelEditor().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem SaveFilejMenuItem;
    private javax.swing.JCheckBox animatedjCheckBox;
    private javax.swing.JPanel bitmapCheckBoxPanel;
    private javax.swing.JCheckBox blockAlljCheckBox;
    private javax.swing.JCheckBox blockLowerjCheckBox;
    private javax.swing.JCheckBox blockUpperjCheckBox;
    private javax.swing.JPanel borderPanel;
    private javax.swing.JCheckBox breakablejCheckBox;
    private javax.swing.JCheckBox bumpablejCheckBox;
    private javax.swing.JLabel coordinatesjLabel;
    private javax.swing.JLabel currentFileNamejLabel;
    private javax.swing.JFormattedTextField exitXPositionjTextField;
    private javax.swing.JFormattedTextField exitYPositionjTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox jspecialjCheckBox;
    private com.mojang.mario.mapedit.NewLevelEditView levelEditView;
    private javax.swing.JScrollPane levelEditViewjScrollPane;
    private javax.swing.JMenu levelEditorjMenu;
    private javax.swing.JFormattedTextField levelHeightjTextField;
    private javax.swing.JPanel levelInfojPanel;
    private javax.swing.JFormattedTextField levelWidthjTextField;
    private javax.swing.JPanel lowerPanel;
    private javax.swing.JMenuItem newLeveljMenuItem;
    private javax.swing.JMenuItem openFilejMenuItem;
    private javax.swing.JCheckBox pickupablejCheckBox;
    private javax.swing.JMenuItem saveAsjMenuItem;
    private javax.swing.JButton testLeveljButton;
    private javax.swing.JMenuItem testLeveljMenuItem;
    private com.mojang.mario.mapedit.NewTilePicker tilePicker;
    private javax.swing.JPanel tilePickerPanel;
    // End of variables declaration//GEN-END:variables

 

    public JPanel buildBitmapPanel()
    {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (int i=0; i<8; i++)
        {
            bitmapCheckboxes[i] = new JCheckBox(Level.BIT_DESCRIPTIONS[i]);
            panel.add(bitmapCheckboxes[i]);
            if (Level.BIT_DESCRIPTIONS[i].startsWith("- ")) bitmapCheckboxes[i].setEnabled(false);
            
            final int id = i;
            bitmapCheckboxes[i].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent arg0)
                {
                    int bm = Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff]&0xff;
                    bm&=255-(1<<id);
                    if (bitmapCheckboxes[id].isSelected()) bm|=(1<<id);
                    Level.TILE_BEHAVIORS[tilePicker.pickedTile&0xff] = (byte)bm;

                    try
                    {
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream(tilesDataFilePath)));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
        return panel;
    }
    
    
    public void setFilenamePanelTextEdited() {
        String fName = new File(levelsDataFilePath).getName();
        currentFileNamejLabel.setText("*" + fName );
        unsaved = true;
    }
    
    public void setFilenamePanelTextSaved() {
        String fName = new File(levelsDataFilePath).getName();
        currentFileNamejLabel.setText(fName);
        unsaved = false;
    } 
    private void save() {
        try {
            levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream(levelsDataFilePath)));
            setFilenamePanelTextSaved();
        } catch (IOException ex) {
            Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void saveAs(String levelName){
        File unamedLevel = new File(lastJFileChooserPath + File.separator + levelName);
    
      final JFileChooser fileChooser = new JFileChooser(lastJFileChooserPath);
 
     
      File choice;
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      FileNameExtensionFilter levelFilter = new FileNameExtensionFilter("lvl files (*.lvl)", "lvl");
      fileChooser.setSelectedFile(unamedLevel);
      fileChooser.setFileFilter(levelFilter);
      fileChooser.setApproveButtonText("Save");
      
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            if (fileChooser.getSelectedFile() != null) {
                choice = fileChooser.getSelectedFile();
                lastJFileChooserPath = choice.getPath();

                try {
                    levelsDataFilePath = choice.getCanonicalFile().toString();

                    if (choice.exists()) {

                        if (confirmationDialog(choice.getName()) == true) {
                            save();
                            currentFileNamejLabel.setText(choice.getAbsoluteFile().getName().replace("*", ""));
                        }

                    } else {
                        save();
                        currentFileNamejLabel.setText(choice.getAbsoluteFile().getName().replace("*", ""));
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(NewLevelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    boolean confirmationDialog(String name) {

        int retVal = JOptionPane.showConfirmDialog(this, "Are you sure you want to overwrite " + name + "?");

        if (retVal == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }  // end confirmationDialog
    

    private void startLevel(Level level)
    {
        final MarioComponent mario = new MarioComponent(level,640, 480);
        final JFrame frame = new JFrame();
        frame.setContentPane(mario);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);
        
        frame.setVisible(true);
        
        mario.start();
        
        
frame.addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
         mario.stop();
        
    }
});        
        
//        frame.addKeyListener(mario);
//        frame.addFocusListener(mario);
    }  
    
Level copyLevel(Level l){
                Level currentLevel = l;
               Level level = null;
        try {
            l.save(new DataOutputStream(new FileOutputStream("temp.lvl")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            level = Level.load(new DataInputStream(new FileInputStream("temp.lvl")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
                
                
                
               
      return level;
                

}


void getLevelProperties(){
    Level currentLevel = this.levelEditView.getLevel();
    this.levelWidthjTextField.setText(currentLevel.width+"");
    this.levelHeightjTextField.setText(currentLevel.height+"");
    this.exitXPositionjTextField.setText(currentLevel.xExit+"");
    this.exitYPositionjTextField.setText(currentLevel.yExit+"");
    


}
}
