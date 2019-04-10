package pl.bartlomiejstepien.controllers;

import org.json.JSONObject;
import pl.bartlomiejstepien.routes.Result;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static pl.bartlomiejstepien.routes.Results.ok;

@Controller
public class UserController
{
    public static Result information(final String id) {
        return ok("You got user's [id=" + id + "] informations!");
    }

    public static Result getUser(final String id)
    {
        File file = new File(".").toPath().resolve("ChatServer").resolve("src").resolve("main").resolve("java").resolve("pl").resolve("bartlomiejstepien").resolve("index.html").toFile();
        if(file.exists())
        {
            try
            {
                return ok(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return ok("ERROR");
    }

    public static Result sendMessage()
    {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", "Nivis");
        jsonObject.put("message", "SIEMA! ELO! ELO! ELO!");
        jsonObject.put("timestamp", "2019-04-10T20:20");
        return ok(jsonObject);
    }
}
