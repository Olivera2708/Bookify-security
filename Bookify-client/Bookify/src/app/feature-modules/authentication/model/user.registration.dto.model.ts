import { Address } from "../../accommodation/model/address.dto.model"

export interface UserRegistrationDTO {
    email: string,
    password: string,
    confirmPassword: string,
    firstName: string,
    lastName: string,
    address: Address
    phone: string,
    role: string
}
