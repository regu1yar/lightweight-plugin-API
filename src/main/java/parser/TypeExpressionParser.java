package parser;

import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import operations.*;

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
    private static final String OPERAND_REGEXP = "\\(.+?\\)" + "|" + NUMBER_REGEXP + "|" + ELEMENT_REGEXP;
    private static final String OPERATION_REGEXP = "[+\\-*><=&|]";
    private static final String BINARY_OPERATION_REGEXP = "\\(\\s*(" + OPERAND_REGEXP + ")" +
                                                            "\\s*(" + OPERATION_REGEXP + ")\\s*" +
                                                            "(" + OPERAND_REGEXP + ")\\s*\\)";

    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "\\s*(" + NUMBER_REGEXP + ")\\s*|\\s*(" + ELEMENT_REGEXP + ")\\s*|\\s*" + BINARY_OPERATION_REGEXP + "\\s*"
    );

    @Override
    public Expression parse(String representation) throws SyntaxError, TypeError {
        Matcher matcher = EXPRESSION_PATTERN.matcher(representation);
        if (!matcher.matches()) {
            throw new SyntaxError("Unable to parse expression: " + representation);
        } else {
            if (matcher.group(1) != null || matcher.group(2) != null) {
                return new ArithmeticalExpression(representation);
            } else {
                return SYMBOLS_TO_OPERATIONS.get(matcher.group(4)).applyTo(
                        parse(matcher.group(3)),
                        parse(matcher.group(5))
                );
            }
        }
    }
}
