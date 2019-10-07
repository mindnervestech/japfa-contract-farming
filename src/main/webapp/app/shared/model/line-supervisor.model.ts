export interface ILineSupervisor {
  id?: number;
  name?: string;
  branchCode?: string;
}

export class LineSupervisor implements ILineSupervisor {
  constructor(public id?: number, public name?: string, public branchCode?: string) {}
}
