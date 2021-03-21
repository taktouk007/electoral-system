import { IFigure } from 'app/shared/model/figure.model';

export interface ICandidate {
  id?: number;
  figures?: IFigure[];
  electionId?: number;
}

export class Candidate implements ICandidate {
  constructor(public id?: number, public figures?: IFigure[], public electionId?: number) {}
}
