package tech.ada.lazy;


import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class Star {

    final AnotadaComSingleton anotadaComSingleton;
    final AnotadaComApplicationScoped anotadaComApplicationScoped;

    public Star(AnotadaComSingleton anotadaComSingleton, AnotadaComApplicationScoped anotadaComApplicationScoped) {
        this.anotadaComSingleton = anotadaComSingleton;
        this.anotadaComApplicationScoped = anotadaComApplicationScoped;
    }

    public void app() {
        Log.info("calling app(): " + anotadaComApplicationScoped.greeting());
    }

    public void sing() {
        Log.info("calling sing(): " + anotadaComSingleton.greeting());
    }
}
