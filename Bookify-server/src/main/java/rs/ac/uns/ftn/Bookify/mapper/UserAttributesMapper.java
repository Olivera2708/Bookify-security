package rs.ac.uns.ftn.Bookify.mapper;

import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration;
import org.springframework.ldap.core.AttributesMapper;
import rs.ac.uns.ftn.Bookify.model.LdapUser;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class UserAttributesMapper implements AttributesMapper<LdapUser> {

    @Override
    public LdapUser mapFromAttributes(Attributes attrs) throws NamingException {
        LdapUser ldapUser = new LdapUser();

        if (attrs.get("employeeType") != null) {
            ldapUser.setRole((String) attrs.get("employeeType").get());
        }

        if (attrs.get("displayName") != null) {
            ldapUser.setLastName((String) attrs.get("displayName").get());
        }

        if (attrs.get("givenName") != null) {
            ldapUser.setFirstName((String) attrs.get("givenName").get());
        }

        if (attrs.get("mobile") != null) {
            ldapUser.setPhone((String) attrs.get("mobile").get());
        }

        if (attrs.get("mail") != null) {
            ldapUser.setEmail((String) attrs.get("mail").get());
            ldapUser.setCn((String) attrs.get("cn").get());
            ldapUser.setSn((String) attrs.get("sn").get());
            ldapUser.setUid((String) attrs.get("uid").get());
        }
        return ldapUser;
    }
}
