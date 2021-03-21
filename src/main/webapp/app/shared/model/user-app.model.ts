import { IElection } from 'app/shared/model/election.model';

export interface IUserApp {
  id?: number;
  city?: string;
  region?: string;
  country?: string;
  internalUserId?: number;
  electionsMades?: IElection[];
}

export class UserApp implements IUserApp {
  constructor(
    public id?: number,
    public city?: string,
    public region?: string,
    public country?: string,
    public internalUserId?: number,
    public electionsMades?: IElection[]
  ) {}
}
