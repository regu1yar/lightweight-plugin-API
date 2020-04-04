package parser;

import calls.CallChain;
import exceptions.SyntaxError;
import exceptions.TypeError;

public interface CallChainParser {
    CallChain parse(String representation) throws SyntaxError, TypeError;
}
