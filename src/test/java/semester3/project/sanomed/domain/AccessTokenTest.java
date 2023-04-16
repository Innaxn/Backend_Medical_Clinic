package semester3.project.sanomed.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenTest {

    @Test
    public void testHasRole() {
        AccessToken token = new AccessToken();
        token.setRoles(Arrays.asList("admin", "user"));

        assertTrue(token.hasRole("admin"));
        assertTrue(token.hasRole("user"));
        assertFalse(token.hasRole("doctor"));
    }

    @Test
    public void testHasRole_nullRoles() {
        AccessToken token = new AccessToken();
        token.setRoles(null);
        assertFalse(token.hasRole("admin"));
    }
}