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
public class DelayProportionLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public DelayProportionLineChart(){
		
	}
	
	public DelayProportionLineChart(HashMap<String, List<data>> map) {	
		this.collection = this.getCollection(map);
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Proportion",
				"Delay(Million)", 
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
		numberAxisY.setTickUnit(new NumberTickUnit(20));
		numberAxisY.setRangeWithMargins(10,70);
		numberAxisY.setAutoRangeIncludesZero(true);
		numberAxisY.setAxisLineVisible(false);
		numberAxisY.setTickMarkInsideLength(4f);
		numberAxisY.setTickMarkOutsideLength(0);

		
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
		List<data> list = new ArrayList<DelayProportionLineChart.data>();
		list.add(new DelayProportionLineChart().new data(0.05f, 66f));
		list.add(new DelayProportionLineChart().new data(0.1f, 48f));
		list.add(new DelayProportionLineChart().new data(0.15f, 38f));
		list.add(new DelayProportionLineChart().new data(0.2f, 32f));
		list.add(new DelayProportionLineChart().new data(0.25f, 27f));
		list.add(new DelayProportionLineChart().new data(0.3f, 23f));
		list.add(new DelayProportionLineChart().new data(0.35f, 20f));
		list.add(new DelayProportionLineChart().new data(0.4f, 18f));
		list.add(new DelayProportionLineChart().new data(0.45f, 16f));
		list.add(new DelayProportionLineChart().new data(0.5f, 14f));
		list.add(new DelayProportionLineChart().new data(0.55f, 14f));
		list.add(new DelayProportionLineChart().new data(0.6f, 13f));


		
		map.put("Knaspsack", list);


		List<data> list3 = new ArrayList<DelayProportionLineChart.data>();
		list3.add(new DelayProportionLineChart().new data(0.05f, 104f));
		list3.add(new DelayProportionLineChart().new data(0.1f, 93f));
		list3.add(new DelayProportionLineChart().new data(0.15f, 80f));
		list3.add(new DelayProportionLineChart().new data(0.2f, 76f));
		list3.add(new DelayProportionLineChart().new data(0.25f, 66f));
		list3.add(new DelayProportionLineChart().new data(0.3f, 58f));
		list3.add(new DelayProportionLineChart().new data(0.35f, 50f));
		list3.add(new DelayProportionLineChart().new data(0.4f, 47f));
		list3.add(new DelayProportionLineChart().new data(0.45f, 39f));
		list3.add(new DelayProportionLineChart().new data(0.5f, 34f));
		list3.add(new DelayProportionLineChart().new data(0.55f,30f));
		list3.add(new DelayProportionLineChart().new data(0.6f, 27f));
		
		

		
		map.put("Greedy", list3);

		List<data> list2 = new ArrayList<DelayProportionLineChart.data>();
		list2.add(new DelayProportionLineChart().new data(0.05f, 81f));
		list2.add(new DelayProportionLineChart().new data(0.1f, 67f));
		list2.add(new DelayProportionLineChart().new data(0.15f, 60f));
		list2.add(new DelayProportionLineChart().new data(0.2f, 48f));
		list2.add(new DelayProportionLineChart().new data(0.25f, 44f));
		list2.add(new DelayProportionLineChart().new data(0.3f, 41f));
		list2.add(new DelayProportionLineChart().new data(0.35f, 35f));
		list2.add(new DelayProportionLineChart().new data(0.4f, 33f));
		list2.add(new DelayProportionLineChart().new data(0.45f, 29f));
		list2.add(new DelayProportionLineChart().new data(0.5f, 25f));
		list2.add(new DelayProportionLineChart().new data(0.55f, 27f));
		list2.add(new DelayProportionLineChart().new data(0.6f, 24f));

		
		map.put("Lru", list2);

		List<data> list4 = new ArrayList<DelayProportionLineChart.data>();
		list4.add(new DelayProportionLineChart().new data(0.05f, 111f));
		list4.add(new DelayProportionLineChart().new data(0.1f, 100f));
		list4.add(new DelayProportionLineChart().new data(0.15f, 85f));
		list4.add(new DelayProportionLineChart().new data(0.2f, 74f));
		list4.add(new DelayProportionLineChart().new data(0.25f, 63f));
		list4.add(new DelayProportionLineChart().new data(0.3f,57f));
		list4.add(new DelayProportionLineChart().new data(0.35f, 54f));
		list4.add(new DelayProportionLineChart().new data(0.4f, 47f));
		list4.add(new DelayProportionLineChart().new data(0.45f, 39f));
		list4.add(new DelayProportionLineChart().new data(0.5f, 33f));
		list4.add(new DelayProportionLineChart().new data(0.55f, 31f));
		list4.add(new DelayProportionLineChart().new data(0.6f, 27f));

		
		map.put("Lfu", list4);
		
		List<data> list5 = new ArrayList<DelayProportionLineChart.data>();
		for(int i = 0 ; i <12;i++){
			list5.add(new DelayProportionLineChart().new data((float)((i+1)*5)/100f, 117f));
		}
		
		map.put("No Cache", list5);
		
		

		new DelayProportionLineChart(map);
		
	}
}
