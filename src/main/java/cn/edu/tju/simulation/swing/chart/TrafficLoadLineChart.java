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
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;
import cn.edu.tju.simulation.state.State;
import cn.edu.tju.simulation.state.StateQueue;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class TrafficLoadLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public TrafficLoadLineChart() {	
		this.collection = this.getCollection();
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Time Slice",
				"Traffic Load(100GB)", 
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
		numberAxisX.setAutoRangeIncludesZero(false);
		
		numberAxisX.setAutoRangeMinimumSize(0.1);
		numberAxisX.setAxisLineVisible(false);
		numberAxisX.setTickMarkInsideLength(4f);
		numberAxisX.setTickMarkOutsideLength(0);
		
		
		NumberAxis numberAxisY = (NumberAxis) chart.getXYPlot().getRangeAxis();
		numberAxisY.setTickUnit(new NumberTickUnit(5));
		numberAxisY.setRangeWithMargins(0,40);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);
		numberAxisY.setNumberFormatOverride(NumberFormat.getNumberInstance());

		
		
		XYLineAndShapeRenderer renderer =  (XYLineAndShapeRenderer)plot.getRenderer();
		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultShapesVisible(true);
		renderer.setDrawOutlines(true);
			
		renderer.setSeriesOutlineStroke(0, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(1, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(2, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(3, new BasicStroke(5F));
		renderer.setSeriesOutlineStroke(4, new BasicStroke(5F));



			
		renderer.setSeriesPaint(1, Color.RED);
		renderer.setSeriesPaint(2, new Color(53,101,253));
		renderer.setSeriesPaint(3, new Color(0,161,59));//深绿色
		renderer.setSeriesPaint(4, new Color(148,103,189));//紫色
		renderer.setSeriesPaint(0, new Color(255,125,11));//橘黄色

			

		renderer.setSeriesStroke(0, new BasicStroke(4.0F));
		renderer.setSeriesStroke(1, new BasicStroke(4.0F));
		renderer.setSeriesStroke(2, new BasicStroke(4.0F));
		renderer.setSeriesStroke(3, new BasicStroke(4.0F));
		renderer.setSeriesStroke(4, new BasicStroke(2.0F));
		renderer.setSeriesStroke(5, new BasicStroke(2.0F));




//		renderer.setUseFillPaint(true);
		
		
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
		//计算总流量
		StateQueue sq =  Controller.getInstance().getStateQueue();
		int size = Controller.getInstance().getResultDataList().get("Knaspsack").size();

		XYSeries series1 = new XYSeries("No Cache");
		series1.add(0,0);
		long traffic = 0;
		for(int i = 0 ;i<size;i++){
			Iterator<State> mIt = sq.getStateList(i).iterator();
			while(mIt.hasNext()){
				State state = mIt.next();
				traffic += state.getRequestSingleContent().getSize();
			}
			series1.add(i+1,(float)(traffic)/1024f/1024f/10f);
		}
		System.out.println("No cache 流量"+series1.getY(series1.getItemCount()-1).intValue());

		collection.addSeries(series1);

		
		HashMap<String,LinkedList<Data>> dataMap = Controller.getInstance().getResultDataList();
		Iterator<String> it = Controller.getInstance().getResultDataList().keySet().iterator();
		System.out.println("");
		XYSeries series = null;
		while(it.hasNext()){
			String key = it.next();
			series = new XYSeries(key);
			series.add(0,0);
			List<Data> dataList = dataMap.get(key);
			for (Data data : dataList) {
				int index = data.getTimeSlice()+1;
				long temp = series1.getY(index).longValue();
				float my = temp - (float)(data.getSaveTraffic())/1024f/1024f/10f;
				series.add(data.getTimeSlice()+1,my);
			}
			System.out.println(key+"节省的流量是："+series.getY(series.getItemCount()-1));
			collection.addSeries(series);
		}
//		System.out.println(series1.getY(series1.getItemCount()-1).intValue()+"和"+series.getY(series.getItemCount()-1).intValue()+"和"+size);
//		float n = (series1.getY(series1.getItemCount()-1).floatValue() - series.getY(series.getItemCount()-1).floatValue());
//		System.out.println(n+"哈哈");
		
		
		@SuppressWarnings("unchecked")
		List <XYSeries>list = collection.getSeries();
		XYSeriesCollection collect = new XYSeriesCollection();
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("No Cache")){
				collect.addSeries(list.get(i));
				break;
			}
		}
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
