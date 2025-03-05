package framework.pages.testdata;

import org.apache.log4j.Logger;

public class LoginCredentialsData {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(LoginCredentialsData.class.getName());
    private String username = null;
    private String password = null;
    private String tenantName = null;
    private String testValidateStatus = null;
    private String testValidateFailComments = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTestValidateStatus() {
        return testValidateStatus;
    }

    public void setTestValidateStatus(String testValidateStatus) {
        this.testValidateStatus = testValidateStatus;
    }

    public String getTestValidateFailComments() {
        return testValidateFailComments;
    }

    public void setTestValidateFailComments(String testValidateFailComments) {
        this.testValidateFailComments = testValidateFailComments;
    }
}
