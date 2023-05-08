package cdl.interview.kata;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutApplicationTests {
    @Test
    void main_validSystemInputLines_outputsCorrectPrintStatements() {
        String input = String.format("A%sB%sZ%s%s", System.lineSeparator(), System.lineSeparator(), System.lineSeparator(), System.lineSeparator());

        ByteArrayInputStream byteArrInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrInputStream);

        String expectedLine1 = "Please enter items to scan";
        String expectedLine2 = "Scanned item =  A";
        String expectedLine3 = "Running Total = 50.0";
        String expectedLine4 = "Scanned item =  B";
        String expectedLine5 = "Running Total = 80.0";
        String expectedLine6 = "Scanned item =  Z";
        String expectedLine7 = "Scanned item: Z does not exist in the item catalogue. Scanned item Z will be ignored.";
        String expectedLine8 = "Scanned item =  ";
        String expectedLine9 = "Total price =  80.0";

        ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrOutputStream);
        System.setOut(printStream);

        CheckoutApplication.main(null);

        String[] lines = byteArrOutputStream.toString().split(System.lineSeparator());
        String outputLine1 = lines[0];
        String outputLine2 = lines[1];
        String outputLine3 = lines[2];
        String outputLine4 = lines[3];
        String outputLine5 = lines[4];
        String outputLine6 = lines[5];
        String outputLine7 = lines[6];
        String outputLine8 = lines[7];
        String outputLine9 = lines[8];

        assertThat(expectedLine1).isEqualTo(outputLine1);
        assertThat(expectedLine2).isEqualTo(outputLine2);
        assertThat(expectedLine3).isEqualTo(outputLine3);
        assertThat(expectedLine4).isEqualTo(outputLine4);
        assertThat(expectedLine5).isEqualTo(outputLine5);
        assertThat(expectedLine6).isEqualTo(outputLine6);
        assertThat(expectedLine7).isEqualTo(outputLine7);
        assertThat(expectedLine8).isEqualTo(outputLine8);
        assertThat(expectedLine9).isEqualTo(outputLine9);
    }
}
