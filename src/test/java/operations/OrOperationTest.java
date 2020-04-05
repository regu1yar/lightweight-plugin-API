package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrOperationTest {
    private Operation orOperation = new OrOperation();

    @Test
    public void applyToLogicalOperands() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "(0 > 1)";
        Expression orExpression = orOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );

        assertTrue(orExpression instanceof LogicalExpression);
        assertEquals(
                "(" + leftOperandRepresentation + " | " + rightOperandRepresentation + ")",
                orExpression.getRepresentation()
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfLeftOperandIsNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(0 > 1)";
        Expression orExpression = orOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfRightOperandIsNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "element";
        Expression orExpression = orOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfBothOperandsAreNotLogicalExpression() throws TypeError {
        String leftOperandRepresentation = "(element + 10)";
        String rightOperandRepresentation = "element";
        Expression orExpression = orOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }
}