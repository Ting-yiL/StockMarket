package util;

import nl.rug.aoop.util.YamlLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestYamlLoader {
    private YamlLoader yamlLoader;
    private final Path STOCKPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\stock.yaml");
    private final Path TRADERPATH = Path.of("C:\\Users\\ting\\IdeaProjects\\rug\\2023_Team_083\\stocks\\data\\traders.yaml");

    @BeforeEach
    void SetUp() throws IOException {
        this.yamlLoader = new YamlLoader(STOCKPATH);
    }

    @Test
    void TestYamlLoaderConstructor() {
        assertNotNull(this.yamlLoader);
    }

    @Test
    void TestLoad() {
        //this.yamlLoader.load();
    }
}
