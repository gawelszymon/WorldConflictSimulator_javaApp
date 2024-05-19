package project.simulation.wsc.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import project.simulation.wsc.StatisticCounter;

public class StatisticChart {
    private final LineChart chart;
    private final XYChart.Series mapSeries;

    public StatisticChart(String title) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        x.setLabel("War's day");

        this.chart = new LineChart(x, y);
        this.mapSeries = new XYChart.Series();

        chart.setTitle(title);
        mapSeries.setName("Field's Map information");
        chart.setCreateSymbols(false);
        //TODO style
        chart.getData().addAll(mapSeries);
    }

    public LineChart getChart() {
        return chart;
    }

    public void updateTroopChart(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getNumberTroops()));
    }

    public void updateTrenchChart(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getNumberTrenches()));
    }

    public void updateAvgEnergyLevel(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getAvgEnergyLevel()));
    }

    public void updateAvgLifeLength(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getAvgLife()));
    }

    public void updateAvgSupportTroops(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getAvgSupportTroops()));
    }

    public void updateFreeSpaceQuantity(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getFreeSpaceQuantity()));
    }

    public void updateDeathTroops(StatisticCounter statsCounter) {
        mapSeries.getData().add(new XYChart.Data<>(statsCounter.getWarDays(), statsCounter.getNumberDeadTroops()));
    }
}