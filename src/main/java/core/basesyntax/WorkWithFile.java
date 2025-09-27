package core.basesyntax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class WorkWithFile {
    public static final int INDEX_OF_CATEGORY = 0;
    public static final int INDEX_OF_VALUE = 1;
    public static final int LENGTH_OF_ARRAY = 2;
    public static final int SUPPLY = 0;
    public static final int BUY = 1;

    public void getStatistic(String fromFileName, String toFileName) {
        try {
            int[] statistics = parseInformationFromFile(fromFileName);
            String report = buildReport(statistics);
            writeReport(toFileName, report);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while processing files: "
                    + fromFileName + " or " + toFileName, e);
        }
    }

    private int[] parseInformationFromFile(String fromFileName) throws IOException {
        int[] result = new int[LENGTH_OF_ARRAY];
        BufferedReader reader = new BufferedReader(new FileReader(fromFileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] separatedLine = line.trim().split(",");
            try {
                switch (separatedLine[INDEX_OF_CATEGORY]) {
                    case "supply" -> result[SUPPLY]
                            += Integer.parseInt(separatedLine[INDEX_OF_VALUE]);
                    case "buy" -> result[BUY]
                            += Integer.parseInt(separatedLine[INDEX_OF_VALUE]);
                    default -> System.out.println("Unknown operation.");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new RuntimeException("Can't process .CSV file...", e);
            }
        }
        return result;
    }

    private String buildReport(int[] statistics) {
        String lineSeparator = System.lineSeparator();
        return new StringBuilder()
                .append("supply,").append(statistics[0]).append(lineSeparator)
                .append("buy,").append(statistics[1]).append(lineSeparator)
                .append("result,").append(statistics[0] - statistics[1])
                .toString();
    }

    private void writeReport(String toFileName, String content) throws IOException {
        Files.write(
                Path.of(toFileName),
                content.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}
