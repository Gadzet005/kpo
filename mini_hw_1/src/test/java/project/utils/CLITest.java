package project.utils;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class CLITest {
    protected @Mock Scanner reader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
