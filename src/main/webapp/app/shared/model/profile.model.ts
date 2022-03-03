import { Moment } from 'moment';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Designation } from 'app/shared/model/enumerations/designation.model';
import { UserType } from 'app/shared/model/enumerations/user-type.model';

export interface IProfile {
  id?: number;
  title?: Title;
  designation?: Designation;
  userType?: UserType;
  eduQual?: string;
  aboutMe?: string;
  hobbies?: string;
  aadhaar?: string;
  dob?: Moment;
  mobile?: string;
  email?: string;
  imgLink?: string;
  imgContentType?: string;
  img?: any;
  facebookLink?: string;
  instaLink?: string;
  twitterLink?: string;
  linkedLink?: string;
  userFirstName?: string;
  userId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public title?: Title,
    public designation?: Designation,
    public userType?: UserType,
    public eduQual?: string,
    public aboutMe?: string,
    public hobbies?: string,
    public aadhaar?: string,
    public dob?: Moment,
    public mobile?: string,
    public email?: string,
    public imgLink?: string,
    public imgContentType?: string,
    public img?: any,
    public facebookLink?: string,
    public instaLink?: string,
    public twitterLink?: string,
    public linkedLink?: string,
    public userFirstName?: string,
    public userId?: number
  ) {}
}
