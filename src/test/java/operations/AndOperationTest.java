package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndOperationTest {
    private Operation andOperation = new AndOperation();

    @Test
    public void applyToLogicalOperands() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "(0 > 1)";
        Expression andExpression = andOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );

        assertTrue(andExpression instanceof LogicalExpression);
        assertEquals(
                "(" + leftOperandRepresentation + " & " + rightOperandRepresentation + ")",
                andExpression.getRepresentation()
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfLeftOperandIsNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(0 > 1)";
        Expression andExpression = andOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfRightOperandIsNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "element";
        Expression andExpression = andOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfBothOperandsAreNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "(element + 10)";
        String rightOperandRepresentation = "element";
        Expression andExpression = andOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }
}