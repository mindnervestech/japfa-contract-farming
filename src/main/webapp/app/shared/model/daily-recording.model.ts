import { Moment } from 'moment';

export interface IDailyRecording {
  id?: number;
  flockNumber?: string;
  itemCode?: string;
  quantity?: string;
  comment?: string;
  createdBy?: number;
  createdOn?: Moment;
}

export class DailyRecording implements IDailyRecording {
  constructor(
    public id?: number,
    public flockNumber?: string,
    public itemCode?: string,
    public quantity?: string,
    public comment?: string,
    public createdBy?: number,
    public createdOn?: Moment
  ) {}
}
