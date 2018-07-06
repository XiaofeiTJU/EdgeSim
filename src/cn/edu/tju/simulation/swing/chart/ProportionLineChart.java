package cn.edu.tju.simulation.swing.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.text.NumberFormat;
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
public class ProportionLineChart {
	private StandardChartTheme chartTheme;
	private XYSeriesCollection collection;
	private JFreeChart chart;
	private ChartFrame chartFrame;
	
	public ProportionLineChart(){
		
	}
	
	public ProportionLineChart(HashMap<String, List<data>> map) {	
		this.collection = this.getCollection(map);
		initial();
		this.chart = ChartFactory.createXYLineChart(
				null, 
				"Proportion",
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
		numberAxisX.setAutoRangeIncludesZero(false);
		numberAxisX.setTickUnit(new NumberTickUnit(0.2));
		numberAxisX.setAutoRangeMinimumSize(0.1);
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
		List<data> list = new ArrayList<ProportionLineChart.data>();
		list.add(new ProportionLineChart().new data(0.05f, 0.3084f));
		list.add(new ProportionLineChart().new data(0.1f, 0.472f));
		list.add(new ProportionLineChart().new data(0.15f, 0.57f));
		list.add(new ProportionLineChart().new data(0.2f, 0.6348f));
		list.add(new ProportionLineChart().new data(0.25f, 0.6978f));
		list.add(new ProportionLineChart().new data(0.3f, 0.7505f));
		list.add(new ProportionLineChart().new data(0.35f, 0.8038f));
		list.add(new ProportionLineChart().new data(0.4f, 0.8428f));
		list.add(new ProportionLineChart().new data(0.45f, 0.8717f));
		list.add(new ProportionLineChart().new data(0.5f, 0.9025f));
		list.add(new ProportionLineChart().new data(0.55f, 0.9082f));
		list.add(new ProportionLineChart().new data(0.6f, 0.9228f));


		
		map.put("Knaspsack", list);

		List<data> list2 = new ArrayList<ProportionLineChart.data>();
		list2.add(new ProportionLineChart().new data(0.05f, 0.0657f));
		list2.add(new ProportionLineChart().new data(0.1f, 0.1264f));
		list2.add(new ProportionLineChart().new data(0.15f, 0.2031f));
		list2.add(new ProportionLineChart().new data(0.2f, 0.2270f));
		list2.add(new ProportionLineChart().new data(0.25f, 0.3028f));
		list2.add(new ProportionLineChart().new data(0.3f, 0.3662f));
		list2.add(new ProportionLineChart().new data(0.35f, 0.4524f));
		list2.add(new ProportionLineChart().new data(0.4f, 0.4776f));
		list2.add(new ProportionLineChart().new data(0.45f, 0.5714f));
		list2.add(new ProportionLineChart().new data(0.5f, 0.6141f));
		list2.add(new ProportionLineChart().new data(0.55f, 0.6801f));
		list2.add(new ProportionLineChart().new data(0.6f, 0.7183f));


		
		map.put("Greedy", list2);

		List<data> list3 = new ArrayList<ProportionLineChart.data>();
		list3.add(new ProportionLineChart().new data(0.05f, 0.2066f));
		list3.add(new ProportionLineChart().new data(0.1f, 0.2962f));
		list3.add(new ProportionLineChart().new data(0.15f, 0.3388f));
		list3.add(new ProportionLineChart().new data(0.2f, 0.4257f));
		list3.add(new ProportionLineChart().new data(0.25f, 0.4720f));
		list3.add(new ProportionLineChart().new data(0.3f, 0.4874f));
		list3.add(new ProportionLineChart().new data(0.35f, 0.4524f));
		list3.add(new ProportionLineChart().new data(0.4f, 0.5788f));
		list3.add(new ProportionLineChart().new data(0.45f, 0.6388f));
		list3.add(new ProportionLineChart().new data(0.5f, 0.6753f));
		list3.add(new ProportionLineChart().new data(0.55f, 0.6640f));
		list3.add(new ProportionLineChart().new data(0.6f, 0.7160f));

		
		map.put("Lru", list3);

		List<data> list4 = new ArrayList<ProportionLineChart.data>();
		list4.add(new ProportionLineChart().new data(0.05f, 0.0497f));
		list4.add(new ProportionLineChart().new data(0.1f, 0.1123f));
		list4.add(new ProportionLineChart().new data(0.15f, 0.1937f));
		list4.add(new ProportionLineChart().new data(0.2f, 0.2664f));
		list4.add(new ProportionLineChart().new data(0.25f, 0.3502f));
		list4.add(new ProportionLineChart().new data(0.3f, 0.3975f));
		list4.add(new ProportionLineChart().new data(0.35f, 0.4307f));
		list4.add(new ProportionLineChart().new data(0.4f, 0.4973f));
		list4.add(new ProportionLineChart().new data(0.45f, 0.5796f));
		list4.add(new ProportionLineChart().new data(0.5f, 0.6422f));
		list4.add(new ProportionLineChart().new data(0.55f, 0.6778f));
		list4.add(new ProportionLineChart().new data(0.6f, 0.7322f));

		
		map.put("Lfu", list4);
		
		

		new ProportionLineChart(map);
		
	}
}
