package com.inspur.utils;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class HttpResponseUtil {

    public static void outputJsonMsg(HttpServletResponse response, Map<String, String> infoMap) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject result = new JSONObject();
        for (Map.Entry<String, String> entry : infoMap.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(result.toString());
        responseWriter.flush();
        responseWriter.close();
    }
}
