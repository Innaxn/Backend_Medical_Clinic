package semester3.project.sanomed.business;

import semester3.project.sanomed.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
