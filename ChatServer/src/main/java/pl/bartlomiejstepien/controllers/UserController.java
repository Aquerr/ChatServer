package pl.bartlomiejstepien.controllers;

import pl.bartlomiejstepien.routes.Result;
import pl.bartlomiejstepien.routes.Results;

@Controller
public class UserController
{
    public static Result information(final String id) {
        return Results.ok("You got user's [id=" + id + "] informations!");
    }
}
