import { BASE_SERVICE_URL } from "../config";

export default class BookingService{

    public async createBooking(cubicleId: string, employeeId:string, startDate: string, endDate:string){
        return fetch( BASE_SERVICE_URL + '/booking', {
            method: 'post',
            body: JSON.stringify({
                "cubicle":{"cubicleId":cubicleId},
                "employee":{"employeeId":employeeId},
                "startDate":startDate,
                "endDate":endDate,
                "status":"Waiting"
            }),
            headers: { "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},
          }).then((response: any) => {
            console.log(response.headers);
            return response.json();
          })
         }

         public async getBooking(pk:string){
          const body = {};
          return fetch(BASE_SERVICE_URL+`/booking/${pk}`,{
              method:'get',
              headers:{ "Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json' ,"Authorization":'Bearer '+localStorage.getItem("token")},
          }).then((response: any) => {
              return response.json();
          })
        }
    

         public async search() {
          const body = {};
           const responseData = await fetch(BASE_SERVICE_URL + '/booking', {
             method: 'get',
             headers:{"Access-Control-Allow-Origin": "*", 'Content-Type': 'application/json','Authorization': "Bearer " + localStorage.getItem('token')},
           }).then((response: any) => {
             console.log(response.headers);
             return response.json();
           }); 
          return responseData;
    }
  }
