package com.Heladeria.Backend.DTO;

import lombok.Data;
@Data
public class CuentaResponse{
    private String accessToken;
    private String tokenType = "Bearer";
    private String rol;

    public CuentaResponse(String accessToken, String rol) {
        this.accessToken = accessToken;
        this.rol = rol;
    }
    public String getAccessToken() {
        return accessToken;
    }

    // Constructor original (puedes mantenerlo si lo usas en otros lados)
    public CuentaResponse(String accessToken) {
        this.accessToken = accessToken;
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

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }


}