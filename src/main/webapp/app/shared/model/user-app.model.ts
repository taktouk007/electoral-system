import { IElection } from 'app/shared/model/election.model';

export interface IUserApp {
  id?: number;
  city?: string;
  region?: string;
  country?: string;
  phoneNumber?: string;
  cin?: string;
  imageContentType?: string;
  image?: any;
  internalUserId?: number;
  electionsMades?: IElection[];
}

export class UserApp implements IUserApp {
  constructor(
    public id?: number,
    public city?: string,
    public region?: string,
    public country?: string,
    public phoneNumber?: string,
    public cin?: string,
    public imageContentType?: string,
    public image?: any,
    public internalUserId?: number,
    public electionsMades?: IElection[]
  ) {}
}
