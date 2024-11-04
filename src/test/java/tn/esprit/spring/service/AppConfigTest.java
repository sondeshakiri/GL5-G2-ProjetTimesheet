package tn.esprit.spring.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(AppConfigTest.MockConfig.class)
class AppConfigTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void testMockModelMapperBean() {
        assertNotNull(modelMapper, "Mocked ModelMapper bean should not be null");
    }

    @Configuration
    static class MockConfig {
        @Bean
        public ModelMapper modelMapper() {
            return mock(ModelMapper.class);
        }
    }
}




