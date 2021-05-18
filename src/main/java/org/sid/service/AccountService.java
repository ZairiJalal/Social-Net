package org.sid.service;

import java.util.List;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Post;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
      AppUser addNewUser(AppUser appUser);
      AppRole addNewRole(AppRole appRole);
      void addRoleToUser(String username,String roleName);
      AppUser loadUserByUsername(String username);
      List<AppUser> listUsers();
      List<AppUser> followersListUsers(String id);
      List<AppUser> followingsListUsers(String id);
      List<Post> followersListPost(String id);
      List<Post> followingsListPost(String id);
}
