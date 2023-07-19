import { PlaceLocation } from './location.model';

export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  age: number;
}

export class Place {
  constructor(
    public id: string,
    public title: string,
    public description: string,
    public imageUrl: string,
    public price: number,
    public availableFrom: Date,
    public availableTo: Date,
    public user: User,
    public location: PlaceLocation
  ) {}
}
