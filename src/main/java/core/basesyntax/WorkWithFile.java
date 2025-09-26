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

    public void getStatistic(String fromFileName, String toFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFileName))) {
            int[] result = new int[LENGTH_OF_ARRAY];
            String[] separatedLine;
            String line;

            while ((line = reader.readLine()) != null) {
                separatedLine = line.split(",");
                switch (separatedLine[INDEX_OF_CATEGORY]) {
                    case "supply" -> result[0] += Integer.parseInt(separatedLine[INDEX_OF_VALUE]);
                    case "buy" -> result[1] += Integer.parseInt(separatedLine[INDEX_OF_VALUE]);
                    default -> System.out.println("Unknown operation.");
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("supply,").append(result[0]).append(System.lineSeparator())
                    .append("buy,").append(result[1]).append(System.lineSeparator())
                    .append("result,").append(result[0] - result[1]);

            Files.write(
                    Path.of(toFileName),
                    stringBuilder.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Can't read that file: " + fromFileName, e);
        }
    }
}
