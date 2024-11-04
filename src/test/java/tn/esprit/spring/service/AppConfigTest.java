package tn.esprit.spring.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = AppConfig.class)
class AppConfigTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void testModelMapperBean() {
        assertNotNull(modelMapper, "ModelMapper bean should be available in the application context");
    }
}



