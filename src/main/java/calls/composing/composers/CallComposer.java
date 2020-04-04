package calls.composing.composers;

import calls.CallChain;
import exceptions.TypeError;

public interface CallComposer {
    void compose(CallChain callChain) throws TypeError;
}
