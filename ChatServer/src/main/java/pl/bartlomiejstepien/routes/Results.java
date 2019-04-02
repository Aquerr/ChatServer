package pl.bartlomiejstepien.routes;

public final class Results
{
    public static Result ok(final String message) {
        return new Result("200 OK", "text/plain", message);
    }
}