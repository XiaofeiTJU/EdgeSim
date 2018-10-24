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
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;

import cn.edu.tju.simulation.handler.RequestHandler;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class NumberLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public NumberLineChart() {	
		this.collection = this.getCollection();
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Request",
				"Hit Rate", 
				collection, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false
		);
		
		this.chart.getPlot().setBackgroundPaint(SystemColor.white);
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.RIGHT);
		legend.setHorizontalAlignment(HorizontalAlignment.LEFT);

		

		
		XYPlot plot = (XYPlot) chart.getPlot();		
		
		NumberAxis numberAxisX = (NumberAxis) chart.getXYPlot().getDomainAxis();
		numberAxisX.setTickUnit(new NumberTickUnit(500));
//		numberAxisX.setAutoRangeMinimumSize(0.1);
		numberAxisX.setAutoRangeIncludesZero(true);
		numberAxisX.setAxisLineVisible(false);
		numberAxisX.setTickMarkInsideLength(4f);
		numberAxisX.setTickMarkOutsideLength(0);

		
		
		NumberAxis numberAxisY = (NumberAxis) chart.getXYPlot().getRangeAxis();
		numberAxisY.setTickUnit(new NumberTickUnit(0.2));
		numberAxisY.setRangeWithMargins(0,1);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);
		// 设置Y轴坐标为百分比
		numberAxisY.setNumberFormatOverride(NumberFormat.getPercentInstance());
		
		
		
		XYItemRenderer xyitem = plot.getRenderer();   
        xyitem.setDefaultItemLabelsVisible(true);
//        ItemLabelsVisible(true);

		xyitem.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
//        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
//        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
		xyitem.setDefaultItemLabelFont(new Font("Dialog", 1, 12));
        plot.setRenderer(xyitem);
		
		 	XYLineAndShapeRenderer renderer =  (XYLineAndShapeRenderer)plot.getRenderer();
			renderer.setDefaultItemLabelsVisible(true);
			renderer.setDefaultShapesVisible(true);
			renderer.setDrawOutlines(true);
			
			renderer.setSeriesOutlineStroke(0, new BasicStroke(5F));
			renderer.setSeriesOutlineStroke(1, new BasicStroke(5F));
			renderer.setSeriesOutlineStroke(2, new BasicStroke(5F));
			renderer.setSeriesOutlineStroke(3, new BasicStroke(5F));


			
			renderer.setSeriesPaint(0, Color.RED);
			renderer.setSeriesPaint(1, new Color(53,101,253));
			renderer.setSeriesPaint(2, new Color(0,161,59));//深绿色
			renderer.setSeriesPaint(3, new Color(148,103,189));//紫色
			

			renderer.setSeriesStroke(0, new BasicStroke(4.0F));
			renderer.setSeriesStroke(1, new BasicStroke(4.0F));
			renderer.setSeriesStroke(2, new BasicStroke(4.0F));
			renderer.setSeriesStroke(3, new BasicStroke(4.0F));
			renderer.setSeriesStroke(4, new BasicStroke(2.0F));
			renderer.setSeriesStroke(5, new BasicStroke(2.0F));
		
			this.chartFrame = new ChartFrame("Line Chart", chart);
			chartFrame.pack();
			chartFrame.setSize(1600,1200);
			chartFrame.setLocation(300,200);
			chartFrame.setVisible(true);
	}
	
	public void initial(){		
		this.chartTheme = new StandardChartTheme("CN");
		chartTheme.setLargeFont(new Font("黑体", Font.BOLD, 55));
		chartTheme.setRegularFont(new Font("黑体", Font.BOLD, 45));
		ChartFactory.setChartTheme(chartTheme);
	}
	
	public XYSeriesCollection getCollection() {
		XYSeriesCollection collection = new XYSeriesCollection();
		HashMap<String,LinkedList<cn.edu.tju.simulation.handler.RequestHandler.mydata>> ndMap = RequestHandler.ndMap;

		Iterator<String> it = ndMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			XYSeries series = new XYSeries(key);
//			series.add(0,0);
			LinkedList<cn.edu.tju.simulation.handler.RequestHandler.mydata> mdList  = ndMap.get(key);
			
			for(int i=0 ; i<mdList.size();i++){
				if(i % 30 ==0 && i !=0){
					series.add(i+1,mdList.get(i).hitrate);
				}
			}
			collection.addSeries(series);
		}
		
		@SuppressWarnings("unchecked")
		List <XYSeries>list = collection.getSeries();
		XYSeriesCollection collect = new XYSeriesCollection();

	
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Knaspsack")){
				collect.addSeries(list.get(i));
				break;
			}
		}
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Greedy")){
				collect.addSeries(list.get(i));
				break;
			}
		}for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Lru")){
				collect.addSeries(list.get(i));
				break;
			}
		}for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Lfu")){
				collect.addSeries(list.get(i));
				break;
			}
		}
		
		
		return collect;
	}
}
