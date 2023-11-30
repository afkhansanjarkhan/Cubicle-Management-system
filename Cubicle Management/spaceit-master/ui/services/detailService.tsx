import { BASE_SERVICE_URL } from '../config';

export default class DetailInfoService {
  constructor() { }

  public async fetchDetails(pk: string) {
    const body = {};
    return [{ "name": "Booking ID", "value": 123 },
    { "name": "Name", "value": "Pavan" },
    { "name": "Email", "value": "pavan@mymail.com" },
    { "name": "Booked for", "value": "12/12/2022" },
    { "name": "Cubicle Id", "value": "W009" },
    { "name": "Status", "value": "Pending Approval" }
    ];
  }
}
