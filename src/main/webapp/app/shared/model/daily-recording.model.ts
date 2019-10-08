import { Moment } from 'moment';

export interface IDailyRecording {
  id?: number;
  flockNumber?: string;
  materialCode?: string;
  chiksSamplingWeight?: number;
  chiksCondition?: string;
  quantity?: string;
  comment?: string;
  createdBy?: number;
  createdOn?: Moment;
}

export class DailyRecording implements IDailyRecording {
  constructor(
    public id?: number,
    public flockNumber?: string,
    public materialCode?: string,
    public chiksSamplingWeight?: number,
    public chiksCondition?: string,
    public quantity?: string,
    public comment?: string,
    public createdBy?: number,
    public createdOn?: Moment
  ) {}
}
