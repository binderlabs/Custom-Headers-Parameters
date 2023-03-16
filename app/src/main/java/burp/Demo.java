package burp;

import java.io.PrintWriter;
import burp.api.montoya.MontoyaApi;
/**
 *
 * @author malvik
 */
public class Demo {
    public static BurpExtender burpExtender ;
    public static Gui burpGui;
    public static String extensionName = "GUI Demo";
    public static String caption = "Gui Demo";
    public static PrintWriter stdout ;
    public static PrintWriter stderr ;
    public static IBurpExtenderCallbacks callbacks ;
    public static IExtensionHelpers helpers;
    public static boolean extensionStatus = true ;
    public static MyHttpHandler handler;
    public static MyTableModel tableModel;
    public static MontoyaApi api;
    public  static boolean isScopeonly = false;
    
}