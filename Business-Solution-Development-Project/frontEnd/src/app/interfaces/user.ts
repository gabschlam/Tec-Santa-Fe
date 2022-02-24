import { Address } from "src/app/interfaces/address";

export interface User {
  profile_pic: string;
  name: string;
  lname: string;
  dBirth: string;
  addresses: Array<Address>;
  email: string;
  password: string;
}
