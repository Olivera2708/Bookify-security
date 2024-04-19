import {SubjectDTO} from "./subjectdto.dto";

export interface BasicCertificateDTO {
  issuerEmail: string,
  subjectCertificateAlias: string,
  subject: SubjectDTO,
  extensions: string[],
  expires: Date
}
