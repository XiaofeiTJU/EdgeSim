package cn.edu.tju.simulation.swing.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class DelayLineChart extends BaseLineChart{
	private double xais_space = 5;
	private double yais_space; 
	private double yais_min_value = 0;
	private double yais_max_value; 
	
	@Override
	public void draw() {
		XYSeriesCollection collection = new XYSeriesCollection();
		HashMap<String,LinkedList<Data>> dataMap = Controller.getInstance().getResultDataList();
		Iterator<String> it = Controller.getInstance().getResultDataList().keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			XYSeries series = new XYSeries(key);
			List<Data> dataList = dataMap.get(key);
			series.add(0,0);
			for (Data data2 : dataList) {
				if(yais_max_value == 0 || yais_max_value < data2.getLatency()){
					yais_max_value = data2.getLatency();
				}	
				series.add(data2.getTimeSlice()+1,data2.getLatency());
			}
			collection.addSeries(series);
		}
		yais_space = (int)(yais_max_value/6);
		generateLineChart("Time Slice","Latency(s)",new NumberTickUnit(xais_space), new NumberTickUnit(yais_space),yais_min_value, yais_max_value, collection);

	}
}
