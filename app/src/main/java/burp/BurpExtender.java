package burp;

import java.awt.Component;
import java.io.PrintWriter;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.handler.HttpResponseReceived;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import static burp.api.montoya.http.message.HttpHeader.httpHeader;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static burp.api.montoya.ui.editor.EditorOptions.READ_ONLY;
import java.awt.event.ActionEvent;
/**
 *
 * @author malvik
 */

public class BurpExtender implements IBurpExtender, IExtensionStateListener, ITab, BurpExtension  {
    private MontoyaApi api;
    
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
  
        Demo.callbacks = callbacks;
        Demo.helpers = callbacks.getHelpers();
        Demo.stdout = new PrintWriter(callbacks.getStdout(), true); //for normal console output
        Demo.stderr = new PrintWriter(callbacks.getStderr(), true); // for error console output
//        Demo.burpGui = new Gui();
//        Demo.handler = new MyHttpHandler();
        //Set name for extension
//        callbacks.setExtensionName("Custom Burp");
//        callbacks.addSuiteTab(this);
        callbacks.registerExtensionStateListener(BurpExtender.this);
//        callbacks.registerHttpListener(this);
        Demo.stdout.println("Extension Loaded");
    }

    @Override
    public void extensionUnloaded() {
        //action to perform when extension is unloaded  
        Demo.stdout.println("Extension Unloaded");
        
    }

    @Override
    public String getTabCaption() {
        return Demo.caption ;
    }

    @Override
    public Component getUiComponent() {
        return Demo.burpGui;
    }

    @Override
    public void initialize(MontoyaApi api)
    {
        this.api = api;
        Demo.api = api;
        Demo.tableModel = new MyTableModel();
        Demo.handler = new MyHttpHandler(api);
        //Register a suite tab that has a button that uses both api's
        api.userInterface().registerSuiteTab("Custom Headers Parameters", new Gui());
        api.extension().setName("Custom Headers Parameters"); //Replaces name set by Wiener Api.
        api.http().registerHttpHandler(Demo.handler);
    }
  
    
}