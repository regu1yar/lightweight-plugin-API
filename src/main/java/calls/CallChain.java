package calls;

import calls.composing.composers.ComposeAction;
import exceptions.TypeError;
import utils.Representable;

import java.util.ArrayList;
import java.util.List;

public class CallChain implements Representable {
    private final ArrayList<Call> calls;

    public CallChain() {
        calls = new ArrayList<>();
    }

    public CallChain(ArrayList<Call> calls) {
        this.calls = calls;
    }

    @Override
    public String getRepresentation() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Call call : calls) {
            stringBuilder.append(call.getRepresentation()).append("%>%");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        }

        return stringBuilder.toString();
    }

    public int getLength() {
        return calls.size();
    }

    public void add(Call call) {
        calls.add(call);
    }

    public void add(int index, Call call) {
        calls.add(index, call);
    }

    public void execute(ComposeAction composeAction) throws TypeError {
        for (int i = 1; i < calls.size(); i++) {
            List<Call> composition = composeAction.applyTo(calls.get(i - 1), calls.get(i));
             if (composition.size() > 0) {
                 calls.remove(i - 1);
                 calls.remove(i - 1);
                 for (int j = composition.size() - 1; j >= 0; j--) {
                      calls.add(i - 1, composition.get(j));
                 }
                 break;
             }
        }
    }
}
