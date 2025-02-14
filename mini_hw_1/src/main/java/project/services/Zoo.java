package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Zoo {
    public @Autowired AnimalStorage animals;
    public @Autowired ThingStorage things;
}
