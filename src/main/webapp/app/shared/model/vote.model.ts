import { Moment } from 'moment';

export interface IVote {
  id?: number;
  candidateId?: string;
  submissionDate?: Moment;
  electionId?: number;
}

export class Vote implements IVote {
  constructor(public id?: number, public candidateId?: string, public submissionDate?: Moment, public electionId?: number) {}
}
