package com.seguridadlimite.util;

import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Reduce resolución y guarda como JPEG para ocupar menos disco.
 */
@Component
public class FotoImageOptimizer {

	public byte[] optimizeToJpeg(byte[] imageBytes, int maxEdgePx, float jpegQuality) throws IOException {
		if (imageBytes == null || imageBytes.length == 0) {
			throw new IOException("Imagen vacía");
		}
		float q = (float) Math.max(0.05, Math.min(1.0, jpegQuality));
		int maxEdge = Math.clamp(maxEdgePx, 320, 4096);

		BufferedImage src = ImageIO.read(new ByteArrayInputStream(imageBytes));
		if (src == null) {
			throw new IOException("Formato de imagen no reconocido");
		}

		int w = src.getWidth();
		int h = src.getHeight();
		if (w <= 0 || h <= 0) {
			throw new IOException("Dimensiones de imagen inválidas");
		}

		double scale = 1.0;
		int maxDim = Math.max(w, h);
		if (maxDim > maxEdge) {
			scale = (double) maxDim / maxEdge;
		}
		int newW = Math.max(1, (int) Math.round(w / scale));
		int newH = Math.max(1, (int) Math.round(h / scale));

		BufferedImage rgb = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = rgb.createGraphics();
		try {
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(src, 0, 0, newW, newH, null);
		} finally {
			g.dispose();
		}

		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
		if (!writers.hasNext()) {
			throw new IOException("No hay codificador JPEG disponible");
		}
		ImageWriter writer = writers.next();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
			writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(q);
			}
			writer.write(null, new IIOImage(rgb, null, null), param);
		} finally {
			writer.dispose();
		}
		return baos.toByteArray();
	}
}
