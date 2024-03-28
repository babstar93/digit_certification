package digit.enrichment;

import digit.service.UserService;
import digit.util.IdgenUtil;
import digit.util.UserUtil;
import digit.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import digit.models.coremodels.UserDetailResponse;
import org.egov.common.contract.request.User;

@Component
@Slf4j
public class BirthApplicationEnrichment {

    @Autowired
    private IdgenUtil idgenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtil userUtils;

    public void enrichBirthApplication(BirthRegistrationRequest birthRegistrationRequest) {
        List<String> birthRegistrationIdList = idgenUtil.getIdList(birthRegistrationRequest.getRequestInfo(), birthRegistrationRequest.getBirthRegistrationApplications().get(0).getTenantId(), "btr.registrationid", "", birthRegistrationRequest.getBirthRegistrationApplications().size());
        Integer index = 0;
        for(BirthRegistrationApplication application : birthRegistrationRequest.getBirthRegistrationApplications()){
            // Enrich audit details
            AuditDetails auditDetails = AuditDetails.builder().createdBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).createdTime(System.currentTimeMillis()).lastModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid()).lastModifiedTime(System.currentTimeMillis()).build();
            application.setAuditDetails(auditDetails);

            // Enrich UUID
            application.setId(UUID.randomUUID().toString());

//            application.getFather().setUuid(application.getId());
//            application.getMother().setUuid(application.getId());

            // Enrich registration Id
            application.getAddress().setRegistrationId(application.getId());

            // Enrich address UUID
            application.getAddress().setId(UUID.randomUUID().toString());

            //Enrich application number from IDgen
            application.setApplicationNumber(birthRegistrationIdList.get(index++));

        }
    }

    public void enrichBirthApplicationUponUpdate(BirthRegistrationRequest birthRegistrationRequest) {
        // Enrich lastModifiedTime and lastModifiedBy in case of update
        birthRegistrationRequest.getBirthRegistrationApplications().get(0).getAuditDetails().setLastModifiedTime(System.currentTimeMillis());
        birthRegistrationRequest.getBirthRegistrationApplications().get(0).getAuditDetails().setLastModifiedBy(birthRegistrationRequest.getRequestInfo().getUserInfo().getUuid());
    }

    private digit.web.models.User  convertToNewUser(org.egov.common.contract.request.User oldUser) {
        digit.web.models.User newUser = new digit.web.models.User();

        // Copying fields from oldUser to newUser
        newUser.setTenantId(oldUser.getTenantId());
        newUser.setId(oldUser.getId() != null ? Math.toIntExact(oldUser.getId()) : null);
        newUser.setUuid(oldUser.getUuid());
        newUser.setUserName(oldUser.getUserName());
        newUser.setMobileNumber(oldUser.getMobileNumber());
        newUser.setEmailId(oldUser.getEmailId());

        // Converting roles
        List<Role> roles = new ArrayList<>();
        oldUser.getRoles().forEach(oldRole -> {
            Role newRole = new Role();
            newRole.setName(oldRole.getName());
            newRole.setTenantId(oldRole.getTenantId());
            roles.add(newRole);
        });
        newUser.setRoles(roles);

        newUser.setName(oldUser.getName());
        newUser.setType(oldUser.getType());

        return newUser;
    }

    public void enrichFatherApplicantOnSearch(BirthRegistrationApplication application) {
        UserDetailResponse fatherUserResponse = userService.searchUser(userUtils.getStateLevelTenant(application.getTenantId()),application.getFather().getUuid() ,null);
        User fatherUser = fatherUserResponse.getUser().get(0);
        log.info(fatherUser.toString());
        User fatherApplicant =
                User.builder()
                        .id(fatherUser.getId())
                        .mobileNumber(fatherUser.getMobileNumber())
                        .userName(fatherUser.getUserName())
                        .uuid(fatherUser.getUuid())
                        .name(fatherUser.getName())
                        .type(fatherUser.getType())
                        .emailId(fatherUser.getEmailId())
                        .roles(fatherUser.getRoles()).build();
        application.setFather(convertToNewUser(fatherApplicant));
    }

    public void enrichMotherApplicantOnSearch(BirthRegistrationApplication application) {
        UserDetailResponse motherUserResponse = userService.searchUser(userUtils.getStateLevelTenant(application.getTenantId()),application.getMother().getUuid().toString(),null);
        User motherUser = motherUserResponse.getUser().get(0);
        log.info(motherUser.toString());
        User motherApplicant = User.builder()
                .id(motherUser.getId())
                .mobileNumber(motherUser.getMobileNumber())
                .userName(motherUser.getUserName())
                .uuid(motherUser.getUuid())
                .name(motherUser.getName())
                .type(motherUser.getType())
                .emailId(motherUser.getEmailId())
                .roles(motherUser.getRoles()).build();
        application.setMother(convertToNewUser(motherApplicant));
    }

}