export interface IFigure {
  id?: number;
  fullName?: string;
  imageContentType?: string;
  image?: any;
  linkedIn?: string;
  facebook?: string;
  twitter?: string;
  candidateId?: number;
}

export class Figure implements IFigure {
  constructor(
    public id?: number,
    public fullName?: string,
    public imageContentType?: string,
    public image?: any,
    public linkedIn?: string,
    public facebook?: string,
    public twitter?: string,
    public candidateId?: number
  ) {}
}
