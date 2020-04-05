package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class EqualsOperationTest {
    private Operation equalsOperation = new EqualsOperation();

    @Test
    public void applyToArithmeticalOperands() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(element + 10)";
        Expression equalsExpression = equalsOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );

        assertTrue(equalsExpression instanceof LogicalExpression);
        assertEquals(
                "(" + leftOperandRepresentation + " = " + rightOperandRepresentation + ")",
                equalsExpression.getRepresentation()
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfLeftOperandIsNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 > 1)";
        String rightOperandRepresentation = "element";
        Expression equalsExpression = equalsOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new ArithmeticalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfRightOperandIsNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "element";
        String rightOperandRepresentation = "(0 < 1)";
        Expression equalsExpression = equalsOperation.applyTo(
                new ArithmeticalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }

    @Test(expected = TypeError.class)
    public void throwsTypeErrorIfBothOperandsAreNotArithmeticalExpression() throws TypeError {
        String leftOperandRepresentation = "(0 < 1)";
        String rightOperandRepresentation = "(0 > 1)";
        Expression equalsExpression = equalsOperation.applyTo(
                new LogicalExpression(leftOperandRepresentation),
                new LogicalExpression(rightOperandRepresentation)
        );
    }
}