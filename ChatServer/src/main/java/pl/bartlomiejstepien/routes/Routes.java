package pl.bartlomiejstepien.routes;

/**
 * This class should ONLY contain fields with routes!
 */
public final class Routes
{
    private Routes()
    {

    }

    public static final Route GET_USER_INFORMATION = new Route("GET", "/user/information/{id}", "pl.bartlomiejstepien.controllers.UserController", "information(id)", "{id}");

    public static final Route GET_USER = new Route("GET", "/user/getUser/{id}", "pl.bartlomiejstepien.controllers.UserController", "getUser(id)", "{id}");

    public static final Route SEND_MESSAGE = new Route("GET", "/user/sendMessage", "pl.bartlomiejstepien.controllers.UserController", "sendMessage()");
}
