/**
 * @author Nils Persson
 * @date 2019-Jan-31 10:26:45 AM 
 */
package fitbitGrapher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 */
public class SequentialGrapher extends ApplicationFrame{

	public SequentialGrapher(String title) {
		super(title);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		updateDataset(dataset);
		JFreeChart lineChart = ChartFactory.createLineChart("Heart Rate vs Time", "Time","Heart Rate (bpm)", 
				dataset, PlotOrientation.VERTICAL, true, true, false);
		         
		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );
		updateDataset(dataset);
		lineChart.getXYPlot().setDataset(lineChart.getXYPlot().getDataset());
	}
	
	private DefaultCategoryDataset updateDataset(DefaultCategoryDataset dataset){
		// get json information 
		JSONParser parser = new JSONParser();
		JSONArray array = null;
		try {
			array = (JSONArray) parser.parse(new FileReader("heart_rate-2019-01-19.json"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int j = 0;
		for (Object o : array){
			if(j == 1000){break;}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			JSONObject dataPoint = (JSONObject) o;
			
			String dateTime = (String) dataPoint.get("dateTime");
			JSONObject value = (JSONObject) dataPoint.get("value");
			long bpm = (long) value.get("bpm");
			dataset.addValue(bpm, "January 19th", dateTime);
//			dataset.setValue(bpm, "January 19th", dateTime);
			j++;
			
		}
		
		return dataset;
	}

	public static void main(String[] args) {
		// create chart
		SequentialGrapher chart = new SequentialGrapher("Heart Rate vs Time");

		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
		      
	}
	
	

}
