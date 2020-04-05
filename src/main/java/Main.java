import calls.CallChain;
import calls.composing.ComposerManager;
import calls.composing.FilterMapComposeManager;
import calls.composing.composers.FilterFilterComposer;
import calls.composing.composers.MapFilterComposer;
import calls.composing.composers.MapMapComposer;
import exceptions.SyntaxError;
import exceptions.TypeError;
import parser.CallChainParser;
import parser.CallChainParserImpl;
import parser.ExpressionParser;
import parser.TypeExpressionParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ExpressionParser expressionParser = new TypeExpressionParser();
            CallChainParser parser = new CallChainParserImpl(expressionParser);
            Scanner scan = new Scanner(System.in);
            CallChain callChain = parser.parse(scan.nextLine());

            ComposerManager composerManager = new FilterMapComposeManager();
            composerManager.addComposer(new FilterFilterComposer());
            composerManager.addComposer(new MapFilterComposer());
            composerManager.addComposer(new MapMapComposer());

            composerManager.runComposing(callChain);
            System.out.println(callChain.getRepresentation());
        } catch (SyntaxError e) {
            System.out.println("SYNTAX ERROR");
            System.err.println(e.getMessage());
        } catch (TypeError e) {
            System.out.println("TYPE ERROR");
            System.err.println(e.getMessage());
        }
    }
}
