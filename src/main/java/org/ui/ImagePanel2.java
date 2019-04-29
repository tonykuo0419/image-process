package org.ui;

import org.process.StatisticsFilter;
import org.process.BiLineRotateFilter;
import org.process.BicubicSharpenFilter;
import org.process.LanczosZoomFilter;
import org.process.HistogramEqualization4Gray;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel2 extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private BufferedImage sourceImage;
    private BufferedImage destImage;

    public ImagePanel2() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
        if (sourceImage != null) {
            g2d.drawImage(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
            if (destImage != null) {
                g2d.drawImage(destImage, sourceImage.getWidth() + 10, 0, destImage.getWidth(),
                        destImage.getHeight(), null);
            }
        }
    }
    
    /**
     * 
     * @param type
     */
    public void process(int type) {

        switch (type) {
            case 6:
                HistogramEqualization4Gray filter6 = new HistogramEqualization4Gray();
                destImage = filter6.filter(sourceImage, null);
                break;
            case 71:
                LanczosZoomFilter filter71 = new LanczosZoomFilter();
                destImage = filter71.filter(sourceImage, null);
                break;
            case 72:
                BicubicSharpenFilter filter72 = new BicubicSharpenFilter();
                filter72.setDestHeight(sourceImage.getHeight() * 2);
                filter72.setDestWidth(sourceImage.getHeight() * 2);
                destImage = filter72.filter(sourceImage, null);
                break;
            case 73:
                BiLineRotateFilter filter73 = new BiLineRotateFilter();
                filter73.setDegree(58);
                destImage = filter73.filter(sourceImage, null);
                break;
            case 8:
                StatisticsFilter filter8 = new StatisticsFilter();
                filter8.setType(StatisticsFilter.MEADIAN_FILTER);
                destImage = filter8.filter(sourceImage, null);
                break;
            default:
                System.out.println("do nothing");
                break;
        }

        // chapter 6
        // HistogramEFilter filter = new HistogramEFilter();
        // destImage = filter.filter(sourceImage, null);

    }

    public void refresh() {
        this.repaint();
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(BufferedImage sourceImage) {
        this.sourceImage = sourceImage;
    }

    public BufferedImage getDestImage() {
        return destImage;
    }

    public void setDestImage(BufferedImage destImage) {
        this.destImage = destImage;
    }

}
