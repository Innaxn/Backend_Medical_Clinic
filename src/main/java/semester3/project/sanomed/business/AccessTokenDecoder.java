package semester3.project.sanomed.business;

import semester3.project.sanomed.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
