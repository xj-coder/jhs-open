package com.jhs.open.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class SharpenFilter extends Filter {
	public double ratio;

	@Override
	public void filter(final BufferedImage bufferedImage) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				WritableRaster raster = bufferedImage.getRaster();
				DataBufferByte bufferByte = (DataBufferByte) raster.getDataBuffer();

				setTotal(raster.getHeight() * raster.getWidth() * 4);

				for (int i = 0; i < raster.getHeight(); i++) {
					for (int j = 0; j < raster.getWidth(); j++) {

						int offset = (i * raster.getWidth() + j) * 4;

						int a = -1;
						int b = -1;
						int e = -1;
						int f = -1;

						a = new Color(bufferByte.getElem(offset + 1), bufferByte.getElem(offset + 2), bufferByte
								.getElem(offset + 3), bufferByte.getElem(offset)).getRGB();

						if (j + 1 < raster.getWidth()) {
							offset = (i * raster.getWidth() + j + 1) * 4;
							b = new Color(bufferByte.getElem(offset + 1), bufferByte.getElem(offset + 2), bufferByte
									.getElem(offset + 3), bufferByte.getElem(offset)).getRGB();
						}

						if (i + 1 < raster.getHeight()) {
							offset = ((i + 1) * raster.getWidth() + j) * 4;
							e = new Color(bufferByte.getElem(offset + 1), bufferByte.getElem(offset + 2), bufferByte
									.getElem(offset + 3), bufferByte.getElem(offset)).getRGB();
						}

						if (j + 1 < raster.getWidth() && i + 1 < raster.getHeight()) {
							offset = ((i + 1) * raster.getWidth() + j + 1) * 4;
							f = new Color(bufferByte.getElem(offset + 1), bufferByte.getElem(offset + 2), bufferByte
									.getElem(offset + 3), bufferByte.getElem(offset)).getRGB();
						}

						if (a != -1 && b != -1 && e != -1 && f != -1) {
							int delta = a - (b + e + f) / 3;
							a = (int) (a + delta * ratio);

							Color c = new Color(a);
							offset = (i * raster.getWidth() + j) * 4;
							bufferByte.setElem(offset, c.getAlpha());
							bufferByte.setElem(offset + 1, c.getRed());
							bufferByte.setElem(offset + 2, c.getGreen());
							bufferByte.setElem(offset + 3, c.getBlue());
						}

						setProcess(getProcess() + 4);
					}
				}
			}
		}).start();
	}
}
