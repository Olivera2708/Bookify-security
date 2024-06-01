# Bookify Security System

## Overview
This project implements a robust security system for a booking platform using Keycloak. It encompasses various authentication and security mechanisms to ensure user data protection and secure access to resources.

## Features

### 1. Single Sign-On (SSO) Authentication
- **Implementation**: Integrated SSO using Keycloak to streamline the login process.
- **User Data Management**: User data is stored in an LDAP directory for centralized identity management. Every new user is registered in the LDAP database, not in a relational database. Post-authentication user data is also maintained in LDAP.

### 2. Passwordless Login
- **Method**: Enabled passwordless login using methods such as magic links (Google), reducing the need for users to remember passwords.

### 3. Two-Factor Authentication (2FA)
- **Implementation**: Integrated 2FA with the option to generate One-Time Passwords (OTPs), adding an extra layer of security during user authentication.

### 4. Password Policy on Registration
- **Requirements**: Users are required to set passwords that comply with a defined password policy during registration.
- **Blacklist**: Registration of passwords found on a blacklist of known compromised passwords is prohibited.

### 5. Protection Against Automated Attacks
- **Integration**: Implemented reCAPTCHA in registration processes to reduce the possibility of automated bot attacks.

### 6. Role-Based Access Control (RBAC)
- **Access Management**: Restricted access to resources based on user permissions within the system, enabling efficient and secure management of access rights.

### 7. Token Management
- **Access Tokens**: Used for quick access to protected resources.
- **Refresh Tokens**: Enabled to refresh access tokens without the need for re-login.
- **Validation Tokens**: Implemented for additional validation during password resets.

### 8. Input Validation
- **Implementation**: Conducted strict validation of all user inputs to prevent the entry of harmful content or data manipulation.

### 9. OWASP Top 10 Compliance
- **Creation**: Wrote an OWASP Top 10 list to cover security measures implemented in the system.

### 10. Certificate Management in Booking Application
1. **SysAdmin Role**: Allows requesting certificates for issuing certificates within the Bookify application.
2. **Owner Role**: Allows requesting certificates for digitally signing messages.
3. **Secure Distribution**: Certificates are distributed in a secure manner.
4. **Secure HTTP**: Users are enabled to use a secure version of the HTTP protocol (HTTPS).

### 11. Public Key Infrastructure (PKI) Service
1. **Centralized Certificate Issuance**: SysAdmin can centrally issue certificates for digital entities in the system (PKI). SysAdmin can issue any certificate in the chain, including self-signed certificates, intermediate certificates (CA), and end-entity certificates. Multiple levels of intermediary certificates are supported.
2. **Certificate Visibility**: SysAdmin has insight into all certificates existing in the system.
3. **Implementation Considerations**:
   - For end-entity certificates, prevent storage of private keys in the PKI system. Keys can be generated externally by users or via an autogenerate option, where the PKI system generates key pairs but does not store them.
   - Consider whether all certificates should be stored in the same KeyStore file.
   - Track information about the type of entity the certificate is issued to (service, subsystem, user).
   - Enable certificate templates to define extensions and intended use, simplifying admin tasks.
   - Ensure the validity of certificates in terms of issuer selection. Determine which certificates can be offered for signing non-root certificates. Address the validity of certificates beyond just expiration dates.
   - Follow best practices for security function configuration.
   - SysAdmin can revoke certificates, and the PKI must provide a service to check certificate revocation status. Decide on the technique for revocation checking and handle the impact on certificates signed by a revoked intermediary certificate.
   - Address the lifespan of certificates (root CA, intermediate CA, end user) and the lifespan of the CA's private key for signing purposes.
