package com.book.chapter.three;

import com.book.chapter.eight.StatisticsFilter;
import com.book.chapter.four.BCSAdjustFilter;
import com.book.chapter.seven.BiLineRotateFilter;
import com.book.chapter.seven.BicubicSharpenFilter;
import com.book.chapter.seven.LanczosZoomFilter;
import com.book.chapter.six.HistogramEFilter;
import com.book.chapter.six.HistogramEqualization4Gray;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage sourceImage;
	private BufferedImage destImage;

	public ImagePanel() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
		if (sourceImage != null) {
			g2d.drawImage(sourceImage, 0, 0, sourceImage.getWidth(),
					sourceImage.getHeight(), null);
			if (destImage != null) {
				g2d.drawImage(destImage, sourceImage.getWidth() + 10, 0,
						destImage.getWidth(), destImage.getHeight(), null);
			}
		}
	}

	public void process()
	{
		// chapter 2
	    // SIFTFeatureDetector filter = new SIFTFeatureDetector();
		// destImage = filter.filter(sourceImage, null);

	    // chapter 6
	    // HistogramEFilter filter = new HistogramEFilter();
	    // destImage = filter.filter(sourceImage, null);
	    
	    // chapter 6
	    HistogramEqualization4Gray filter = new HistogramEqualization4Gray();
	    destImage = filter.filter(sourceImage, null);
	    
	    // chapter 7: Lanczos
	    // LanczosZoomFilter filter = new LanczosZoomFilter();
	    // destImage = filter.filter(sourceImage, null);
	    
	    // chapter 7: BicubicSharpenFilter
	    // BicubicSharpenFilter filter = new BicubicSharpenFilter();
	    // filter.setDestHeight(sourceImage.getHeight() * 2);
	    // filter.setDestWidth(sourceImage.getHeight() * 2);
	    // destImage = filter.filter(sourceImage, null);
	    
	    // chapter 7: BiLineRotateFilter
	    // BiLineRotateFilter filter = new BiLineRotateFilter();
	    // filter.setDegree(58);
	    // destImage = filter.filter(sourceImage, null);
	    
	    // chapter 8: StatisticsFilter
	    // StatisticsFilter filter = new StatisticsFilter();
	    // filter.setType(StatisticsFilter.MEADIAN_FILTER);
	    // destImage = filter.filter(sourceImage, null);
	    
		// 开操作 结构元素 5x5矩形
		// 30 x 2
		// int[][] elements = new int[][]{{2,1,2},{0,1,1},{0,0,2}};//new int[3][3];
//		int[][] se = new int[5][5];
//		for(int i=0; i<5; i++)
//		{
//			Arrays.fill(se[i], 0);
//		}
//		filter.setElements(se);
		//****************第十一章集合操作****************//
//		SetOperatorFilter filter = new SetOperatorFilter(SetOperatorFilter.DIFFERENCE);
//		File bImageFile = new File("E:\\image\\B-set.png");
//		try {
//			BufferedImage toImage = ImageIO.read(bImageFile);
//			filter.setbImage(toImage);
//			destImage = filter.filter(sourceImage, null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		YShearFilter yfilter = new YShearFilter();
//		yfilter.setAngle(angle);
//		yfilter.setSrcWidth(sourceImage.getWidth());
//		yfilter.setBackgroundColor(Color.WHITE);
//		destImage = yfilter.filter(destImage, null);
// ********************************** six chapter test codes ****************************/
//		HistogramComparisonFilter heFilter = new HistogramComparisonFilter(sourceImage, 
//				HistogramComparisonFilter.EARTH_MOVERS_DISTANCE);
//		// File destFile = new File("E:\\image\\test11.png");
//		File destFile = new File("E:\\image\\lena.png");
//		try {
//			BufferedImage toImage = ImageIO.read(destFile);
//			double value = heFilter.compareTo(toImage);
//			Graphics2D g2d = toImage.createGraphics();
//			g2d.setPaint(Color.RED);
//			g2d.drawString("" + value, 50, 50);
//			destImage = toImage;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    
	    /* for Ch6 基於直方圖實現圖像二值化
		final HistogramDataExtractor extractor = new HistogramDataExtractor();
		destImage = extractor.filter(sourceImage, null);
		int[] histData = extractor.getHistogram();
		final HistogramPanel uiPanel = new HistogramPanel(destImage, histData);
		RedLineMonitor lineListener = new RedLineMonitor(uiPanel);
		uiPanel.addMouseListener(lineListener);
		uiPanel.addMouseMotionListener(lineListener);
		uiPanel.setupActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				extractor.setThreshold(uiPanel.getThreshold());
				destImage = extractor.filter(sourceImage, null);	
				refresh();
			}
			
		});
		uiPanel.openView();
		*/
		
// ********************************** end comments of sixth chapter ****************************/
		
		// TODO:zhigang save it local storage
//		File outputfile = new File("E:\\image\\saved.png");  
//		try {
//			ImageIO.write(destImage, "png",outputfile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void refresh()
	{
		this.repaint();
	}
	
	public void process(double[] parameters)
	{
		BCSAdjustFilter filter = new BCSAdjustFilter();
		filter.setSaturation(parameters[0]);
		filter.setBrightness(parameters[1]);
		filter.setContrast(parameters[2]);
		destImage = filter.filter(sourceImage, null);
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
