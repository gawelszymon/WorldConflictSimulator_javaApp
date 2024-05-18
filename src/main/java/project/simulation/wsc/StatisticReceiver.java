package project.simulation.wsc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class StatisticReceiver {

    private static String SETTINGS_FILE;
    private static final String CSV_SEPARATOR = ",";

    public void save(StatisticCounter statsCounter) throws IOException {
        String[] stats = statisticsParser(statsCounter);

        FileWriter writer = new FileWriter(SETTINGS_FILE, true);

        String line = String.join(CSV_SEPARATOR, stats);
        line += "\n";

        writer.flush();
        writer.close();
    }

    public void setSettingsFile(String name) throws Exception {
        String date = "";
        Calendar calendar = Calendar.getInstance();
        date += calendar.get(Calendar.DAY_OF_MONTH);
        date += calendar.get(Calendar.MONTH);
        date += calendar.get(Calendar.YEAR);
        date += calendar.get(Calendar.HOUR_OF_DAY);
        date += calendar.get(Calendar.MINUTE);
        date += calendar.get(Calendar.SECOND);

        SETTINGS_FILE = "src/main/resources/statistics" + name + "_" + date;

        if (new File(SETTINGS_FILE).exists()) {
            throw new Exception("This simulation model exists");
        }

        FileWriter writer = new FileWriter(SETTINGS_FILE, true); //true mean that file is in append mode - new data will be written at the end of the file (false perform overwrite)
        String headers = "war day,troops quantity,trenches quantity,dead troops quantity,avg support quantity," +
                "avg life length,avg energy level,free space quantity,mian feature\n";
        writer.write(headers);
        writer.flush(); //it's really important, because it ensures us that any data, which is currently in buffer will be actually write out of the file as soon as possible in the disk, buffering is really positive occurrence - it provide reducing of disk writers, which are relatively slow
        writer.close();
    }

    private String[] statisticsParser(StatisticCounter statisticCounter) {
        String[] statParsed = new String[9];

        statParsed[0] = String.valueOf(statisticCounter.getWarDays());
        statParsed[1] = String.valueOf(statisticCounter.getNumberTroops());
        statParsed[2] = String.valueOf(statisticCounter.getNumberTrenches());
        statParsed[3] = String.valueOf(statisticCounter.getNumberDeadTroops());
        statParsed[4] = String.valueOf(statisticCounter.getAvgSupportTroops());
        statParsed[5] = String.valueOf(statisticCounter.getAvgLife());
        statParsed[6] = String.valueOf(statisticCounter.getAvgEnergyLevel());
        statParsed[7] = String.valueOf(statisticCounter.getFreeSpaceQuantity());
        statParsed[8] = String.valueOf(statisticCounter.getMainFeature());

        return statParsed;
    }
}
