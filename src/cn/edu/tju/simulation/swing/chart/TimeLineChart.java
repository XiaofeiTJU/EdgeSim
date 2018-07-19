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
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

import cn.edu.tju.simulation.controller.Controller;
import cn.edu.tju.simulation.data.Data;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class TimeLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public TimeLineChart() {	
		this.collection = this.getCollection();
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Time Slice",
				"Hit Rate(%)", 
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
		numberAxisY.setTickUnit(new NumberTickUnit(0.1));
		numberAxisY.setRangeWithMargins(0.05,0.5);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);
		// 设置Y轴坐标为百分比
		numberAxisY.setNumberFormatOverride(NumberFormat.getPercentInstance());

		
		XYItemRenderer xyitem = plot.getRenderer();   
        xyitem.setBaseItemLabelsVisible(true); 
        
		numberAxisX.setTickUnit(new NumberTickUnit(5));
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
//        xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
        xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
        plot.setRenderer(xyitem);
        
        XYLineAndShapeRenderer renderer =  (XYLineAndShapeRenderer)plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseShapesVisible(true);
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
		HashMap<String,LinkedList<Data>> dataMap = Controller.getInstance().getResultDataList();
		Iterator<String> it = Controller.getInstance().getResultDataList().keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			XYSeries series = new XYSeries(key);
			List<Data> dataList = dataMap.get(key);
			for (Data data2 : dataList) {
				series.add(data2.getTimeSlice()+1,data2.getHitRate());
			}
			collection.addSeries(series);
		}
		
		
		@SuppressWarnings("unchecked")
		List <XYSeries> list = collection.getSeries();
		XYSeriesCollection collect = new XYSeriesCollection();

		/**
		 * 
		 */
	
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Knaspsack")){
				System.out.print("背包的平均命中率是:" );
				int size = list.get(i).getItemCount();
				float sum = 0;
				for(int j = 0 ; j <size;j++){
					sum +=list.get(i).getY(j).floatValue();
				}
				System.out.println(sum/size);	
				collect.addSeries(list.get(i));
				break;
			}
		}
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Greedy")){
				System.out.print("贪心的平均命中率是:" );
					int size = list.get(i).getItemCount();
					float sum = 0;
					for(int j = 0 ; j <size;j++){
						sum +=list.get(i).getY(j).floatValue();
					}
					System.out.println(sum/size);	
				collect.addSeries(list.get(i));
				break;
		}
		}
		for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Lru")){
				System.out.print("LRU的平均命中率是:" );
					int size = list.get(i).getItemCount();
					float sum = 0;
					for(int j = 0 ; j <size;j++){
						sum +=list.get(i).getY(j).floatValue();
					}
					System.out.print(sum/size);	
				collect.addSeries(list.get(i));
				break;
			}
		}for(int i = 0 ;i<list.size();i++){
			if(list.get(i).getKey().equals("Lfu")){
				System.out.print("LFU的平均命中率是:" );
					int size = list.get(i).getItemCount();
					float sum = 0;
					for(int j = 0 ; j <size;j++){
						sum +=list.get(i).getY(j).floatValue();
					}
					System.out.println(sum/size);	
				collect.addSeries(list.get(i));
				break;
			}
		}
		
		
		
		
		return collect;
	}
}
