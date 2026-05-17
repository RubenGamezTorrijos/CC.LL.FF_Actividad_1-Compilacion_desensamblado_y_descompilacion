// Decompile main function in Ghidra Headless
// @author Antigravity
// @category Decompiler

import ghidra.app.script.GhidraScript;
import ghidra.app.decompiler.DecompInterface;
import ghidra.app.decompiler.DecompileResults;
import ghidra.program.model.listing.Function;
import ghidra.program.model.listing.FunctionIterator;
import ghidra.program.model.listing.FunctionManager;
import ghidra.util.task.ConsoleTaskMonitor;
import java.io.File;
import java.io.FileWriter;

public class DecompileMain extends GhidraScript {

    @Override
    public void run() throws Exception {
        // Setup decompiler
        DecompInterface decomp = new DecompInterface();
        decomp.openProgram(currentProgram);

        // Find main function
        FunctionManager fm = currentProgram.getFunctionManager();
        FunctionIterator functions = fm.getFunctions(true);
        Function mainFunc = null;

        while (functions.hasNext()) {
            Function f = functions.next();
            if (f.getName().equals("main") || f.getName().contains("main")) {
                mainFunc = f;
                break;
            }
        }

        if (mainFunc == null) {
            println("Functions in program:");
            functions = fm.getFunctions(true);
            while (functions.hasNext()) {
                Function f = functions.next();
                println("  - " + f.getName());
                if (f.getName().toLowerCase().contains("main")) {
                    mainFunc = f;
                }
            }
        }

        if (mainFunc == null) {
            println("ERROR: Function 'main' not found!");
            return;
        }

        println("Found function: " + mainFunc.getName());

        // Decompile
        ConsoleTaskMonitor monitor = new ConsoleTaskMonitor();
        DecompileResults results = decomp.decompileFunction(mainFunc, 60, monitor);

        if (results.decompileCompleted()) {
            String decompiledCode = results.getDecompiledFunction().getC();
            println("=== DECOMPILED MAIN ===");
            println(decompiledCode);
            println("=======================");

            // Determine output file
            String outputFileName = "evidencias/c/c_decompile.txt";
            if (currentProgram.getName().contains("HolaMundo") || currentProgram.getName().contains("class")) {
                outputFileName = "evidencias/java/java_decompile.txt";
            }

            File outputFile = new File(outputFileName);
            // Ensure parent directories exist
            outputFile.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(decompiledCode);
            }
            println("Saved to " + outputFile.getAbsolutePath());
        } else {
            println("ERROR: Decompilation failed: " + results.getErrorMessage());
        }
    }
}
