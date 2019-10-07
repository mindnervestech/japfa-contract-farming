export interface ISAPMaster {
  id?: number;
  farmerName?: string;
  farmerID?: string;
  branchCode?: string;
  flockNumber?: string;
  addressOfFarmer?: string;
  itemCode?: string;
  quantity?: string;
  pONumber?: string;
}

export class SAPMaster implements ISAPMaster {
  constructor(
    public id?: number,
    public farmerName?: string,
    public farmerID?: string,
    public branchCode?: string,
    public flockNumber?: string,
    public addressOfFarmer?: string,
    public itemCode?: string,
    public quantity?: string,
    public pONumber?: string
  ) {}
}
