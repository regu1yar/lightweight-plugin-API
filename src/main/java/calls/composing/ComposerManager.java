package calls.composing;

import calls.CallChain;
import calls.composing.composers.CallComposer;
import exceptions.TypeError;

public interface ComposerManager {
    void runComposing(CallChain callChain) throws TypeError;
    void addComposer(CallComposer composer);
}
