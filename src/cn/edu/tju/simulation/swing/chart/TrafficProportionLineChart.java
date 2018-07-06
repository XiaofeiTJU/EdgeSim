package cn.edu.tju.simulation.swing.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class TrafficProportionLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public TrafficProportionLineChart(){
		
	}
	
	public TrafficProportionLineChart(HashMap<String, List<data>> map) {	
		this.collection = this.getCollection(map);
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Proportion",
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
		numberAxisX.setTickUnit(new NumberTickUnit(0.2));
		numberAxisX.setAutoRangeMinimumSize(0.1);
		numberAxisX.setAxisLineVisible(false);
		numberAxisX.setTickMarkInsideLength(4f);
		numberAxisX.setTickMarkOutsideLength(0);
		
		
		NumberAxis numberAxisY = (NumberAxis) chart.getXYPlot().getRangeAxis();
		numberAxisY.setTickUnit(new NumberTickUnit(10));
		numberAxisY.setRangeWithMargins(0,40);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);
		// 设置Y轴坐标为百分比
		
		
		XYItemRenderer xyitem = plot.getRenderer();   
        xyitem.setBaseItemLabelsVisible(true); 
        
        xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
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
	
	public XYSeriesCollection getCollection(HashMap<String, List<data>> map) {
		XYSeriesCollection collection = new XYSeriesCollection();
		
		
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			XYSeries series = new XYSeries(key);
			List<data> dataList = map.get(key);
			for (data data2 : dataList) {
				series.add(data2.getBili(),data2.getHitrate());
			}
			collection.addSeries(series);
		}
		
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
	
	class data{
		private float bili ;
		private float hitrate;
		
		public data(float bili,float hitrate){
			this.bili = bili;
			this.hitrate = hitrate;
		}
		
		public float getBili() {
			return bili;
		}
		public void setBili(float bili) {
			this.bili = bili;
		}
		public float getHitrate() {
			return hitrate;
		}
		public void setHitrate(float hitrate) {
			this.hitrate = hitrate;
		}
		
	}
	
	public static void main(String[] args) {
		HashMap<String, List<data>> map = new HashMap<String, List<data>>(); 
		List<data> list = new ArrayList<TrafficProportionLineChart.data>();
		list.add(new TrafficProportionLineChart().new data(0.05f, 33.9f));
		list.add(new TrafficProportionLineChart().new data(0.1f, 31.488f));
		list.add(new TrafficProportionLineChart().new data(0.15f, 27.22f));
		list.add(new TrafficProportionLineChart().new data(0.2f, 24.68f));
		list.add(new TrafficProportionLineChart().new data(0.25f, 22.62f));
		list.add(new TrafficProportionLineChart().new data(0.3f, 19.31f));
		list.add(new TrafficProportionLineChart().new data(0.35f, 17.46f));
		list.add(new TrafficProportionLineChart().new data(0.4f, 16.07f));
		list.add(new TrafficProportionLineChart().new data(0.45f, 14.94f));
		list.add(new TrafficProportionLineChart().new data(0.5f, 11.87f));
		list.add(new TrafficProportionLineChart().new data(0.55f, 11.7f));
		list.add(new TrafficProportionLineChart().new data(0.6f, 8.95f));


		
		map.put("Knaspsack", list);

		List<data> list2 = new ArrayList<TrafficProportionLineChart.data>();
		list2.add(new TrafficProportionLineChart().new data(0.05f, 32.81f));
		list2.add(new TrafficProportionLineChart().new data(0.1f, 29.39f));
		list2.add(new TrafficProportionLineChart().new data(0.15f, 25.66f));
		list2.add(new TrafficProportionLineChart().new data(0.2f, 23f));
		list2.add(new TrafficProportionLineChart().new data(0.25f, 20.47f));
		list2.add(new TrafficProportionLineChart().new data(0.3f, 16.94f));
		list2.add(new TrafficProportionLineChart().new data(0.35f, 14.55f));
		list2.add(new TrafficProportionLineChart().new data(0.4f, 13.26f));
		list2.add(new TrafficProportionLineChart().new data(0.45f, 11.25f));
		list2.add(new TrafficProportionLineChart().new data(0.5f, 8.94f));
		list2.add(new TrafficProportionLineChart().new data(0.55f, 8.10f));
		list2.add(new TrafficProportionLineChart().new data(0.6f, 5.92f));


		
		map.put("Greedy", list2);


		List<data> list4 = new ArrayList<TrafficProportionLineChart.data>();
		list4.add(new TrafficProportionLineChart().new data(0.05f, 32.7f));
		list4.add(new TrafficProportionLineChart().new data(0.1f, 28.28f));
		list4.add(new TrafficProportionLineChart().new data(0.15f,22.59f));
		list4.add(new TrafficProportionLineChart().new data(0.2f, 21.51f));
		list4.add(new TrafficProportionLineChart().new data(0.25f, 17.78f));
		list4.add(new TrafficProportionLineChart().new data(0.3f, 13.7f));
		list4.add(new TrafficProportionLineChart().new data(0.35f, 12.44f));
		list4.add(new TrafficProportionLineChart().new data(0.4f, 11.1f));
		list4.add(new TrafficProportionLineChart().new data(0.45f, 9.4f));
		list4.add(new TrafficProportionLineChart().new data(0.5f, 6.75f));
		list4.add(new TrafficProportionLineChart().new data(0.55f, 5.98f));
		list4.add(new TrafficProportionLineChart().new data(0.6f, 5.53f));
		
		map.put("Lru", list4);


		List<data> list3 = new ArrayList<TrafficProportionLineChart.data>();
		list3.add(new TrafficProportionLineChart().new data(0.05f, 32.96f));
		list3.add(new TrafficProportionLineChart().new data(0.1f, 28.32f));
		list3.add(new TrafficProportionLineChart().new data(0.15f, 25.8f));
		list3.add(new TrafficProportionLineChart().new data(0.2f, 22.9f));
		list3.add(new TrafficProportionLineChart().new data(0.25f, 20.92f));
		list3.add(new TrafficProportionLineChart().new data(0.3f, 16.4f));
		list3.add(new TrafficProportionLineChart().new data(0.35f, 14.45f));
		list3.add(new TrafficProportionLineChart().new data(0.4f, 13.5f));
		list3.add(new TrafficProportionLineChart().new data(0.45f,11.15f));
		list3.add(new TrafficProportionLineChart().new data(0.5f, 9.1f));
		list3.add(new TrafficProportionLineChart().new data(0.55f, 7.83f));
		list3.add(new TrafficProportionLineChart().new data(0.6f, 5.92f));

		
		map.put("Lfu", list3);
		
		List<data> list5 = new ArrayList<TrafficProportionLineChart.data>();
		for(int i = 0 ; i <12;i++){
			list5.add(new TrafficProportionLineChart().new data((float)((i+1)*5)/100f,38.14f));
		}
		
		map.put("No Cache", list5);
		
		
		

		new TrafficProportionLineChart(map);
		
	}
}
