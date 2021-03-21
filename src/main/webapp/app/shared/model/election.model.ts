import { Moment } from 'moment';
import { IVote } from 'app/shared/model/vote.model';
import { ICandidate } from 'app/shared/model/candidate.model';
import { Scope } from 'app/shared/model/enumerations/scope.model';

export interface IElection {
  id?: number;
  targetFunction?: string;
  startDate?: Moment;
  endDate?: Moment;
  scope?: Scope;
  city?: string;
  region?: string;
  country?: string;
  votes?: IVote[];
  candidates?: ICandidate[];
  userAppId?: number;
}

export class Election implements IElection {
  constructor(
    public id?: number,
    public targetFunction?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public scope?: Scope,
    public city?: string,
    public region?: string,
    public country?: string,
    public votes?: IVote[],
    public candidates?: ICandidate[],
    public userAppId?: number
  ) {}
}
