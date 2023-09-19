import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // change as you want
        String inputFilePath = "src/rect/Rect.asm";
        String outputFilePath = "Rect.hack";

        // first parse
        // scan the file and subscribe the label and remove whitespaces
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(inputFilePath);
        ArrayList<String> commands = new ArrayList<>(); // store all commands with no label

        String command;
        while((command = parser.next()) != null) {
            if(Parser.isLabel(command)) {
                // add substring bc label is (label)
                symbolTable.subscribeLabel(command.substring(1, command.length() - 1), commands.size());
            }
            if(Parser.isAInstruction(command) || Parser.isCInstruction(command)) {
                String commandWithoutComment = Parser.removeComment(command);
                commands.add(commandWithoutComment);
            }
        }

        // second parse
        // for each command, translate the asm command to corresponding binary command
        Converter converter = new Converter(outputFilePath);
        for(String com : commands) {
            // in case A instruction
            if(Parser.isAInstruction(com)) {
                if(Parser.isVar(com)) {
                    com = converter.varToNum(com, symbolTable);
                }
                converter.write(converter.AInstructionToBinary(com));
            }
            // in case C instruction
            else {
                converter.write(converter.CInstructionToBinary(com));
            }
        }

        // terminate
        parser.quit();
        converter.quit();
    }
}
