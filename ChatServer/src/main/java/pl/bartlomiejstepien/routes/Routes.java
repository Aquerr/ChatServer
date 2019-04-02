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
}
