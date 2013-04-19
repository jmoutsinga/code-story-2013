package net.codestory.jajascript.util;

import java.util.Scanner;

public class FileUtil {

    public static String fileContentToString(String filePath) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream(filePath));
        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine());
        }
        scanner.close();
        return result.toString();
    }
    
}
