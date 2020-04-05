package calls;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapCallTest {
    private MapCall mapCall;

    @Test
    public void representationTest() throws TypeError {
        String argument = "element";
        mapCall = new MapCall(new ArithmeticalExpression(argument));

        assertEquals("map{" + argument + "}", mapCall.getRepresentation());
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorExceptionIfNonArithmeticalArgument() throws TypeError {
        mapCall = new MapCall(new LogicalExpression("(element > 0)"));
    }
}