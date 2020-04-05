package calls.composing.composers;

import calls.CallChain;
import calls.FilterCall;
import calls.MapCall;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.LogicalExpression;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MapFilterComposerTest {
    CallComposer composer = new MapFilterComposer();

    @Test
    public void composeTwoFilterCalls() throws TypeError {
        String firstCallArgument = "(element + 10)";
        String secondCallArgument = "(element > 1)";
        CallChain callChain = new CallChain(new ArrayList<>(Arrays.asList(
                new MapCall(new ArithmeticalExpression(firstCallArgument)),
                new FilterCall(new LogicalExpression(secondCallArgument))
        )));

        composer.compose(callChain);

        assertEquals(2, callChain.getLength());
        assertEquals(
                "filter{(" + firstCallArgument + " > 1)}"
                + "%>%map{" + firstCallArgument + "}",
                callChain.getRepresentation()
        );
    }
}