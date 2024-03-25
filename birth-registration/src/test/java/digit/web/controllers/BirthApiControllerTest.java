package digit.web.controllers;

import com.google.gson.Gson;
import digit.repository.BirthRegistrationRepository;
//import digit.web.models.*;
import digit.web.models.*;
import org.egov.common.contract.request.Role;
import org.egov.common.contract.request.User;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import digit.TestConfiguration;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for BirthApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(BirthApiController.class)
@Import(TestConfiguration.class)
public class BirthApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void birthRegistrationV1CreatePostSuccess() throws Exception {
        BirthRegistrationRequest birthRegistrationRequest = new BirthRegistrationRequest();
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        org.egov.common.contract.request.User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .roles(listRoles)
                .tenantId("pb")
                .build();

        List<BirthRegistrationApplication> listApplications = new ArrayList<>();

        Address address = Address.builder()
                .tenantId("pb")
                .doorNo("1010")
                .addressNumber("34 GA")
                .type("RESIDENTIAL")
                .addressLine1("KP Layout")
                .landmark("Petrol pump")
                .city("Amritsar")
                .pincode("143501")
                .buildingName("Avigna Residence")
                .street("12th Main")
                .build();

        List<digit.web.models.Role> roleList = new ArrayList<>();
        digit.web.models.Role role3 = digit.web.models.Role.builder()
                .name("CITIZEN")
                .tenantId("pb")
                .build();
        roleList.add(role3);

        digit.web.models.User userFather = digit.web.models.User.builder()
                .name("Abhay")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        digit.web.models.User userMother = digit.web.models.User.builder()
                .name("Amita")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        List<String> assignees = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();
        Document document = Document.builder()
                .documentType("PDF")
                .fileStore("b0c5a846-c75a-11ea-87d0-0242ac130003")
                .documentType("")
                .build();
        documentList.add(document);
        Workflow workflow = Workflow.builder()
                .action("APPLY")
                .assignees(assignees)
                .comment("Applying for birth registration")
                .documents(documentList)
                .build();

        BirthApplicationAddress birthApplicationAddress = BirthApplicationAddress.builder()
                .tenantId("pb")
                .applicantAddress(address)
                .build();

        BirthRegistrationApplication birthRegistrationApplication = BirthRegistrationApplication.builder()
                .tenantId("pb")
                .timeOfBirth(12072001)
                .babyFirstName("Rahul")
                .babyLastName("Singh")
                .doctorName("Dr. Ram")
                .hospitalName("Fortis")
                .placeOfBirth("Palampur")
                .address(birthApplicationAddress)
                .father(userFather)
                .mother(userMother)
                .workflow(workflow)
                .build();

        listApplications.add(birthRegistrationApplication);

        requestInfo.setUserInfo(user);

        birthRegistrationRequest.setRequestInfo(requestInfo);
        birthRegistrationRequest.setBirthRegistrationApplications(listApplications);

        Gson gson = new Gson();
        String request = gson.toJson(birthRegistrationRequest);
        System.out.println(request);
        mockMvc.perform(post("/birth/registration/v1/_create").contentType(MediaType
        .APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isOk());
    }

