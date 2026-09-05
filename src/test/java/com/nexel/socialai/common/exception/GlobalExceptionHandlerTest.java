package com.nexel.socialai.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleBusinessException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/test");

        ResponseEntity<ApiError> response = handler.handleBusinessException(new BusinessException("Bad request"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Bad request");
        assertThat(response.getBody().path()).isEqualTo("/api/test");
    }

    @Test
    void shouldHandleValidationException() throws NoSuchMethodException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/validate");

        Method method = TestController.class.getDeclaredMethod("create", Payload.class);
        BindingResult bindingResult = new BeanPropertyBindingResult(new Payload(), "payload");
        bindingResult.rejectValue("name", "NotBlank", "Name is required");
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(new MethodParameter(method, 0), bindingResult);

        ResponseEntity<ApiError> response = handler.handleMethodArgumentNotValid(exception, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo("Name is required");
    }

    static class TestController {
        public void create(Payload payload) {
        }
    }

    static class Payload {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
