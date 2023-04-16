package ru.quipy.user.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class UserSecurity
    (
    val id: UUID,
    val email: String,
    private val uPassword: String,
    private val uAuthorities: MutableCollection<GrantedAuthority>
) : UserDetails {
    override fun getAuthorities() = uAuthorities
    override fun getPassword() = uPassword
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired()= true
    override fun isEnabled() = true
}