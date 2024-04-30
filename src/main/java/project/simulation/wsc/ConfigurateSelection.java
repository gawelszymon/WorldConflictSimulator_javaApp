package project.simulation.wsc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.*;


public class ConfigurateSelection {
    private static final String csv_file = "src/main/resources/config.csv";
    private static final String csv_file_split = ",";

    public static List<String[]> read() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(csv_file));

        List<String[]> conf_options = new LinkedList<>();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            conf_options.add(line.split(csv_file_split));
        }
        return conf_options;
    }

    public static void add(String name, String[] config) throws Exception {
        FileWriter writer = new FileWriter(csv_file, true);

        String line = String.join(csv_file_split, config); //connect elements of config array and seperate it with csv_file_split- comma
        line = name + "," + line;
        line += "\n";
        writer.write(line);

        writer.flush(); //sendind the data to accurate, right file
        writer.close();
    }

    public static String[] find(String name) throws FileNotFoundException {
        List<String[]> configs = read();
        for (String[] config : configs){
            if(config[0].equals(name)) return Arrays.copyOfRange(config, 1, config.length);
        }
        return null;
    }

    public static String[] names() throws FileNotFoundException {
        List<String[]> configs = read();
        String[] list = new String[configs.size() - 1];
        for (int i = 0; i < list.length; i++) {
            list[i] = configs.get(i + 1)[0];
        }
        return list;
    }
}
