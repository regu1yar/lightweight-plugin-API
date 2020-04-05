package calls;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilterCallTest {
    private FilterCall filterCall;

    @Test
    public void representationTest() throws TypeError {
        String argument = "(element > 0)";
        filterCall = new FilterCall(new LogicalExpression(argument));

        assertEquals("filter{" + argument + "}", filterCall.getRepresentation());
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorExceptionIfNonLogicalArgument() throws TypeError {
        filterCall = new FilterCall(new ArithmeticalExpression("element"));
    }
}