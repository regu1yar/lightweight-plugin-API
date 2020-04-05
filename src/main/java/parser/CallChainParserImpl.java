package parser;

import calls.Call;
import calls.CallChain;
import calls.FilterCall;
import calls.MapCall;
import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.Expression;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallChainParserImpl implements CallChainParser {
    private static final Set<String> CALL_NAMES = Set.of("map", "filter");
    private final ExpressionParser expressionParser;

    public CallChainParserImpl(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

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
                Expression expression = expressionParser.parse(matcher.group(2));
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
}
