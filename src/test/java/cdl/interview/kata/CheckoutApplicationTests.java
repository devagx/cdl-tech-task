package cdl.interview.kata;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutApplicationTests {
    @Test
    void main_validSystemInputLines_outputsCorrectPrintStatements() {
        String input = String.format("A%sB%sZ%s", System.lineSeparator(), System.lineSeparator(), System.lineSeparator());

        ByteArrayInputStream byteArrInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrInputStream);

        String expectedLine1 = "Scanned item =  Running Total = 50.0";
        String expectedLine2 = "Scanned item =  Running Total = 80.0";
        String expectedLine3 = "Scanned item =  Scanned item: Z does not exist in the item catalogue. Scanned item Z will be ignored.";
        String expectedLine4 = "Total price =  80.0";

        ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrOutputStream);
        System.setOut(printStream);

        CheckoutApplication.main(null);

        String[] lines = byteArrOutputStream.toString().split(System.lineSeparator());
        String outputLine1 = lines[0];
        String outputLine2 = lines[1];
        String outputLine3 = lines[2];
        String outputLine4 = lines[3];

        assertThat(expectedLine1).isEqualTo(outputLine1);
        assertThat(expectedLine2).isEqualTo(outputLine2);
        assertThat(expectedLine3).isEqualTo(outputLine3);
        assertThat(expectedLine4).isEqualTo(outputLine4);
    }
}
