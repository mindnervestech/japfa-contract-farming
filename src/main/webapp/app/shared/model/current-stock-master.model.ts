export interface ICurrentStockMaster {
  id?: number;
  flockNumber?: string;
  materialCode?: string;
  materialName?: string;
  stockInHand?: number;
}

export class CurrentStockMaster implements ICurrentStockMaster {
  constructor(
    public id?: number,
    public flockNumber?: string,
    public materialCode?: string,
    public materialName?: string,
    public stockInHand?: number
  ) {}
}
