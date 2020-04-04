package calls.composing;

import calls.CallChain;
import calls.FilterCall;
import calls.MapCall;
import calls.composing.composers.CallComposer;
import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.LogicalExpression;

import java.util.ArrayList;
import java.util.Collection;

public class FilterMapComposeManager implements ComposerManager {
    private final Collection<CallComposer> composers = new ArrayList<>();

    @Override
    public void runComposing(CallChain callChain) throws TypeError {
        callChain.add(0, new FilterCall(new LogicalExpression("(0 = 0)")));
        callChain.add(new MapCall(new ArithmeticalExpression("element")));

        while (callChain.getLength() != 2) {
            for (CallComposer composer : composers) {
                composer.compose(callChain);
            }
        }
    }

    @Override
    public void addComposer(CallComposer composer) {
        composers.add(composer);
    }
}
