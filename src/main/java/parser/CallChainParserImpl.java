package parser;

import calls.Call;
import calls.CallChain;
import calls.FilterCall;
import calls.MapCall;
import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import operations.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallChainParserImpl implements CallChainParser {
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

    private static final Set<String> CALL_NAMES = Set.of("map", "filter");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "\\s*(\\d+)\\s*"
            + "|\\s*(element)\\s*"
            + "|\\s*\\(\\s*(.+?)\\s*([+\\-*><=&|])\\s*(.+?)\\s*\\)\\s*"
    );

    @Override
    public CallChain parse(String representation) throws SyntaxError, TypeError {
        String callRegexp = "(" + String.join("|", CALL_NAMES) + ")" + "\\{(.+?)}(?:%>%)?";
        Pattern pattern = Pattern.compile(callRegexp);
        Matcher matcher = pattern.matcher(representation);

        ArrayList<Call> calls = new ArrayList<>();
        int lastMatchEnd = 0;
        while (matcher.find()) {
            if (lastMatchEnd != matcher.start()) {
                throw new SyntaxError("Unable to parse call chain: " + representation);
            } else {
                Expression expression = parseExpression(matcher.group(2));
                switch (matcher.group(1)) {
                    case "map":
                        calls.add(new MapCall(expression));
                        break;
                    case "filter":
                        calls.add(new FilterCall(expression));
                        break;
                    default:
                        throw new SyntaxError("Unexpected call :" + matcher.group(1));
                }

                lastMatchEnd = matcher.end();
            }
        }

        if (lastMatchEnd != representation.length()) {
            throw new SyntaxError("Unable to parse call chain: " + representation);
        }

        return new CallChain(calls);
    }

    private Expression parseExpression(String representation) throws SyntaxError, TypeError {
        Matcher matcher = EXPRESSION_PATTERN.matcher(representation);
        if (!matcher.matches()) {
            throw new SyntaxError("Unable to parse expression: " + representation);
        } else {
            if (matcher.group(1) != null || matcher.group(2) != null) {
                return new ArithmeticalExpression(representation);
            } else {
                return SYMBOLS_TO_OPERATIONS.get(matcher.group(4)).applyTo(
                        parseExpression(matcher.group(3)),
                        parseExpression(matcher.group(5))
                );
            }
        }
    }
}
