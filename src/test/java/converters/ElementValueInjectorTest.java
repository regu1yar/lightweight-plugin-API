package converters;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElementValueInjectorTest {
    ExpressionConverter valueInjector;

    @Test
    public void convertArithmeticalExpression() throws TypeError {
        String elementValue = "(element + 10)";
        valueInjector = new ElementValueInjector(new ArithmeticalExpression(elementValue));
        String oldExpressionRepresentation = "element";
        Expression oldExpression = new ArithmeticalExpression(oldExpressionRepresentation);
        Expression newExpression = valueInjector.convert(oldExpression);

        assertTrue(newExpression instanceof ArithmeticalExpression);
        assertEquals(elementValue, newExpression.getRepresentation());
    }

    @Test
    public void convertLogicalExpression() throws TypeError {
        String elementValue = "(element + 10)";
        valueInjector = new ElementValueInjector(new ArithmeticalExpression(elementValue));
        String oldExpressionRepresentation = "(element > 0)";
        Expression oldExpression = new LogicalExpression(oldExpressionRepresentation);
        Expression newExpression = valueInjector.convert(oldExpression);

        assertTrue(newExpression instanceof LogicalExpression);
        assertEquals("(" + elementValue + " > 0)", newExpression.getRepresentation());
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfElementValueIsNotArithmeticalExpression() throws TypeError {
        valueInjector = new ElementValueInjector(new LogicalExpression("(0 = 0)"));
    }
}