export interface ReportedUser {
    id?:number,
    reason?:string,
    created?:number,
    reportedUser:{
        id?:number,
        firstName?:string,
        lastName?:string,
        email?:string,
        blocked: boolean
    },
    createdBy:{
        id?:number,
        firstName?:string,
        lastName?:string,
        email?:string
    }

}
