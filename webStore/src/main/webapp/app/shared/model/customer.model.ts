import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface ICustomer {
  id?: number;
  name?: string;
  phoneNumber?: string;
  gender?: Gender;
  addressLine1?: string;
  addressLine2?: string;
  addressLine3?: string;
  addressLine4?: string;
  city?: string;
  state?: string;
  country?: string;
  zip?: string;
  userLogin?: string;
  userId?: number;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public phoneNumber?: string,
    public gender?: Gender,
    public addressLine1?: string,
    public addressLine2?: string,
    public addressLine3?: string,
    public addressLine4?: string,
    public city?: string,
    public state?: string,
    public country?: string,
    public zip?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
