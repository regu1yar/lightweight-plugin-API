package calls.composing.composers;

import calls.Call;
import calls.CallChain;
import calls.MapCall;
import converters.ElementValueInjector;
import converters.ExpressionConverter;
import exceptions.TypeError;

import java.util.Collections;

public class MapMapComposer implements CallComposer {
    @Override
    public void compose(CallChain callChain) throws TypeError {
        callChain.execute((Call firstCall, Call secondCall) -> {
            if (!(firstCall instanceof MapCall) || !(secondCall instanceof MapCall)) {
                return Collections.emptyList();
            }

            ExpressionConverter converter = new ElementValueInjector(firstCall.getArgument());
            return Collections.singletonList(new MapCall(converter.convert(secondCall.getArgument())));
        });
    }
}
