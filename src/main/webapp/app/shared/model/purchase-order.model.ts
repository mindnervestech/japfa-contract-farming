export interface IPurchaseOrder {
  id?: number;
  farmerName?: string;
  flockNumber?: string;
  pONumber?: string;
  itemName?: string;
  itemID?: string;
  quantity?: number;
  supplierName?: string;
  transpoterName?: string;
}

export class PurchaseOrder implements IPurchaseOrder {
  constructor(
    public id?: number,
    public farmerName?: string,
    public flockNumber?: string,
    public pONumber?: string,
    public itemName?: string,
    public itemID?: string,
    public quantity?: number,
    public supplierName?: string,
    public transpoterName?: string
  ) {}
}
