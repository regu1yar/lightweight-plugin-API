package parser;

import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;
import org.junit.Test;

import static org.junit.Assert.*;

public class TypeExpressionParserTest {
    private final ExpressionParser expressionParser = new TypeExpressionParser();

    @Test
    public void parseNumber() throws TypeError, SyntaxError {
        String representation = "10";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseElement() throws TypeError, SyntaxError {
        String representation = "element";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parsePlusOperation() throws TypeError, SyntaxError {
        String representation = "(element + 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseMinusOperation() throws TypeError, SyntaxError {
        String representation = "(element - 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseStarOperation() throws TypeError, SyntaxError {
        String representation = "(element * 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseGreaterOperation() throws TypeError, SyntaxError {
        String representation = "(element > 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseLowerOperation() throws TypeError, SyntaxError {
        String representation = "(element < 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseEqualsOperation() throws TypeError, SyntaxError {
        String representation = "(element = 10)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseAndOperation() throws TypeError, SyntaxError {
        String representation = "((element > 10) & (0 = 1))";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseOrOperation() throws TypeError, SyntaxError {
        String representation = "((element > 10) | (0 = 1))";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseNegativeNumber() throws TypeError, SyntaxError {
        String representation = "-10";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof ArithmeticalExpression);
        assertEquals(representation, expression.getRepresentation());
    }

    @Test
    public void parseNestedParentheses() throws TypeError, SyntaxError {
        String representation = "(((element + 10) - 100) > -1)";

        Expression expression = expressionParser.parse(representation);

        assertTrue(expression instanceof LogicalExpression);
        assertEquals(representation, expression.getRepresentation());
    }
}