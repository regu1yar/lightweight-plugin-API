package calls;

import expressions.Expression;
import utils.Representable;

public interface Call extends Representable {
    Expression getArgument();
}
