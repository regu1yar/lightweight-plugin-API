package parser;

import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import operations.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeExpressionParser implements ExpressionParser {
    private static final Map<String, Operation> SYMBOLS_TO_OPERATIONS = Map.of(
            "+", new PlusOperation(),
            "-", new MinusOperation(),
            "*", new StarOperation(),
            ">", new GreaterOperation(),
            "<", new LowerOperation(),
            "=", new EqualsOperation(),
            "&", new AndOperation(),
            "|", new OrOperation()
    );

    private static final String NUMBER_REGEXP = "-?\\s*\\d+";
    private static final String ELEMENT_REGEXP = "element";
    private static final String SIMPLE_OPERAND_REGEXP =  NUMBER_REGEXP + "|" + ELEMENT_REGEXP;
    private static final String PARENTHESES_OPERAND_REGEXP = "\\(.+\\)";
    private static final String OPERATION_REGEXP = "[+\\-*><=&|]";

    @Override
    public Expression parse(String representation) throws SyntaxError, TypeError {
        Pattern simpleExpressionPattern = Pattern.compile("\\s*(" + SIMPLE_OPERAND_REGEXP + ")\\s*");
        Matcher simpleMatcher = simpleExpressionPattern.matcher(representation);
        if (simpleMatcher.matches()) {
            return new ArithmeticalExpression(representation);
        }

        if (!representation.startsWith("(") || !representation.endsWith(")")) {
            throw new SyntaxError("Unable to parse expression: " + representation);
        }

        String innerExpression = representation.substring(1, representation.length() - 1);
        ArrayList<String> parts = split(innerExpression);
        if (parts.size() < 1 || parts.size() > 3) {
            throw new SyntaxError("Unable to parse expression: " + representation);
        }

        switch (parts.size()) {
            case 1:
                Pattern withoutParenthesesExpression = Pattern.compile(
                        "\\(\\s*(" + SIMPLE_OPERAND_REGEXP + ")\\s*" +
                                "\\s*(" + OPERATION_REGEXP + ")\\s*" +
                                "\\s*(" + SIMPLE_OPERAND_REGEXP + ")\\s*\\)"
                );
                Matcher withoutParenthesesMatcher = withoutParenthesesExpression.matcher(representation);
                if (!withoutParenthesesMatcher.matches()) {
                    throw new SyntaxError("Unable to parse expression: " + representation);
                } else {
                    return SYMBOLS_TO_OPERATIONS.get(withoutParenthesesMatcher.group(2)).applyTo(
                            parse(withoutParenthesesMatcher.group(1)),
                            parse(withoutParenthesesMatcher.group(3))
                    );
                }
            case 2:
                if (parts.get(0).endsWith(")")) {
                    Pattern parenthesesOperandPattern = Pattern.compile("\\s*(" + PARENTHESES_OPERAND_REGEXP + ")\\s*");
                    Pattern operationWithOperandPattern = Pattern.compile(
                            "\\s*(" + OPERATION_REGEXP + ")\\s*" +
                                    "\\s*(" + SIMPLE_OPERAND_REGEXP + ")\\s*"
                    );

                    Matcher parenthesesOperandMatcher = parenthesesOperandPattern.matcher(parts.get(0));
                    Matcher operationWithOperandMatcher = operationWithOperandPattern.matcher(parts.get(1));

                    if (!parenthesesOperandMatcher.matches() || !operationWithOperandMatcher.matches()) {
                        throw new SyntaxError("Unable to parse expression: " + representation);
                    } else {
                        return SYMBOLS_TO_OPERATIONS.get(operationWithOperandMatcher.group(1)).applyTo(
                                parse(parenthesesOperandMatcher.group(1)),
                                parse(operationWithOperandMatcher.group(2))
                        );
                    }
                } else {
                    Pattern parenthesesOperandPattern = Pattern.compile("\\s*(" + PARENTHESES_OPERAND_REGEXP + ")\\s*");
                    Pattern operandWithOperationPattern = Pattern.compile(
                            "\\s*(" + SIMPLE_OPERAND_REGEXP + ")\\s*" +
                                    "\\s*(" + OPERATION_REGEXP + ")\\s*"
                    );

                    Matcher operandWithOperationMatcher = operandWithOperationPattern.matcher(parts.get(0));
                    Matcher parenthesesOperandMatcher = parenthesesOperandPattern.matcher(parts.get(1));

                    if (!operandWithOperationMatcher.matches() || !parenthesesOperandMatcher.matches() ) {
                        throw new SyntaxError("Unable to parse expression: " + representation);
                    } else {
                        return SYMBOLS_TO_OPERATIONS.get(operandWithOperationMatcher.group(2)).applyTo(
                                parse(operandWithOperationMatcher.group(1)),
                                parse(parenthesesOperandMatcher.group(1))
                        );
                    }
                }
            case 3:
                Pattern parenthesesOperandPattern = Pattern.compile("\\s*(" + PARENTHESES_OPERAND_REGEXP + ")\\s*");
                Pattern operationPattern = Pattern.compile("\\s*(" + OPERATION_REGEXP + ")\\s*");

                Matcher firstOperandMatcher = parenthesesOperandPattern.matcher(parts.get(0));
                Matcher operationMatcher = operationPattern.matcher(parts.get(1));
                Matcher secondOperandMatcher = parenthesesOperandPattern.matcher(parts.get(2));

                if (!firstOperandMatcher.matches() ||
                        !operationMatcher.matches() ||
                        !secondOperandMatcher.matches()) {
                    throw new SyntaxError("Unable to parse expression: " + representation);
                } else {
                    return SYMBOLS_TO_OPERATIONS.get(operationMatcher.group(1)).applyTo(
                            parse(firstOperandMatcher.group(1)),
                            parse(secondOperandMatcher.group(1))
                    );
                }
            default:
                throw new SyntaxError("Unreachable part of parsing");
        }
    }

    private ArrayList<String> split(String expression) throws SyntaxError {
        int parenthesesBalance = 0;
        ArrayList<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        for (char c : expression.toCharArray()) {
            switch (c) {
                case '(':
                    if (parenthesesBalance == 0 && currentPart.length() != 0) {
                        parts.add(currentPart.toString());
                        currentPart.setLength(0);
                    }

                    currentPart.append(c);
                    parenthesesBalance += 1;
                    break;
                case ')':
                    currentPart.append(c);
                    parenthesesBalance -= 1;

                    if (parenthesesBalance < 0) {
                        throw new SyntaxError("Unable to parse expression: " + expression);
                    } else if (parenthesesBalance == 0) {
                        parts.add(currentPart.toString());
                        currentPart.setLength(0);
                    }
                    break;
                default:
                    currentPart.append(c);
            }
        }

        if (parenthesesBalance != 0) {
            throw new SyntaxError("Unable to parse expression: " + expression);
        } else if (currentPart.length() != 0) {
            parts.add(currentPart.toString());
        }

        return parts;
    }
}
