export interface Division {
  id: number;
  name: string;
}

export interface District {
  id: number;
  name: string;
  division?: Division;
}

export interface PoliceStation {
  id: number;
  name: string;
  district?: District;
}
