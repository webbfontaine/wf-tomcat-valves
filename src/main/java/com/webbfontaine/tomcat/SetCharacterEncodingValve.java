/*
 * Copyrights 2002-2012 Webb Fontaine
 * Developer: Sargis Harutyunyan
 * Date: 27 déc. 2012
 * This software is the proprietary information of Webb Fontaine.
 * Its use is subject to License terms.
 */
package com.webbfontaine.tomcat;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SetCharacterEncodingValve extends ValveBase {

    private final Log log = LogFactory.getLog(SetCharacterEncodingValve.class); // must not be static

    private String encoding = "UTF-8";

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        if (encoding != null) {
            if (log.isDebugEnabled()) {
                log.debug(String.format("SetCharacterEncodingValve applying %s encoding to request", encoding));
            }

            applyEncoding(request);
            invokeNext(request, response);
        }
    }

    private void applyEncoding(Request request) throws UnsupportedEncodingException {
        request.setCharacterEncoding(encoding);
    }

    private void invokeNext(Request request, Response response) throws IOException, ServletException {
        Valve nextValve = getNext();
        if (nextValve != null) {
            nextValve.invoke(request, response);
        }
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
