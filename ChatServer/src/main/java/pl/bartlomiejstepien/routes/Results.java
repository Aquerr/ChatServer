package pl.bartlomiejstepien.routes;

import org.json.JSONObject;

public final class Results
{
    public static Result ok(final String message) {
        return new Result("200 OK", "text/html", message);
    }

    public static Result ok(final JSONObject jsonObject) {
        return new Result("200 OK", "application/json", jsonObject.toString());
    }
}