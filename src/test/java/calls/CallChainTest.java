package calls;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class CallChainTest {
    private CallChain callChain = new CallChain();

    @Test
    public void representationTest() throws TypeError {
        String mapArgument = "element";
        String filterArgument = "(element > 0)";
        callChain.add(new MapCall(new ArithmeticalExpression(mapArgument)));
        callChain.add(new FilterCall(new LogicalExpression(filterArgument)));

        assertEquals("map{" + mapArgument + "}%>%filter{" + filterArgument + "}", callChain.getRepresentation());
    }
}