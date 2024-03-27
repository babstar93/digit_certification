package digit.validators;

import digit.TestConfiguration;
import digit.repository.BirthRegistrationRepository;
import digit.util.CreateBirthRequest;
import digit.web.models.BirthRegistrationApplication;
import digit.web.models.BirthRegistrationRequest;
import org.egov.tracer.model.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
@Import(TestConfiguration.class)
public class BirthApplicationValidatorTest {

    @Mock
    private BirthRegistrationRepository repository;

    @InjectMocks
    private BirthApplicationValidator validator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateBirthApplication_WithValidRequest() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();

        assertDoesNotThrow(() -> validator.validateBirthApplication(birthRegistrationRequest));
    }

    @Test
    public void testValidateBirthApplication_WithMissingTenantId() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();
        birthRegistrationRequest.getBirthRegistrationApplications().get(0).setTenantId(null);

        CustomException exception = assertThrows(CustomException.class, () -> validator.validateBirthApplication(birthRegistrationRequest));
        assertEquals("EG_BT_APP_ERR", exception.getCode());
        assertEquals("tenantId is mandatory for creating birth registration applications", exception.getMessage());
    }

    @Test
    public void testValidateApplicationExistence_WithExistingApplication() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();
        BirthRegistrationApplication application = birthRegistrationRequest.getBirthRegistrationApplications().get(0);
        application.setApplicationNumber("PB-BTR-2024-03-26-000065");
        when(repository.getApplications(any())).thenReturn(Collections.singletonList(application));

        BirthRegistrationApplication result = validator.validateApplicationExistence(application);

        assertNotNull(result);
        assertEquals("PB-BTR-2024-03-26-000065", result.getApplicationNumber());
    }

    @Test
    public void testValidateApplicationExistence_WithNonExistingApplication() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();
        BirthRegistrationApplication application = birthRegistrationRequest.getBirthRegistrationApplications().get(0);
        application.setApplicationNumber("PB-BTR-2024-03-26-000065");
        when(repository.getApplications(any())).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> validator.validateApplicationExistence(application));
        assertEquals("BT_APP_NOT_FOUND", exception.getCode());
        assertEquals("Application number not found", exception.getMessage());
    }
}
