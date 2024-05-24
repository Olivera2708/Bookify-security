package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.mapper.UserAttributesMapper;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class LdapPollingService {
    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserService userService;

    public LdapPollingService(LdapTemplate ldapTemplate, IUserRepository userRepository, IUserService userService) {
        this.ldapTemplate = ldapTemplate;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public void synchronizeSQLToLDAP() throws NamingException {
        List<User> sqlUsers = userRepository.findAll();
        for (User sqlUser : sqlUsers) {
            LdapQuery query = query()
                    .base("ou=users,ou=system")
                    .where("uid")
                    .is(sqlUser.getEmail());

            List<LdapUser> ldapUsers = ldapTemplate.search(query, new UserAttributesMapper());

            if (!ldapUsers.isEmpty()) {
                Attributes attributes = sqlUser.toAttributes();
                attributes.put("employeeType", userService.getRole(sqlUser));

                if(sqlUser.getEmail().contains("+")){
                    sqlUser.setEmail(sqlUser.getEmail().replace("+", "\\+"));
                }


                String userDn = "uid=" + sqlUser.getEmail() + ",ou=users,ou=system";
                List<ModificationItem> modificationItems = new ArrayList<>();
                NamingEnumeration<? extends Attribute> attributeEnumeration = (NamingEnumeration<? extends Attribute>) attributes.getAll();
                while (attributeEnumeration.hasMore()) {
                    Attribute updatedAttribute = attributeEnumeration.next();
                    modificationItems.add(new ModificationItem(DirContext.REPLACE_ATTRIBUTE, updatedAttribute));
                }
                ldapTemplate.modifyAttributes(userDn, modificationItems.toArray(new ModificationItem[0]));
            }
        }
    }

    @Scheduled(fixedRate = 6000) // Poll every 60 seconds
    public void pollLdapForNewUsers() {
        LdapQuery query = query().base("ou=users,ou=system").where("objectclass").is("inetOrgPerson");
        List<LdapUser> ldapUsers = ldapTemplate.search(query, new UserAttributesMapper());

        for (LdapUser ldapUser : ldapUsers) {
            if (userRepository.findByEmail(ldapUser.getEmail()) == null) {
                User user;
                switch (ldapUser.getRole()){
                    case "GUEST":
                        user = new Guest();
                        break;
                    case "OWNER":
                        user = new Owner();
                        break;
                    case "ADMIN":
                        user = new Admin();
                        break;
                    default:
                        user = new SysAdmin();
                        break;
                }
                user.setEmail(ldapUser.getEmail());
                user.setFirstName(ldapUser.getFirstName());
                user.setLastName(ldapUser.getLastName());
                user.setPhone(ldapUser.getPhone());

                userRepository.save(user);

                if(ldapUser.getEmail().contains("+")){
                    ldapUser.setEmail(ldapUser.getEmail().replace("+", "\\+"));
                }

                String userDn = "uid=" + ldapUser.getEmail() + ",ou=users,ou=system";
                String groupDn = "cn=" + ldapUser.getRole() + ",ou=roles,dc=example,dc=com";

                ModificationItem[] modificationItems = new ModificationItem[]{
                        new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("member", userDn))
                };
                ldapTemplate.modifyAttributes(groupDn, modificationItems);
            }
        }
    }
}
