package semester3.project.sanomed.business.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.keygen.KeyGenerators;
import semester3.project.sanomed.domain.AccessToken;

import java.security.Key;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AccessTokenEncoderDecoderImplTest {


//    @Mock
//    private Key keyGenerators;
//
//    @InjectMocks
//    AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
//
//
//
//    @Test
//    public void testEncode() {
//        AccessToken accessToken = AccessToken.builder().subject("user").roles(Arrays.asList("admin")).patientId(1L).build();
//        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);
//
//        when(accessTokenEncoderDecoder.encode(accessToken)).thenReturn(encodedToken);
//
//        assertNotNull(encodedToken);
//        assertTrue(encodedToken.startsWith("eyJhbGciOiJIUzI1NiJ9"));
//    }
//
//    @Test
//    void decode() {
//        String accessTokenEncoded = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYwMTY3NjI3MCwiZXhwIjoxNjAx" +
//                "Njc2NDUwLCJyb2xlcyI6WyJhZG1pbiIsInVzZXIiXSwiZW1wbG95ZWVJZCI6MSwicGF0aWVudElkIjoxMjN9.pZm1Y7VnI_wzf7ZMkcFzC7b9XWZz8yVlNq3g3yB5lQI";
//        AccessToken accessToken = accessTokenEncoderDecoder.decode(accessTokenEncoded);
//
//        assertNotNull(accessToken);
//        assertEquals("user1", accessToken.getSubject());
//        assertEquals(Arrays.asList("admin", "user"), accessToken.getRoles());
//        assertEquals(1L, accessToken.getEmployeeId().longValue());
//        assertEquals(123L, accessToken.getPatientId().longValue());
//    }
}