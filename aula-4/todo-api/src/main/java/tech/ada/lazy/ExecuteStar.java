package tech.ada.lazy;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/execute-star")
public class ExecuteStar {

    @Inject
    Star star;

    @GET
    public String execute() {
        star.app();
        star.sing();
        return "executed";
    }
}
