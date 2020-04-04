package calls.composing.composers;

import calls.Call;
import calls.CallChain;
import calls.FilterCall;
import exceptions.TypeError;
import operations.AndOperation;

import java.util.Collections;

public class FilterFilterComposer implements CallComposer {
    @Override
    public void compose(CallChain callChain) throws TypeError {
        callChain.execute((Call firstCall, Call secondCall) -> {
            if (!(firstCall instanceof FilterCall) || !(secondCall instanceof FilterCall)) {
                return Collections.emptyList();
            }

            return Collections.singletonList(
                    new FilterCall(
                            new AndOperation().applyTo(firstCall.getArgument(), secondCall.getArgument())));
        });
    }
}