    @Test
    public void birthRegistrationV1CreatePostFailure() throws Exception {
        BirthRegistrationRequest birthRegistrationRequest = new BirthRegistrationRequest();
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .tenantId("pb")
                .roles(listRoles)
                .build();

        List<BirthRegistrationApplication> listApplications = new ArrayList<>();

        Address address = Address.builder()
                .tenantId("pb")
                .doorNo("1010")
                .addressNumber("34 GA")
                .type("RESIDENTIAL")
                .addressLine1("KP Layout")
                .landmark("Petrol pump")
                .city("Amritsar")
                .pincode("143501")
                .buildingName("Avigna Residence")
                .street("12th Main")
                .build();

        List<digit.web.models.Role> roleList = new ArrayList<>();
        digit.web.models.Role role3 = digit.web.models.Role.builder()
                .name("CITIZEN")
                .tenantId("pb")
                .build();
        roleList.add(role3);

        digit.web.models.User userFather = digit.web.models.User.builder()
                .name("Abhay")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        digit.web.models.User userMother = digit.web.models.User.builder()
                .name("Amita")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        List<String> assignees = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();
        Document document = Document.builder()
                .documentType("PDF")
                .fileStore("b0c5a846-c75a-11ea-87d0-0242ac130003")
                .documentType("")
                .build();
        documentList.add(document);
        Workflow workflow = Workflow.builder()
                .action("APPLY")
                .assignees(assignees)
                .comment("Applying for birth registration")
                .documents(documentList)
                .build();

        BirthApplicationAddress birthApplicationAddress = BirthApplicationAddress.builder()
                .tenantId("pb")
                .applicantAddress(address)
                .build();

        BirthRegistrationApplication birthRegistrationApplication = BirthRegistrationApplication.builder()
//                .tenantId("pb")
                .timeOfBirth(12072001)
                .babyFirstName("Rahul")
                .babyLastName("Singh")
                .doctorName("Dr. Ram")
                .hospitalName("Fortis")
                .placeOfBirth("Palampur")
                .address(birthApplicationAddress)
                .father(userFather)
                .mother(userMother)
                .workflow(workflow)
                .build();

        listApplications.add(birthRegistrationApplication);

        requestInfo.setUserInfo(user);

        birthRegistrationRequest.setRequestInfo(requestInfo);
        birthRegistrationRequest.setBirthRegistrationApplications(listApplications);

        Gson gson = new Gson();
        String request = gson.toJson(birthRegistrationRequest);
        System.out.println(request);
        mockMvc.perform(post("/birth/registration/v1/_create").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void birthRegistrationV1SearchPostSuccess() throws Exception {
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .tenantId("pb")
                .roles(listRoles)
                .build();

        requestInfo.setUserInfo(user);

        Gson gson = new Gson();
        String request = gson.toJson(requestInfo);
        mockMvc.perform(post("/birth/registration/v1/_search?tenantId=pb").contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(request))
        .andExpect(status().isOk());
    }

    @Test
    public void birthRegistrationV1SearchPostFailure() throws Exception {
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .tenantId("pb")
                .roles(listRoles)
                .build();

        requestInfo.setUserInfo(user);

        Gson gson = new Gson();
        String request = gson.toJson(requestInfo);
        mockMvc.perform(post("/birth/registration/v1/_search").contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(request))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void birthRegistrationV1UpdatePostSuccess() throws Exception {
        BirthRegistrationRequest birthRegistrationRequest = new BirthRegistrationRequest();
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        org.egov.common.contract.request.User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .roles(listRoles)
                .tenantId("pb")
                .build();

        List<BirthRegistrationApplication> listApplications = new ArrayList<>();

        Address address = Address.builder()
                .tenantId("pb")
                .doorNo("1010")
                .addressNumber("34 GA")
                .type("RESIDENTIAL")
                .addressLine1("KP Layout")
                .landmark("Petrol pump")
                .city("Amritsar")
                .pincode("143501")
                .buildingName("Avigna Residence")
                .street("12th Main")
                .build();

        List<digit.web.models.Role> roleList = new ArrayList<>();
        digit.web.models.Role role3 = digit.web.models.Role.builder()
                .name("CITIZEN")
                .tenantId("pb")
                .build();
        roleList.add(role3);

        digit.web.models.User userFather = digit.web.models.User.builder()
                .name("Abhay")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        digit.web.models.User userMother = digit.web.models.User.builder()
                .name("Amita")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        List<String> assignees = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();
        Document document = Document.builder()
                .documentType("PDF")
                .fileStore("b0c5a846-c75a-11ea-87d0-0242ac130003")
                .documentType("")
                .build();
        documentList.add(document);
        Workflow workflow = Workflow.builder()
                .action("APPLY")
                .assignees(assignees)
                .comment("Applying for birth registration")
                .documents(documentList)
                .build();

        BirthApplicationAddress birthApplicationAddress = BirthApplicationAddress.builder()
                .tenantId("pb")
                .applicantAddress(address)
                .build();

        BirthRegistrationApplication birthRegistrationApplication = BirthRegistrationApplication.builder()
                .id("831e9930-8426-4d14-9636-d5e4a1e1f263")
                .tenantId("pb")
                .timeOfBirth(12072001)
                .babyFirstName("Rahul Update")
                .babyLastName("Singh")
                .doctorName("Dr. Ram")
                .hospitalName("Fortis")
                .placeOfBirth("Palampur")
                .address(birthApplicationAddress)
                .father(userFather)
                .mother(userMother)
                .workflow(workflow)
                .build();

        listApplications.add(birthRegistrationApplication);

        requestInfo.setUserInfo(user);

        birthRegistrationRequest.setRequestInfo(requestInfo);
        birthRegistrationRequest.setBirthRegistrationApplications(listApplications);

        Gson gson = new Gson();
        String request = gson.toJson(birthRegistrationRequest);
        System.out.println(request);
        mockMvc.perform(post("/birth/registration/v1/_update").contentType(MediaType
        .APPLICATION_JSON_VALUE)
        .content(request))
        .andExpect(status().isOk());
    }

    @Test
    public void birthRegistrationV1UpdatePostFailure() throws Exception {
        BirthRegistrationRequest birthRegistrationRequest = new BirthRegistrationRequest();
        org.egov.common.contract.request.RequestInfo requestInfo = new org.egov.common.contract.request.RequestInfo();
        requestInfo.setApiId("asset-services");
        requestInfo.setMsgId("search with from and to values");
        requestInfo.setAuthToken("a58c1f24-61d9-48d6-992b-3ff0e7fc80a6");
        List<Role> listRoles = new ArrayList<Role>();
        Role role1 = Role.builder()
                .name("CSC Collection Operator")
                .tenantId("pb")
                .build();
        Role role2 = Role.builder()
                .name("Employee")
                .tenantId("pb")
                .build();

        listRoles.add(role1);
        listRoles.add(role2);
        org.egov.common.contract.request.User user = org.egov.common.contract.request.User.builder()
                .id(24226L)
                .uuid("2f0afc67-bfc1-4263-bb01-e4df1476cf60")
                .userName("amr001")
                .name("leela")
                .mobileNumber("9814424443")
                .emailId("leela@llgmail.com")
                .type("EMPLOYEE")
                .roles(listRoles)
                .tenantId("pb")
                .build();

        List<BirthRegistrationApplication> listApplications = new ArrayList<>();

        Address address = Address.builder()
                .tenantId("pb")
                .doorNo("1010")
                .addressNumber("34 GA")
                .type("RESIDENTIAL")
                .addressLine1("KP Layout")
                .landmark("Petrol pump")
                .city("Amritsar")
                .pincode("143501")
                .buildingName("Avigna Residence")
                .street("12th Main")
                .build();

        List<digit.web.models.Role> roleList = new ArrayList<>();
        digit.web.models.Role role3 = digit.web.models.Role.builder()
                .name("CITIZEN")
                .tenantId("pb")
                .build();
        roleList.add(role3);

        digit.web.models.User userFather = digit.web.models.User.builder()
                .name("Abhay")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        digit.web.models.User userMother = digit.web.models.User.builder()
                .name("Amita")
                .userName("91300114")
                .mobileNumber("9230011254")
                .emailId("xyz@egovernments.org")
                .tenantId("pb")
                .type("CITIZEN")
                .roles(roleList)
                .build();

        List<String> assignees = new ArrayList<>();
        List<Document> documentList = new ArrayList<>();
        Document document = Document.builder()
                .documentType("PDF")
                .fileStore("b0c5a846-c75a-11ea-87d0-0242ac130003")
                .documentType("")
                .build();
        documentList.add(document);
        Workflow workflow = Workflow.builder()
                .action("APPLY")
                .assignees(assignees)
                .comment("Applying for birth registration")
                .documents(documentList)
                .build();

        BirthApplicationAddress birthApplicationAddress = BirthApplicationAddress.builder()
                .tenantId("pb")
                .applicantAddress(address)
                .build();

        BirthRegistrationApplication birthRegistrationApplication = BirthRegistrationApplication.builder()
                .id("831e9930-8426-4d14-9636-d5e4a1e1f263")
//                .tenantId("pb")
                .timeOfBirth(12072001)
                .babyFirstName("Rahul Update")
                .babyLastName("Singh")
                .doctorName("Dr. Ram")
                .hospitalName("Fortis")
                .placeOfBirth("Palampur")
                .address(birthApplicationAddress)
                .father(userFather)
                .mother(userMother)
                .workflow(workflow)
                .build();

        listApplications.add(birthRegistrationApplication);

        requestInfo.setUserInfo(user);

        birthRegistrationRequest.setRequestInfo(requestInfo);
        birthRegistrationRequest.setBirthRegistrationApplications(listApplications);

        Gson gson = new Gson();
        String request = gson.toJson(birthRegistrationRequest);
        System.out.println(request);
        mockMvc.perform(post("/birth/registration/v1/_update").contentType(MediaType
                                .APPLICATION_JSON_VALUE)
                        .content(request))
        .andExpect(status().isBadRequest());
    }

}
