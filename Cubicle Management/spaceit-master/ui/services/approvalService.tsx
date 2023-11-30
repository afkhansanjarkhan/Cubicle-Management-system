import { BASE_SERVICE_URL } from '../config';
import { BookingServices } from './bookingServices';

const bookingServices = new BookingServices();
export default class ApprovalsService {

  constructor() { }

  public async approve(pk: string, comment: string) {
    console.log(comment);
    const res = await bookingServices.getBooking(pk);
    res["status"]="Approved";
      var body = res;
      console.log(body)
      const resposeData = await fetch(BASE_SERVICE_URL + `/booking/${pk}`, {
        method: 'put',
        body: JSON.stringify(body),
        headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
      }).then((response: any) => {
        console.log(response.headers);
        return response.json();
      });
    return {};
  }


  public async reject(pk: string, comment: string) {
    console.log(comment);
    const res = await bookingServices.getBooking(pk);
    res["status"] = "Declined";
        var body= res;
        return fetch(BASE_SERVICE_URL+`/booking/${pk}`,{
            method:'put',
            body:JSON.stringify(body),
            headers:{ "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},
        }).then((response: any) => {
            return response.json();
        })
    return {};
  }

}