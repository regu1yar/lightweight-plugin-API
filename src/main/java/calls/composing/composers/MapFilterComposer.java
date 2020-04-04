package calls.composing.composers;

import calls.Call;
import calls.CallChain;
import calls.FilterCall;
import calls.MapCall;
import converters.ElementValueInjector;
import converters.ExpressionConverter;
import exceptions.TypeError;

import java.util.Arrays;
import java.util.Collections;

public class MapFilterComposer implements CallComposer {
    @Override
    public void compose(CallChain callChain) throws TypeError {
        callChain.execute((Call firstCall, Call secondCall) -> {
            if (!(firstCall instanceof MapCall) || !(secondCall instanceof FilterCall)) {
                return Collections.emptyList();
            }

            ExpressionConverter converter = new ElementValueInjector(firstCall.getArgument());
            return Arrays.asList(
                    new FilterCall(converter.convert(secondCall.getArgument())),
                    firstCall
            );
        });
    }
}
