package tech.ada.dao;

import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Unremovable
public class OutroCaraA {

    @PostConstruct
    void init() {
        Log.info("init do " + this.getClass().getName());
    }
}
