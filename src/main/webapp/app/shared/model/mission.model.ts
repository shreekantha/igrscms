export interface IMission {
  id?: number;
  details?: string;
}

export class Mission implements IMission {
  constructor(public id?: number, public details?: string) {}
}
