package skbadani.usingguice;


import com.google.inject.Inject;
import com.google.inject.Provider;

import skbadani.audit.AuditGuard;
import skbadani.audit.Audited;

public class DrawSquare implements DrawShape
{
    private final Provider<SquareRequest> requestProvider;

    @Inject
    public DrawSquare(Provider<SquareRequest> requestProvider)
    {
        this.requestProvider = requestProvider;
        // Can't call get method in the constructor
        // 1. might as well inject the actual object
        // 2. It beats the point of deferred loading -> provider object can be stored so that object
        // getting can be deferred.
        // requestProvider.get();
    }

    @Audited
    @Override
    public void draw()
    {
        if (!AuditGuard.isAudited())
        {
            throw new IllegalStateException("draw() called without being audited");
        }
        SquareRequest squareReq = requestProvider.get();
        System.out.println("Drawing square");
    }
}
