package digit.enrichment;

import digit.TestConfiguration;
import digit.service.UserService;
import digit.util.CreateBirthRequest;
import digit.util.IdgenUtil;
import digit.util.UserUtil;
import digit.web.controllers.BirthApiController;
import digit.web.models.*;
import org.egov.common.contract.request.RequestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@Import(TestConfiguration.class)
class BirthApplicationEnrichmentTest {

    @InjectMocks
    private BirthApplicationEnrichment birthApplicationEnrichment;

    @Mock
    private IdgenUtil idgenUtil;

    @Mock
    private UserService userService;

    @Mock
    private UserUtil userUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void enrichBirthApplication() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();
        when(idgenUtil.getIdList(any(), any(), any(), any(), anyInt()))
                .thenReturn(List.of("PB-BTR-2024-03-26-000065"));

        birthApplicationEnrichment.enrichBirthApplication(birthRegistrationRequest);

        List<BirthRegistrationApplication> applications = birthRegistrationRequest.getBirthRegistrationApplications();
        assertNotNull(applications);
        assertEquals(1, applications.size());
        for (BirthRegistrationApplication application : applications) {
            assertNotNull(application.getAuditDetails());
            assertNotNull(application.getId());
            assertNotNull(application.getApplicationNumber());
            assertNotNull(application.getAddress());
            assertNotNull(application.getFather());
            assertNotNull(application.getMother());
            assertNotNull(application.getAddress().getId());
            assertEquals(application.getId(), application.getFather().getUuid());
            assertEquals(application.getId(), application.getMother().getUuid());
            assertEquals("PB-BTR-2024-03-26-000065", application.getApplicationNumber());
        }
    }

    @Test
    void enrichBirthApplicationUponUpdate() {
        BirthRegistrationRequest birthRegistrationRequest = CreateBirthRequest.createBirthRegistrationRequest();
        BirthRegistrationApplication application = birthRegistrationRequest.getBirthRegistrationApplications().get(0);
        AuditDetails auditDetails = new AuditDetails("a8ae2803-40b7-422e-ae91-a9260b593afa", "a8ae2803-40b7-422e-ae91-a9260b593afa", 1710994329516L, 1710994329516L);
        application.setAuditDetails(auditDetails);

        birthApplicationEnrichment.enrichBirthApplicationUponUpdate(birthRegistrationRequest);

        assertEquals("2f0afc67-bfc1-4263-bb01-e4df1476cf60", application.getAuditDetails().getLastModifiedBy());
        assertNotEquals(System.currentTimeMillis(), application.getAuditDetails().getLastModifiedTime());
    }

}
