import { Place, User } from '../places/place.model';

export class Booking {
  constructor(
    public id: number | null,
    public place: Place,
    public user: User,
    public title: string,
    public firstName: string,
    public lastName: string,
    public guestNumber: number,
    public bookedFrom: Date,
    public bookedTo: Date
  ) {}
}
