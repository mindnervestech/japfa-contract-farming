import { Moment } from 'moment';

export interface IMrn {
  id?: number;
  vehicleNumber?: string;
  dCDate?: Moment;
  dCNumber?: string;
  postingDate?: Moment;
  pONumber?: string;
  itemNumber?: string;
  avgWeight?: string;
  condition?: string;
  createdBy?: number;
  itemRecieved?: number;
  flockNumber?: string;
}

export class Mrn implements IMrn {
  constructor(
    public id?: number,
    public vehicleNumber?: string,
    public dCDate?: Moment,
    public dCNumber?: string,
    public postingDate?: Moment,
    public pONumber?: string,
    public itemNumber?: string,
    public avgWeight?: string,
    public condition?: string,
    public createdBy?: number,
    public itemRecieved?: number,
    public flockNumber?: string
  ) {}
}
