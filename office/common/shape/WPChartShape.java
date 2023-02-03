package com.documentreader.docs.pdf.word.document.reader.viewer.office.common.shape;

import com.documentreader.docs.pdf.word.document.reader.viewer.office.thirdpart.achartengine.chart.AbstractChart;

public class WPChartShape extends WPAutoShape
{

	public AbstractChart getAChart() 
	{
		return chart;
	}

	public void setAChart(AbstractChart chart) 
	{
		this.chart = chart;
	}
	
	private AbstractChart chart;
}
