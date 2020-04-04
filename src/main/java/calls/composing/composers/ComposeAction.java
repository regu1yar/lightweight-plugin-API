package calls.composing.composers;

import calls.Call;
import exceptions.TypeError;

import java.util.List;

public interface ComposeAction {
    List<Call> applyTo(Call firstCall, Call secondCall) throws TypeError;
}
