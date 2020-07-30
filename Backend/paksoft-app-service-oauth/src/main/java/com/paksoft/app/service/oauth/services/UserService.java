package com.paksoft.app.service.oauth.services;

import com.paksoft.app.commons.user.models.entity.User;

public interface UserService {
	public User findByUsername(String username);
	public User update(User user, Long id);
}
