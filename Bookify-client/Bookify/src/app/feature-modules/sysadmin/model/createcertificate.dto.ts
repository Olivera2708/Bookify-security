import {SubjectDTO} from "./subjectdto.dto";

export interface CreateCertificateDTO {
  issuerCertificateAlias: string,
  subject: SubjectDTO,
  extensions: string[],
  issued: Date,
  expires: Date
}
