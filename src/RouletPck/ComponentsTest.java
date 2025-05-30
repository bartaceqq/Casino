package RouletPck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentsTest {

    @Test
    void extractNumber() {
        Components components = new Components();
        int result = components.extractNumber("button_2.png");
        assertEquals(2, result);
    }

}