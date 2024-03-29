package com.jhs.open.filter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 * ���㷨
 * 
 * @author JHS
 * 
 */
public class SharpenFilter extends Filter {
	public double ratio;

	public SharpenFilter() {
	}

	@Override
	protected void dofilter(BufferedImage bufferedImage) {
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
		byte[] data = dataBuffer.getData();

		int w = raster.getWidth();
		int h = raster.getHeight();

		setTotal(data.length);

		for (int i = 0; i < h - 1; i++) {
			for (int j = 0; j < w - 1; j++) {
				int offset = (i * w + j) * 3;
				byte a_b = data[offset];
				byte a_g = data[offset + 1];
				byte a_r = data[offset + 2];

				offset = (i * w + j + 1) * 3;
				byte b_b = data[offset];
				byte b_g = data[offset + 1];
				byte b_r = data[offset + 2];

				offset = ((i + 1) * w + j) * 3;
				byte e_b = data[offset];
				byte e_g = data[offset + 1];
				byte e_r = data[offset + 2];

				offset = ((i + 1) * w + j + 1) * 3;
				byte f_b = data[offset];
				byte f_g = data[offset + 1];
				byte f_r = data[offset + 2];

				byte delta = (byte) (a_r - (b_r + e_r + f_r) / 3);
				byte n_r = (byte) (a_r + delta * ratio);
				n_r = n_r > Byte.MAX_VALUE ? Byte.MAX_VALUE : n_r;
				n_r = n_r < Byte.MIN_VALUE ? Byte.MIN_VALUE : n_r;

				delta = (byte) (a_g - (b_g + e_g + f_g) / 3);
				byte n_g = (byte) (a_g + delta * ratio);
				n_g = n_g > Byte.MAX_VALUE ? Byte.MAX_VALUE : n_g;
				n_g = n_g < Byte.MIN_VALUE ? Byte.MIN_VALUE : n_g;

				delta = (byte) (a_b - (b_b + e_b + f_b) / 3);
				byte n_b = (byte) (a_b + delta * ratio);
				n_b = n_b > Byte.MAX_VALUE ? Byte.MAX_VALUE : n_b;
				n_b = n_b < Byte.MIN_VALUE ? Byte.MIN_VALUE : n_b;

				offset = (i * w + j) * 3;
				dataBuffer.setElem(offset, n_b);
				dataBuffer.setElem(offset + 1, n_g);
				dataBuffer.setElem(offset + 2, n_r);

				setProcess(getProcess() + 1);
			}
		}

		setProcess(getTotal());
	}
}
