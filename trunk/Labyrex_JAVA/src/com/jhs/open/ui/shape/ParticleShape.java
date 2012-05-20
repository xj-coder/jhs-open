package com.jhs.open.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;

/**
 * 粒子，用来实现球爆炸效果
 * 
 * @author JHS
 * 
 */
public class ParticleShape extends Canvas {
	public static final double G = 0.0;// 重力加速度，像素/毫秒，影响垂直加速度
	public static final double F = 0.0;// 摩擦力，像素/毫秒，影响垂直加速度和水平加速度

	public double radius = 1.0d;// 半径

	public double x;
	public double y;
	public double xV = 0;// 水平加速度,像素/毫秒
	public double yV = 0;// 垂直加速度,像素/毫秒

	public Color color;// 颜色

	public ParticleShape(double x, double y, double xV, double yV, Color color, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.xV = xV;
		this.yV = yV;
		this.color = color;
		this.radius = radius;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		Graphics2D g2 = (Graphics2D) g;

		GraphicsTools.backupGraphics(g2);
		g2.setColor(color);
		Ellipse2D ellipse = new Ellipse2D.Double(x, y, radius * 2, radius * 2);
		g2.fill(ellipse);
		GraphicsTools.restoreGraphics(g2);
	}

	@Override
	public void update(long l) {
		super.update(l);

		x = x + xV;
		y = y + yV;

		xV = xV - F;// 水平加速度受摩擦力影响
		yV = yV + G - F;// 垂直加速度受重力和摩擦力影响
	}
}
