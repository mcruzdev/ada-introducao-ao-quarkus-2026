package tech.ada.lazy;

import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AnotadaComApplicationScoped {

    @PostConstruct
    void init() {
        Log.info("init() " + this.getClass().getName());
    }

    public String greeting() {
        return "app";
    }
}
