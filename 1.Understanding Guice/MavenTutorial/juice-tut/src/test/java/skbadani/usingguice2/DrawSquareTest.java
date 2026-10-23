package skbadani.usingguice2;

import skbadani.usingguice.AuditGuard;
import skbadani.usingguice.DrawSquare;
import skbadani.usingguice.AppModule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Test;

public class DrawSquareTest {

    @Test
    public void draw_withoutGoingThroughGuice_throwsBecauseNotAudited()
    {
        DrawSquare drawSquare = new DrawSquare(() -> {
            throw new UnsupportedOperationException("should not be reached");
        });

        try
        {
            drawSquare.draw();
            fail("Expected draw() to fail when AuditGuard has not been set");
        }
        catch (IllegalStateException expected)
        {
            // draw() checks AuditGuard.isAudited() before doing anything else
        }
    }

    @Test
    public void draw_throughGuiceProxy_isAuditedByAuditInterceptor()
    {
        Injector injector = Guice.createInjector(new AppModule());
        DrawSquare drawSquare = injector.getInstance(DrawSquare.class);

        assertFalse("AuditGuard should be clear before draw() runs", AuditGuard.isAudited());

        // draw() throws IllegalStateException unless AuditGuard.isAudited() is true, so
        // reaching this line without an exception proves AuditInterceptor called
        // AuditGuard.setAudited(true) before invocation.proceed().
        drawSquare.draw();

        assertFalse("AuditInterceptor should reset the guard after draw() returns", AuditGuard.isAudited());
    }
}
