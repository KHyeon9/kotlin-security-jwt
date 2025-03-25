import com.kotlin.springsecurity.entity.UserAccount

fun userAccountEntity(userId: String, password: String) = UserAccount(
    userId, password, "nickname"
)