import java.io.*;
import java.util.ArrayList;

public class Converter {

    private BufferedWriter writer;

    public Converter(String outputFile) {
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(String binary) {
        try {
            writer.write(binary + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void quit() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String AInstructionToBinary(String instruction) {
        int value = Integer.parseInt(instruction.substring(1));
        String binaryString = Integer.toBinaryString(value);
        while (binaryString.length() < 16) {
            binaryString = "0" + binaryString;
        }
        return binaryString;
    }

    public String CInstructionToBinary(String instruction) {
        String dest = getDest(instruction);
        String comp = getComp(instruction);
        String jump = getJump(instruction);

        return "111" + compToBinary(comp) + destToBinary(dest) + jumpToBinary(jump);
    }

    private String destToBinary(String dest) {
        if(dest == null) {
            return "000";
        }
        String out = "";
        if(dest.contains("A")) {
            out += "1";
        } else {
            out += "0";
        }
        if(dest.contains("D")) {
            out += "1";
        } else {
            out += "0";
        }
        if(dest.contains("M")) {
            out += "1";
        } else {
            out += "0";
        }
        return out;
    }

    private String compToBinary(String comp) {
        // a = 0
        if(comp.equals("0")) return "0101010";
        if(comp.equals("1")) return "0111111";
        if(comp.equals("-1")) return "0111010";
        if(comp.equals("D")) return "0001100";
        if(comp.equals("A")) return "0110000";
        if(comp.equals("!D")) return "0001101";
        if(comp.equals("!A")) return "0110001";
        if(comp.equals("-D")) return "0001111";
        if(comp.equals("-A")) return "0110011";
        if(comp.equals("D+1")) return "0011111";
        if(comp.equals("A+1")) return "0110111";
        if(comp.equals("D-1")) return "0001110";
        if(comp.equals("A-1")) return "0110010";
        if(comp.equals("D+A")) return "0000010";
        if(comp.equals("D-A")) return "0010011";
        if(comp.equals("A-D")) return "0000111";
        if(comp.equals("D&A")) return "0000000";
        if(comp.equals("D|A")) return "0010101";

        // a = 1
        if(comp.equals("M")) return "1110000";
        if(comp.equals("!M")) return "1110001";
        if(comp.equals("-M")) return "1110011";
        if(comp.equals("M+1")) return "1110111";
        if(comp.equals("M-1")) return "1110010";
        if(comp.equals("D+M")) return "1000010";
        if(comp.equals("D-M")) return "1010011";
        if(comp.equals("M-D")) return "1000111";
        if(comp.equals("D&M")) return "1000000";
        if(comp.equals("D|M")) return "1010101";

        // error case
        return null;
    }
    private String jumpToBinary(String jump) {
        if(jump == null) {
            return "000";
        }
        if(jump.equals("JGT")) {
            return "001";
        }
        if(jump.equals("JEQ")) {
            return "010";
        }
        if(jump.equals("JGE")) {
            return "011";
        }
        if(jump.equals("JLT")) {
            return "100";
        }
        if(jump.equals("JNE")) {
            return "101";
        }
        if(jump.equals("JLE")) {
            return "110";
        }
        if (jump.equals("JMP")) {
            return "111";
        }
        return null;
    }


    private String getComp(String instruction) {
        String out = "";
        for(int i = 0; i < instruction.length(); i ++) {
            if(instruction.charAt(i) == '=') {
                out = "";
            } else {
                out += instruction.charAt(i);
            }
            if(instruction.charAt(i) == ';') {
                return out.substring(0, out.length() - 1);
            }
        }
        return out;
    }

    private boolean destExists(String instruction) {
        return instruction.split("=").length == 2;
    }
    private String getDest(String instruction) {
        if(destExists(instruction)) {
            return instruction.split("=")[0];
        }
        return null;
    }

    private boolean jumpExists(String instruction) {
        return instruction.split(";").length == 2;
    }

    private String getJump(String instruction) {
        if(jumpExists(instruction)) {
            return instruction.split(";")[1];
        }
        return null;
    }

    public String varToNum(String instruction, SymbolTable table) {
        String var = instruction.substring(1);
        if(!table.table.containsKey(var)) {
            table.subscribeVar(var);
        }
        return "@" + table.table.get(var);
    }
}
