package skbadani.usingguice;

public class AuditGuard {

    private static final ThreadLocal<Boolean> AUDITED = ThreadLocal.withInitial(() -> false);

    public static boolean isAudited()
    {
        return AUDITED.get();
    }

    public static void setAudited(boolean audited)
    {
        AUDITED.set(audited);
    }
}
