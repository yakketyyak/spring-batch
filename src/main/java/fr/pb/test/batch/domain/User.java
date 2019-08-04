package fr.pb.test.batch.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {

   private Integer id;
   private String userName;
   private String password;
   private String email;
   private Boolean pwdIsDefaultPwd;
   private Boolean isDeleted;
   private Date createdAt;
   private Boolean locked;
   private Date expired;
   private String forgotPasswordCode;
   private Date codeExpiredAt;
   private Boolean isValidCode;
   private Date  lastLogin;

}
