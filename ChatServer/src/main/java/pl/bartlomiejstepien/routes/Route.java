package pl.bartlomiejstepien.routes;

public class Route
{
    private final String httpMethod;
    private final String url;
    private final String methodName;
    private final String className;
    private final String[] params;

    public Route(final String httpMethod, final String url, final String className, final String methodName, final String... params)
    {
        this.httpMethod = httpMethod;
        this.className = className;
        this.methodName = methodName;
        this.params = params;
        this.url = url;
    }

    public String getHttpMethod()
    {
        return this.httpMethod;
    }

    public String getClassName()
    {
        return this.className;
    }

    public String getMethodName()
    {
        return this.methodName;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String[] getParams()
    {
        return this.params;
    }
}
