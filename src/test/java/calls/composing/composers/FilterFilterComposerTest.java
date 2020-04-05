package calls.composing.composers;

import calls.Call;
import calls.CallChain;
import calls.FilterCall;
import exceptions.TypeError;
import expressions.LogicalExpression;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FilterFilterComposerTest {
    CallComposer composer = new FilterFilterComposer();

    @Test
    public void composeTwoFilterCalls() throws TypeError {
        String firstCallArgument = "(element > 0)";
        String secondCallArgument = "(0 > 1)";
        CallChain callChain = new CallChain(new ArrayList<>(Arrays.asList(
                new FilterCall(new LogicalExpression(firstCallArgument)),
                new FilterCall(new LogicalExpression(secondCallArgument))
        )));

        composer.compose(callChain);

        assertEquals(1, callChain.getLength());
        assertEquals(
                "filter{(" + firstCallArgument + " & " + secondCallArgument + ")}",
                callChain.getRepresentation()
        );
    }
}