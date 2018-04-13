package com.codecool.login;

import com.google.common.io.Resources;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URL;


public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            URL url = Resources.getResource("static/index.html");
            StaticHandler.sendFile(httpExchange, url);
        }

    }
}
