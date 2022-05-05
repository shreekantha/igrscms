import { Moment } from 'moment';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

export interface IUserProfile {
  id?: number;
  title?: Title;
  firstName?: string;
  lastName?: string;
  mobile?: string;
  email?: string;
  designation?: Designation;
  userType?: UserType;
  eduQual?: string;
  aboutMe?: string;
  hobbies?: string;
  aadhaar?: string;
  dob?: Moment;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
  facebookLink?: string;
  instaLink?: string;
  twitterLink?: string;
  linkedLink?: string;
  tenantId?: string;
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public title?: Title,
    public firstName?: string,
    public lastName?: string,
    public mobile?: string,
    public email?: string,
    public designation?: Designation,
    public userType?: UserType,
    public eduQual?: string,
    public aboutMe?: string,
    public hobbies?: string,
    public aadhaar?: string,
    public dob?: Moment,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any,
    public facebookLink?: string,
    public instaLink?: string,
    public twitterLink?: string,
    public linkedLink?: string,
    public tenantId?: string
  ) {}
}
