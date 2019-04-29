package org.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author tonykuo
 *
 */
public class MainUI extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String DEFAULT_FILE_PATH = "/Users/tonykuo/Documents/workspace-sts/image-process/img";
    
    public static final String IMAGE_CMD = "選擇圖片";
    public static final String PROCESS_CMD = "Histogram Equalization";
    public static final String PROCESS_CMD_2 = "Median filtering";
    public static final String PROCESS_CMD_3 = "縮小";
    public static final String PROCESS_CMD_4 = "放大";
    public static final String PROCESS_CMD_5 = "旋轉";

    private JButton imgBtn;
    
    private JButton processBtn;
    private JButton processBtn2;
    private JButton processBtn3;
    private JButton processBtn4;
    private JButton processBtn5;
    
    private ImagePanel2 imagePanel2;

    // image
    private BufferedImage srcImage;

    public MainUI() {
        
        setTitle("影像處理 - Demo");
        imgBtn = new JButton(IMAGE_CMD);
        
        processBtn = new JButton(PROCESS_CMD);
        processBtn2 = new JButton(PROCESS_CMD_2);
        processBtn3 = new JButton(PROCESS_CMD_3);
        processBtn4 = new JButton(PROCESS_CMD_4);
        processBtn5 = new JButton(PROCESS_CMD_5);

        // buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(imgBtn);
        //
        btnPanel.add(processBtn);
        btnPanel.add(processBtn2);
        btnPanel.add(processBtn3);
        btnPanel.add(processBtn4);
        btnPanel.add(processBtn5);

        // filters
        imagePanel2 = new ImagePanel2();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(imagePanel2, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        // setup listener
        setupActionListener();

    }

    private void setupActionListener() {
        imgBtn.addActionListener(this);
        processBtn.addActionListener(this);
        processBtn2.addActionListener(this);
        processBtn3.addActionListener(this);
        processBtn4.addActionListener(this);
        processBtn5.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (SwingUtilities.isEventDispatchThread()) {
            System.out.println("Event Dispath Thread!!");
        }

        if (srcImage == null) {
            JOptionPane.showMessageDialog(this, "請選擇圖片檔案");
            try {
                JFileChooser chooser = new JFileChooser(DEFAULT_FILE_PATH);
                setFileTypeFilter(chooser);
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                if (f != null) {
                    srcImage = ImageIO.read(f);
                    imagePanel2.setSourceImage(srcImage);
                    imagePanel2.repaint();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }
        
        if (IMAGE_CMD.equals(e.getActionCommand())) {
            
            try {
                JFileChooser chooser = new JFileChooser(DEFAULT_FILE_PATH);
                setFileTypeFilter(chooser);
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                if (f != null) {
                    srcImage = ImageIO.read(f);
                    imagePanel2.setSourceImage(srcImage);
                    imagePanel2.repaint();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            imagePanel2.repaint();
            
        } else if (PROCESS_CMD.equals(e.getActionCommand())) {
            System.out.println("do something");
            imagePanel2.process(6);
            imagePanel2.repaint();
        } else if (PROCESS_CMD_2.equals(e.getActionCommand())) {
            System.out.println("do something 2");
            imagePanel2.process(8);
            imagePanel2.repaint();
        } else if (PROCESS_CMD_3.equals(e.getActionCommand())) {
            System.out.println("do something 3");
            imagePanel2.process(71);
            imagePanel2.repaint();
        } else if (PROCESS_CMD_4.equals(e.getActionCommand())) {
            System.out.println("do something 4");
            imagePanel2.process(72);
            imagePanel2.repaint();
        } else if (PROCESS_CMD_5.equals(e.getActionCommand())) {
            System.out.println("do something 5");
            imagePanel2.process(73);
            imagePanel2.repaint();
        } else {
            System.out.println("nothing to do");
        }
    }

    public void setFileTypeFilter(JFileChooser chooser) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg",
                "png");
        chooser.setFileFilter(filter);
    }

    public void openView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        MainUI ui = new MainUI();
        ui.openView();
    }

}
