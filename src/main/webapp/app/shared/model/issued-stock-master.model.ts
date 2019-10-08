export interface IIssuedStockMaster {
  id?: number;
  flockNumber?: string;
  materialCode?: string;
  materialName?: string;
  stockIssued?: number;
}

export class IssuedStockMaster implements IIssuedStockMaster {
  constructor(
    public id?: number,
    public flockNumber?: string,
    public materialCode?: string,
    public materialName?: string,
    public stockIssued?: number
  ) {}
}
