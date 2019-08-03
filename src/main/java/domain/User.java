package domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "user_name")
   private String userName;

   @Column(name = "password")
   private String password;

   @Column(name = "email")
   private String email;

   @Column(name = "pwd_is_default_pwd")
   private Boolean pwdIsDefaultPwd;

   @Column(name = "is_deleted")
   private Boolean isDeleted;

   @Column(name = "created_at")
   private Date createdAt;

   @Column(name = "locked")
   private Boolean locked;

   @Column(name = "expired")
   private Date expired;

   @Column(name = "forgot_password_code")
   private String forgotPasswordCode;

   @Column(name = "code_expired_at")
   private Date codeExpiredAt;

   @Column(name = "is_valid_code")
   private Boolean isValidCode;

   @Column(name = "last_login")
   private Date  lastLogin;

}
