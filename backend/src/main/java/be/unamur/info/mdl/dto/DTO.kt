package be.unamur.info.mdl.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class UserDTO(val username: String,val password: String,val name : String,val firstname : String,val email : String)

class CredentialDTO(val username : String,val password : String)

class PasswordChangeDTO(val oldpwd : String,@Size(min = 8, max = 20) val newpwd : String)