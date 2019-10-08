export interface IFarmerMaster {
  id?: number;
  farmerName?: string;
  farmerID?: string;
  flockNumber?: string;
  addressOfFarmer?: string;
  lineSupervisorName?: string;
  lineSupervisorID?: string;
}

export class FarmerMaster implements IFarmerMaster {
  constructor(
    public id?: number,
    public farmerName?: string,
    public farmerID?: string,
    public flockNumber?: string,
    public addressOfFarmer?: string,
    public lineSupervisorName?: string,
    public lineSupervisorID?: string
  ) {}
}
