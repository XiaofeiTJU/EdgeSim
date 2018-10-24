package cn.edu.tju.simulation.swing.chart;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.text.NumberFormat;

/**
 * The base class of the line chart
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University
 *
 */
public abstract class BaseLineChart {
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public BaseLineChart() {

	}

	protected void generateLineChart(String xAisTitle, String yAisTitle,
									 NumberTickUnit xAisSpace, NumberTickUnit yAisSpace, double yaisMinValue, double yAisMaxValue,
									 XYSeriesCollection collection) {
		this.chart = ChartFactory.createXYLineChart(null, xAisTitle, yAisTitle,
				collection, PlotOrientation.VERTICAL, true, true, false);

		this.chart.getPlot().setBackgroundPaint(SystemColor.white);

		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);

		XYPlot plot = (XYPlot) chart.getPlot();
		NumberAxis numberAxisX = (NumberAxis) chart.getXYPlot().getDomainAxis();
		numberAxisX.setAutoRangeIncludesZero(false);

		numberAxisX.setTickUnit(xAisSpace);
		numberAxisX.setAxisLineVisible(false);
		numberAxisX.setTickMarkInsideLength(4f);
		numberAxisX.setTickMarkOutsideLength(0);

		NumberAxis numberAxisY = (NumberAxis) chart.getXYPlot().getRangeAxis();
		numberAxisY.setTickUnit(yAisSpace);
		numberAxisY.setRangeWithMargins(yaisMinValue, yAisMaxValue);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);
		numberAxisY.setNumberFormatOverride(NumberFormat.getNumberInstance());

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
//		renderer.setBaseItemLabelsVisible(true);
		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultShapesVisible(true);
//		renderer.setBaseShapesVisible(true);
		renderer.setDrawOutlines(true);

		renderer.setSeriesOutlineStroke(0, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(1, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(2, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(3, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(4, new BasicStroke(5F));

		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesPaint(2, new Color(255, 125, 11));
		renderer.setSeriesPaint(3, new Color(0, 161, 59));
		renderer.setSeriesPaint(4, new Color(148, 103, 189));

		renderer.setSeriesStroke(0, new BasicStroke(4.0F));
		renderer.setSeriesStroke(1, new BasicStroke(4.0F));
		renderer.setSeriesStroke(2, new BasicStroke(4.0F));
		renderer.setSeriesStroke(3, new BasicStroke(4.0F));
		renderer.setSeriesStroke(4, new BasicStroke(2.0F));
		renderer.setSeriesStroke(5, new BasicStroke(2.0F));

		// renderer.setUseFillPaint(true);

		this.chartFrame = new ChartFrame("Line Chart", chart);
		chartFrame.pack();
		chartFrame.setSize(800, 600);
		chartFrame.setLocation(300, 200);
		chartFrame.setVisible(true);
	}
	
	public abstract void draw();
}
