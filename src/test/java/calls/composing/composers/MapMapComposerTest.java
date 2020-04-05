package calls.composing.composers;

import calls.CallChain;
import calls.MapCall;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MapMapComposerTest {
    CallComposer composer = new MapMapComposer();

    @Test
    public void composeTwoFilterCalls() throws TypeError {
        String firstCallArgument = "(element + 10)";
        String secondCallArgument = "(element * 10)";
        CallChain callChain = new CallChain(new ArrayList<>(Arrays.asList(
                new MapCall(new ArithmeticalExpression(firstCallArgument)),
                new MapCall(new ArithmeticalExpression(secondCallArgument))
        )));

        composer.compose(callChain);

        assertEquals(1, callChain.getLength());
        assertEquals(
                "map{(" + firstCallArgument + " * 10)}",
                callChain.getRepresentation()
        );
    }
}