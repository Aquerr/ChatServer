package pl.bartlomiejstepien.routes;

public final class Result
{
    private final String httpVersion = "HTTP/1.1";
    private final String statusCode;
    private final String contentType;
    private final String responseBody;

    public Result(final String statusCode, final String contentType, final String responseBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public String getContentType()
    {
        return this.contentType;
    }

    public String getHttpVersion()
    {
        return this.httpVersion;
    }

    public String getResponseBody()
    {
        return this.responseBody;
    }

    public String getStatusCode()
    {
        return this.statusCode;
    }
}
