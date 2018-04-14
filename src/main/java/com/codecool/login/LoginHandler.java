package com.codecool.login;

import com.codecool.login.data.SqliteUserDAO;
import com.codecool.login.data.UserDAO;
import com.codecool.login.models.User;
import com.google.common.io.Resources;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import javafx.util.Pair;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginHandler implements HttpHandler {

    private Map<String, Integer> sessionsUsers = new HashMap<>();
    private int sessionCounter = 0;
    private UserDAO userDAO = new SqliteUserDAO();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();
        HttpCookie cookie;

        if (method.equals("GET")) {
            String sessionCookie = httpExchange.getRequestHeaders().getFirst("Cookie");
            if (sessionCookie != null) {
                cookie = HttpCookie.parse(sessionCookie).get(0);
                if (sessionsUsers.containsKey(cookie.getValue())) {
                    sendPersonalizedPage(httpExchange, cookie.getValue());
                    return;
                }
            }
            sendLoginPage(httpExchange);
        }

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println(formData);
            Pair<String,String> loginPassword = parseFormData(formData);

            if (userDAO.getByLoginAndPassword(loginPassword.getKey(), loginPassword.getValue()) != null) {

                sessionCounter++;
                User user = userDAO.getByLoginAndPassword(loginPassword.getKey(), loginPassword.getValue());
                cookie = new HttpCookie("sessionId", String.valueOf(sessionCounter));
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                sessionsUsers.put(Integer.toString(sessionCounter), user.getUserId());
                System.out.println(sessionsUsers.toString());
                sendPersonalizedPage(httpExchange, Integer.toString(sessionCounter));

            } else {
                // alert ?
                sendLoginPage(httpExchange);
            }
        }
    }

    private void sendPersonalizedPage(HttpExchange httpExchange, String sessionId) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/template.twig");
        JtwigModel model = JtwigModel.newModel();

        int userId = sessionsUsers.get(sessionId);
        System.out.println("User ID: " + userId);
        User user = userDAO.getById(userId);

        System.out.println("User login: " + user.getLogin());
        model.with("userLogin", user.getLogin());
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendLoginPage(HttpExchange httpExchange) throws IOException {
        URL url = Resources.getResource("static/index.html");
        StaticHandler.sendFile(httpExchange, url);
    }

    private Pair<String, String> parseFormData(String formData) throws UnsupportedEncodingException {

        final int LOGIN_INDEX = 0;
        final int PASSWORD_INDEX = 1;
        final int VALUE_INDEX = 1;

        String[] pairs = formData.split("&");
        List<String> values = new ArrayList<>();
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            values.add(URLDecoder.decode(keyValue[VALUE_INDEX], "UTF-8"));
        }
        return new Pair<>(values.get(LOGIN_INDEX), values.get(PASSWORD_INDEX));
    }
}
