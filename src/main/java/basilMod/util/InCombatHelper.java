package basilMod.util;


public class InCombatHelper {


    private static boolean _inCombat = false;

    public static void startCombat() {
        _inCombat = true;
    }

    public static void stopCombat() {
        _inCombat = false;
    }

    public static boolean inCombat() {
        return _inCombat;
    }

}
