package com.mojang.mario.mapedit;

import com.mojang.mario.MarioComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

import com.mojang.mario.level.*;
import com.mojang.mario.sprites.Enemy;


public class LevelEditor extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 7461321112832160393L;

    private JButton loadButton;
    private JButton saveButton;
    private JButton testLevelButton;
    private JTextField nameField;
    private LevelEditView levelEditView;
    private TilePicker tilePicker;
    private JLabel coordinates;
    private String coordinateText="X=P , Y=Q";
    
    private JCheckBox[] bitmapCheckboxes = new JCheckBox[8];

    public LevelEditor()
    {
        super("Map Edit");
        
        try
        {
            Level.loadBehaviors(new DataInputStream(new FileInputStream("tiles.dat")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
        }
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width * 8 / 10, screenSize.height * 8 / 10);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tilePicker = new TilePicker();
        JPanel tilePickerPanel = new JPanel(new BorderLayout());
        tilePickerPanel.add(BorderLayout.WEST, tilePicker);
        tilePickerPanel.add(BorderLayout.CENTER, buildBitmapPanel());
        tilePickerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Tile picker"));

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.add(BorderLayout.WEST, tilePickerPanel);
        lowerPanel.setBorder(new TitledBorder(new EtchedBorder(), "lowerPanel"));

        JPanel borderPanel = new JPanel(new BorderLayout());
        levelEditView = new LevelEditView(tilePicker);
        borderPanel.add(BorderLayout.CENTER, new JScrollPane(levelEditView));
        borderPanel.add(BorderLayout.SOUTH, lowerPanel);
        borderPanel.add(BorderLayout.NORTH, buildButtonPanel());
        borderPanel.setBorder(new TitledBorder(new EtchedBorder(), "BorderPanel"));
        setContentPane(borderPanel);

        tilePicker.addTilePickChangedListener(this);
    }

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
                        Level.saveBehaviors(new DataOutputStream(new FileOutputStream("tiles.dat")));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(LevelEditor.this, e.toString(), "Failed to load tile behaviors", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
        return panel;
    }

    public JPanel buildButtonPanel()
    {
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        testLevelButton = new JButton("Test Level");
        nameField = new JTextField("test.lvl", 10);
        coordinates = new JLabel(coordinateText,10);
        loadButton.addActionListener(this);
        saveButton.addActionListener(this);
        testLevelButton.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.add(nameField);
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(testLevelButton);
        panel.add(coordinates);
        return panel;
    }

    public void actionPerformed(ActionEvent e)
    {
        try
        {
            if (e.getSource() == loadButton)
            {
                levelEditView.setLevel(Level.load(new DataInputStream(new FileInputStream(nameField.getText().trim()))));
            }
            if (e.getSource() == saveButton)
            {
                levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream(nameField.getText().trim())));
            }
            if (e.getSource() == testLevelButton)
            {
                //levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream("temp.lvl")));
                Level currentLevel = levelEditView.getLevel();
                Level level = new Level(currentLevel.width,currentLevel.height);
                levelEditView.getLevel().save(new DataOutputStream(new FileOutputStream("temp.lvl")));
                level = Level.load(new DataInputStream(new FileInputStream("temp.lvl")));
//                        level.setSpriteTemplate(22, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(30, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(43, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(44, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(80, 4, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(82, 4, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(98, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(99, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(107, 12, new SpriteTemplate(Enemy.ENEMY_GREEN_KOOPA,false));
//        level.setSpriteTemplate(114, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(115, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(124, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(125, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(127, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(128, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(174, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
//        level.setSpriteTemplate(175, 12, new SpriteTemplate(Enemy.ENEMY_GOOMBA,false));
                
                //Level currentLevel = levelEditView.getLevel();
                //currentLevel.save(dos);
                
                
                
               
                level.xExit=level.width-10;
                
               
               startLevel(level);
            }            
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString(), "Failed to load/save", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void setCoordinates(int x , int y)
    {
        coordinateText="X=" + x +" , " +"Y="+y;
        coordinates.setText(coordinateText);

        //coordinates.repaint();
    
    }
    
    public void startLevel(Level level)
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
    public static void main(String[] args)
    {
        new LevelEditor().setVisible(true);
    }

    public void setPickedTile(byte pickedTile)
    {
        int bm = Level.TILE_BEHAVIORS[pickedTile&0xff]&0xff;
        
        for (int i=0; i<8; i++)
        {
            bitmapCheckboxes[i].setSelected((bm&(1<<i))>0);
        }
    }
}
