package org.process;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import com.book.chapter.four.AbstractBufferedImageOp;

/**
 * NOTE: 有圖形放大, 沒有縮小 ???
 * 
 * @author tonykuo
 *
 */
public class BicubicSharpenFilter extends AbstractBufferedImageOp  {
    
    public final static double B = 0.0;
    public final static double C = 0.5; // constant
	private int destH; // zoom height
	private int destW; // zoom width
	
	public BicubicSharpenFilter()
	{
	    System.out.println("run BicubicSharpenFilter");
	}
	public void setDestHeight(int destH) {
		this.destH = destH;
	}

	public void setDestWidth(int destW) {
		this.destW = destW;
	}
	
	private double CatMullRom( double f )
	{
	    if( f < 0.0 )
	    {
	        f = Math.abs(f);
	    }
	    if( f < 1.0 )
	    {
	        return ( ( 12 - 9 * B - 6 * C ) * ( f * f * f ) +
	            ( -18 + 12 * B + 6 *C ) * ( f * f ) +
	            ( 6 - 2 * B ) ) / 6.0;
	    }
	    else if( f >= 1.0 && f < 2.0 )
	    {
	        return ( ( -B - 6 * C ) * ( f * f * f )
	            + ( 6 * B + 30 * C ) * ( f *f ) +
	            ( - ( 12 * B ) - 48 * C  ) * f +
	            8 * B + 24 * C)/ 6.0;
	    }
	    else
	    {
	        return 0.0;
	    }
	} 

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		int width = src.getWidth();
		int height = src.getHeight();

		if (dest == null)
			dest = createCompatibleDestImage(src, null);

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[destH * destW];
		getRGB(src, 0, 0, width, height, inPixels);
		float rowRatio = ((float) height) / ((float) destH);
		float colRatio = ((float) width) / ((float) destW);
		int index = 0;
		for (int row = 0; row < destH; row++) {
			int ta = 0, tr = 0, tg = 0, tb = 0;
			double srcRow = ((float) row) * rowRatio;
			// 取得整數坐標 row Index
			double j = Math.floor(srcRow);
			// 取得行的小數部份坐標
			double t = srcRow - j;
			for (int col = 0; col < destW; col++) {
				double srcCol = ((float) col) * colRatio;
				// 取得整數部份坐標 column Index
				double k = Math.floor(srcCol);
				// 取得列的小數部份坐標
				double u = srcCol - k;
				double[] rgbData = new double[3];
				double rgbCoffeData = 0.0;
				for(int m=-1; m<3; m++)
				{
					for(int n=-1; n<3; n++)
					{
						int[] rgb = getPixel(j+m, k+n, width, height, inPixels);
						double f1  = CatMullRom( ((double) m ) - t );
						double f2 = CatMullRom ( -(( (double) n ) - u ) );
						// sum of weight
						rgbCoffeData += f2*f1;
						// sum of the RGB values
						rgbData[0] += rgb[0] * f2 * f1;
						rgbData[1] += rgb[1] * f2 * f1;
						rgbData[2] += rgb[2] * f2 * f1;
					}
				}
				ta = 255;
				// get Red/green/blue value for sample pixel
				tr = (int) (rgbData[0]/rgbCoffeData);
				tg = (int) (rgbData[1]/rgbCoffeData);
				tb = (int) (rgbData[2]/rgbCoffeData);
				index = row * destW + col;
				outPixels[index] = (ta << 24) | (clamp(tr) << 16)
						| (clamp(tg) << 8) | clamp(tb);
			}
		}
		setRGB(dest, 0, 0, destW, destH, outPixels);
		return dest;
	}
	
	private int[] getPixel(double j, double k, int width, int height,
			int[] inPixels) {
		int row = (int) j;
		int col = (int) k;
		if (row >= height) {
			row = height - 1;
		}
		if (row < 0) {
			row = 0;
		}
		if (col < 0) {
			col = 0;
		}
		if (col >= width) {
			col = width - 1;
		}
		int index = row * width + col;
		int[] rgb = new int[3];
		rgb[0] = (inPixels[index] >> 16) & 0xff;
		rgb[1] = (inPixels[index] >> 8) & 0xff;
		rgb[2] = inPixels[index] & 0xff;
		return rgb;
	}
	public BufferedImage createCompatibleDestImage(
			BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, 
        		dstCM.createCompatibleWritableRaster(destW, destH), 
        		dstCM.isAlphaPremultiplied(), null);
    }
}
