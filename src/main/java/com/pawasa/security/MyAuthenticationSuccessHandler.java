package com.pawasa.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    public MyAuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
    }
}
