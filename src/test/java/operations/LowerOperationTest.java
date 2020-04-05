package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class LowerOperationTest {
    private Operation lowerOperation = new LowerOperation();

    @Test
    public void applyToArithmeticalOperands() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(element + 10)";
        Expression lowerExpression = lowerOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );

        assertTrue(lowerExpression instanceof LogicalExpression);
        assertEquals(
                "(" + leftOperandRepresentation + " < " + rightOperandRepresentation + ")",
                lowerExpression.getRepresentation()
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfLeftOperandIsNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 > 1)";
        String rightOperandRepresentation = "element";
        Expression lowerExpression = lowerOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfRightOperandIsNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(0 < 1)";
        Expression lowerExpression = lowerOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfBothOperandsAreNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "(0 > 1)";
        Expression lowerExpression = lowerOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }
}