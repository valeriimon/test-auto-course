package ua.edu.duan.book_shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

public class Utils {

    public static String stringifyJson(Object json) {
        try {
            return new ObjectMapper().writeValueAsString(json);
        } catch (Exception ex) {
            System.out.println("Exception during stringifying JSON -- " + ex.getMessage());
            return "";
        }
    }

    public static String getExceptionReason(MvcResult result) {
        ResponseStatusException ex = (ResponseStatusException) result.getResolvedException();
        return ex.getReason();
    }
}
