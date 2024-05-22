package com.evac.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * this is a class used for storing information
 * in a @RequestBody in AuthController. It has
 * getters which are used to extract information from
 * the @RequestBody.
 */
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

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
}
