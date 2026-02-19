package tech.ada.lazy;

import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

@Singleton
public class AnotadaComSingleton {

    @PostConstruct
    void init() {
        Log.info("init() " + this.getClass().getName());
    }

    public String greeting() {
        return "sing";
    }
}
