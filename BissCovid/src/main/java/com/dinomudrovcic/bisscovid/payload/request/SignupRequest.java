package com.dinomudrovcic.bisscovid.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {
	
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 200)
    private String password;

}
