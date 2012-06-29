package com.jhs.open.filter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class SmoothFilter extends Filter {
	public double ratio;

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
				int offset = (i * w + j + 1) * 3;
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

				offset = (i * w + j) * 3;

				byte delta = (byte) ((b_b + e_b + f_b) / 3);
				dataBuffer.setElem(offset, (int) (delta * ratio));
				delta = (byte) ((b_g + e_g + f_g) / 3);
				dataBuffer.setElem(offset + 1, (int) (delta * ratio));
				delta = (byte) ((b_r + e_r + f_r) / 3);
				dataBuffer.setElem(offset + 2, (int) (delta * ratio));

				setProcess(getProcess() + 1);
			}
		}

		setProcess(getTotal());
	}
}
