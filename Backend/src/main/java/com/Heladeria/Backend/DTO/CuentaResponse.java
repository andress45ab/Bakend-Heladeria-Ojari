package com.Heladeria.Backend.DTO;

import lombok.Data;
@Data
public class CuentaResponse{
    private String accessToken;
    private String tokenType = "Bearer";

    public CuentaResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}