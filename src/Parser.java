import java.io.*;

public class Parser {

    private BufferedReader reader;


    public Parser(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void quit() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String next() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isBlank(String instruction) {
        return instruction.equals("") || instruction.charAt(0) == '/';
    }

    public static boolean isAInstruction(String instruction) {
        return !isBlank(instruction) && instruction.charAt(0) == '@';
    }

    public static boolean isLabel(String instruction) {
        return !isBlank(instruction) && instruction.charAt(0) == '(';
    }

    public static boolean isCInstruction(String instruction) {
        return !isBlank(instruction) && !isAInstruction(instruction) && !isLabel(instruction);
    }

    public static boolean isVar(String instruction) {
        return instruction.charAt(1) != '0' && instruction.charAt(1) != '1' && instruction.charAt(1) != '2' && instruction.charAt(1) != '3' && instruction.charAt(1) != '4' && instruction.charAt(1) != '5' && instruction.charAt(1) != '6' && instruction.charAt(1) != '7' && instruction.charAt(1) != '8' && instruction.charAt(1) != '9';
    }

    public static String removeComment(String instruction) {
        String out = "";
        for(int i = 0; i < instruction.length(); i ++) {
            char curr = instruction.charAt(i);
            if(curr == '/') {
                break;
            }
            if(instruction.charAt(i) != ' ') {
                out += instruction.charAt(i);
            }
        }
        return out;
    }
}
