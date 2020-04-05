package calls.composing;

import calls.CallChain;
import calls.composing.composers.CallComposer;
import exceptions.TypeError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FilterMapComposeManagerTest {
    @Mock
    private CallComposer composer;
    @Spy
    private CallChain callChain;

    private ComposerManager manager = new FilterMapComposeManager();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        manager.addComposer(composer);
    }

    @Test
    public void managerExecutesComposerOverCallChain() throws TypeError {
        when(callChain.getLength())
                .thenReturn(3)
                .thenReturn(2);
        manager.runComposing(callChain);

        verify(composer).compose(callChain);
    }
}