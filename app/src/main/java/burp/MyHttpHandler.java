/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package burp;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;
import static burp.api.montoya.http.message.params.HttpParameter.urlParameter;
import static burp.api.montoya.http.message.params.HttpParameter.parameter;
import com.google.gson.*;
import burp.api.montoya.http.message.MimeType;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static burp.api.montoya.http.message.HttpHeader.httpHeader;
import burp.api.montoya.http.message.params.HttpParameterType;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyHttpHandler implements HttpHandler
{
    private static final List<String> CONTENT_TYPES = Arrays.asList("application/json", "application/javascript");
    private MontoyaApi api;
    private List<HttpHeader> headerlist;
    private List<HttpParameter> parameterlist;
    
    public MyHttpHandler(MontoyaApi api)
    {
        this.api = api;
        this.headerlist = new ArrayList<>();
    }
    
//    public void setHeader(String headers){
//        headerlist = new ArrayList<>();
//        String[] lines = headers.split("\n");
//        for (String line : lines) {
//            String[] keyvalue = line.split(":");
//            this.headerlist.add(httpHeader(keyvalue[0], keyvalue[1]));
//            api.logging().logToOutput("Hi"+line);
//        }
//    }

    public void setHeader(List<List<Object>> headers) {
        headerlist = new ArrayList<>();   
        for (List<Object> row : headers) {
               String val1 = row.get(0).toString();
               String val2 = row.get(1).toString();
               this.headerlist.add(httpHeader(val1, val2));
           }
    }

    
    public void setParameter(List<List<Object>> parameters) {
        parameterlist = new ArrayList<>();   
        for (List<Object> row : parameters) {
               String val1 = row.get(0).toString();
               String val2 = row.get(1).toString();
               String val3 = row.get(2).toString();
               this.parameterlist.add(parameter(val1, val2, HttpParameterType.valueOf(val3)));
           }
    }
@Override
public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
    HttpRequest modifiedRequest = requestToBeSent;
    if(Demo.isScopeonly){
        if(api.scope().isInScope(modifiedRequest.url())){
            if(headerlist != null){
                for (HttpHeader header : headerlist) {
                    modifiedRequest = modifiedRequest.withAddedHeader(header);
                }
            }
            if(parameterlist != null){
                for (HttpParameter param : parameterlist) {
        //            if(param.type() == HttpParameterType.valueOf("JSON") && String.valueOf(modifiedRequest.contentType()) == "JSON"){
        //            if(param.type() == HttpParameterType.valueOf("JSON") && "JSON".equals(String.valueOf(modifiedRequest.contentType()))){
        //                String requestBody = modifiedRequest.bodyToString();
        //                api.logging().logToOutput(modifiedRequest.bodyToString());
        //                Gson gson = new GsonBuilder().create();
        //                JsonObject json = gson.fromJson(requestBody, JsonObject.class);
        //                json.addProperty(param.name(), param.value());
        //                api.logging().logToOutput(json.toString());
        //                modifiedRequest = modifiedRequest.withBody(json.toString());
        //                modifiedRequest = modifiedRequest.withAddedParameters(param);
        //            }
        //                if(param.type() == HttpParameterType.valueOf("BODY")){
        //                    if("POST".equals(modifiedRequest.method())){
        //                    modifiedRequest = modifiedRequest.withAddedParameters(param);
        //                    }
        //                }
        //                else{
        //                    modifiedRequest = modifiedRequest;
        //                }
                        modifiedRequest = modifiedRequest.withAddedParameters(param);

                } 
            }
        }
    }else{
            if(headerlist != null){
                for (HttpHeader header : headerlist) {
                    modifiedRequest = modifiedRequest.withAddedHeader(header);
                }
            }
            if(parameterlist != null){
                for (HttpParameter param : parameterlist) {
                        modifiedRequest = modifiedRequest.withAddedParameters(param);

                } 
            }
    }
    // Add each header to the modified request
    

    
    return RequestToBeSentAction.continueWith(modifiedRequest);
}
    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived)
    {   
        Demo.tableModel.add(responseReceived);
        return ResponseReceivedAction.continueWith(responseReceived);
    }
}